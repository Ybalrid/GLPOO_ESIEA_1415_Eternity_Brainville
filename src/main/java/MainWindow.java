package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	private int w = 800;
	private int h = 600;

	private ImagePanel[] imgPanels;
	private CellPanel[] cellPanels;

	private PuzzlePanel puzzle;
	private JPanel containerEast;
	private StockPanel stock;

	private JMenuBar menuBar;
	private JMenu menuFichier;
	private JMenuItem itemQuitter;

	public MainWindow()
	{
		super("Eternity");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setResizable(false);
		//this.getContentPane().setMinimumSize(new Dimension(w,h));
		// setPreferredSize rather than setSize because of layout manager
		// The contentPane excludes the menu
		this.getContentPane().setPreferredSize(new Dimension(700,400));
		this.getContentPane().setBackground(Color.GREEN);

		constructMenu();
		constructContent();

		this.setVisible(true);
		this.pack();

		DragInfo.init();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	private void constructMenu()
	{
		menuBar = new JMenuBar();
		menuFichier = new JMenu("Fichier");

		itemQuitter = new JMenuItem("Quitter");
		itemQuitter.addActionListener(this);

		menuFichier.add(itemQuitter);

		menuBar.add(menuFichier);

		this.setJMenuBar(menuBar);
	}

	private void constructContent()
	{
		this.puzzle = new PuzzlePanel();
		this.containerEast = new JPanel();
		this.stock = new StockPanel();

		// size preferences only useful if not in BorderLayout.CENTER
		this.containerEast.setPreferredSize(new Dimension(300, 600));
		this.stock.setPreferredSize(new Dimension(300, 300));

		containerEast.setLayout(new BorderLayout());
		containerEast.add(this.stock, BorderLayout.NORTH);
		containerEast.add(new JPanel(), BorderLayout.CENTER);

		// JFrame.add points to JContentPane.add
		// ContentPane has BorderLayout by default
		this.add(this.puzzle, BorderLayout.CENTER);
		this.add(this.containerEast, BorderLayout.EAST);

		this.cellPanels = new CellPanel[16];
		for (int i = 0; i < 5; i++) {
			CellPanel p = new CellPanel(this);
			p.setBackground(new Color(i * 16 + 15, i * 16 + 15, i * 16 + 15));
			this.puzzle.add(p);
			this.cellPanels[i] = p;
		}

		this.imgPanels = new ImagePanel[16];
		for (int i = 0; i < 3; i++) {
			ImagePanel p = new ImagePanel(this);
			p.setBackground(Color.BLACK);
			this.cellPanels[i].add(p);
			this.imgPanels[i] = p;
		}

		Face[] faces1 = {new Face(0, "blue", "purple", "zigzag"), new Face(0, "purple", "red", "circle"), new Face(0, "green", "blue", "triangle"), new Face(0, "red", "yellow", "")};
		Face[] faces2 = {new Face(0, "", "", ""), new Face(0, "yellow", "red", ""), new Face(0, "yellow", "green", ""), new Face(0, "purple", "red", "")};
		Piece[] pieces = {new Piece(0, faces1, 0, 0, 0), new Piece(0, faces2, 0, 0, 0)};

		for (int i = 0; i < pieces.length; i++)
		{
			this.imgPanels[i].setImg(PieceSynthetizer.synthetize(pieces[i]));
			this.imgPanels[i].repaint();
		}
	}

	/*
	 * Events response implementations
	 */

	public void actionPerformed(ActionEvent arg0)
	{
		this.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
//		System.out.println("[Clicked] Point: " + e.getX() + ", " + e.getY() + " ");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println("[Entered] Point: " + e.getX() + ", " + e.getY() + " ");

		if (DragInfo.getSelected() != null) {
			if (e.getComponent() instanceof CellPanel) {
				DragInfo.setDestination((CellPanel)e.getComponent());
			} else if (e.getComponent() instanceof ImagePanel) {
				DragInfo.setDestination(null);
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.print("[Exited] ");// Point: " + e.getX() + ", " + e.getY() + " ");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Vector2D mousePos = new Vector2D(e.getX(), e.getY());
		System.out.println("[Pressed] Point: " + mousePos + " ");

		if (e.getComponent() instanceof ImagePanel) {
			ImagePanel selected = (ImagePanel)e.getComponent();

			if (DragInfo.getSelected() == null && e.getComponent() instanceof ImagePanel) {
				System.out.println("Press selected" + selected.getLocation());

				CellPanel cell = (CellPanel)selected.getParent();
				DragInfo.setSelected(selected);
				DragInfo.setOrigin(cell);
				DragInfo.setDestination(cell);
				cell.remove(selected);
				cell.repaint();
				this.add(selected, 0);
				Point parentPos = selected.getParent().getLocationOnScreen();
				selected.setLocation(e.getXOnScreen() - (int)parentPos.getX() - selected.getWidth()/2, e.getYOnScreen() - (int)parentPos.getY() - selected.getHeight()/2);
				this.repaint();
				System.out.println("Selected: " + selected + " ");
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vector2D mousePos = new Vector2D(e.getX(), e.getY());
		System.out.println("[Released] Point: " + mousePos + " ");
		//System.out.println("source: " + e.getComponent() + " " + this.getComponentAt(e.getX(), e.getY()) + " ");

		CellPanel origin = (CellPanel)DragInfo.getOrigin();
		ImagePanel selected = (ImagePanel)DragInfo.getSelected();

		if (selected != null) {
			Point p = e.getComponent().getLocation();
			e.translatePoint((int) p.getX(), (int) p.getY());
			Component under = this.puzzle.getComponentAt(e.getX(), e.getY());

			CellPanel dest;
			if (under instanceof CellPanel && ((CellPanel)under).getComponentCount() == 0) {
				dest = (CellPanel)under;
			} else dest = origin;
			System.out.println("Destination: " + dest);

			if (dest != null) {
				dest.add(selected, 0);
				dest.validate();
				this.remove(selected);
				this.repaint();
				DragInfo.reset();
				System.out.println("Unselected " + selected.getLocation() + " " + dest.getComponent(0));
			}
		}
	}

	/* MouseMotionEvent */

	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.print("[Moved] " + e.getX() + " " + e.getY() + " ");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.print("[Dragged] " + e.getX() + " " + e.getY() + " ");
		Vector2D mousePos = new Vector2D(e.getX(), e.getY());

		ImagePanel selected = (ImagePanel)DragInfo.getSelected();
		if (selected != null) {
			System.out.println("Drag selected: " + selected.getLocation());
			Point parentPos = selected.getParent().getLocationOnScreen();
			selected.setLocation(e.getXOnScreen() - (int)parentPos.getX() - selected.getWidth()/2, e.getYOnScreen() - (int)parentPos.getY() - selected.getHeight()/2);
		}

	}

}
