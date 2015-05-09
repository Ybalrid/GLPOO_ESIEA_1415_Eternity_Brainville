package main.java;

import java.util.ArrayList;

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
import javax.swing.SwingUtilities;

public class MainWindow extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	private ImagePanel[] imgPanels;
	private ArrayList<DropTarget> dropTargets;

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
		this.setResizable(false);
		//this.getContentPane().setMinimumSize(new Dimension(w,h));
		// setPreferredSize rather than setSize because of layout manager
		// The contentPane excludes the menu
		this.getContentPane().setPreferredSize(new Dimension(720,400));
		this.getContentPane().setBackground(Color.GREEN);

		constructMenu();
		constructContent();

		this.setVisible(true);
		this.pack();

		DragInfo.init();

		this.addMouseListener(this);
		//this.addMouseMotionListener(this);
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
		this.containerEast.setPreferredSize(new Dimension(320, 600));
		this.stock.setPreferredSize(new Dimension(320, 320));

		containerEast.setLayout(new BorderLayout());
		containerEast.add(this.stock, BorderLayout.NORTH);
		containerEast.add(new JPanel(), BorderLayout.CENTER);

		// JFrame.add points to JContentPane.add
		// ContentPane has BorderLayout by default
		this.add(this.puzzle, BorderLayout.CENTER);
		this.add(this.containerEast, BorderLayout.EAST);

		this.dropTargets = new ArrayList<DropTarget>(17);
		this.dropTargets.add(stock);

		for (int i = 0; i < 16; i++) {
			CellPanel p = new CellPanel(this);
			p.setBackground(new Color(i * 16 + 15, i * 16 + 15, i * 16 + 15));
			this.puzzle.add(p, BorderLayout.WEST);
			this.dropTargets.add(p);
		}

		this.imgPanels = new ImagePanel[16];
		for (int i = 0; i < 7; i++) {
			ImagePanel p = new ImagePanel(this);
			p.setBackground(Color.BLACK);
			this.stock.add(p, BorderLayout.WEST);
			this.imgPanels[i] = p;
		}

		Face[] faces1 = {new Face(0, "blue", "purple", "zigzag"), new Face(0, "purple", "red", "circle"), new Face(0, "green", "blue", "triangle"), new Face(0, "red", "yellow", "")};
		Face[] faces2 = {new Face(0, "", "", ""), new Face(0, "yellow", "red", ""), new Face(0, "yellow", "green", ""), new Face(0, "purple", "red", "")};
		Piece[] pieces = {new Piece(0, faces1, 0, 0, 0), new Piece(0, faces2, 0, 0, 0), new Piece(0, faces1, 0, 0, 0), new Piece(0, faces2, 0, 0, 0)};

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
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.print("[Exited] ");// Point: " + e.getX() + ", " + e.getY() + " ");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Vector2D mousePos = new Vector2D(e.getX(), e.getY());
		System.out.println("[Pressed] Point: " + mousePos + " ");

		if (e.getComponent() instanceof DragTarget && DragInfo.getSelected() == null) {
			DragTarget selected = (DragTarget)e.getComponent();
			DropTarget target = (DropTarget)selected.getParent();
			DragInfo.setSelected(selected);
			DragInfo.setOrigin(target);
			Point globalPos = SwingUtilities.convertPoint(selected, e.getPoint(), this.getContentPane());
			selected.setLocation(globalPos.x - selected.getWidth()/2, globalPos.y - selected.getHeight()/2);
			target.remove(selected);
			target.repaint();
			this.add(selected, 0);
			this.repaint();
			System.out.println("Selected: " + selected + " ");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Vector2D mousePos = new Vector2D(e.getX(), e.getY());
		System.out.println("[Released] Point: " + mousePos + " ");

		DropTarget origin = DragInfo.getOrigin();
		DragTarget selected = DragInfo.getSelected();

		if (selected != null) {
			Point p = e.getPoint();
			DropTarget dest = origin;

			for (DropTarget target : this.dropTargets) {
				p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), target);
				if (target.contains(p)) {
					dest = target;
				}
			}
			System.out.println("Destination: " + dest);

			// Swap the two pieces if drag into already occupied DropTarget
			if (!dest.acceptMultipleChilds() && dest.getComponentCount() != 0) {
				DragTarget destContent = (DragTarget)dest.getComponent(0);
				origin.add(destContent, 0);
				origin.validate();
				dest.remove(destContent);
				dest.add(selected);
				dest.validate();
			} else { // Place the piece in the DropTarget
				dest.add(selected, 0);
				dest.validate();
			}
			this.remove(selected);
			this.repaint();
			DragInfo.reset();
			System.out.println("Unselected " + selected.getLocation() + " " + dest.getComponent(0));
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

		DragTarget selected = DragInfo.getSelected();
		if (selected != null) {
			System.out.println("Drag selected: " + selected.getLocation());
			Point globalPos = SwingUtilities.convertPoint(selected, e.getPoint(), this.getContentPane());
			selected.setLocation(globalPos.x - selected.getWidth()/2, globalPos.y - selected.getHeight()/2);
		}

	}

}
