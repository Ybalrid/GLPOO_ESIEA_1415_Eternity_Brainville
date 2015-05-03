package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	private int w = 800;
	private int h = 600;

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
		//this.setMinimumSize(new Dimension(w,h));
		// setPreferredSize rather than setSize because of layout manager
		this.setPreferredSize(new Dimension(700,400));
		this.setBackground(Color.GRAY);

		constructMenu();
		constructContent();

		this.setVisible(true);
		this.pack();
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
		stock.setPreferredSize(new Dimension(300, 300));

		containerEast.setLayout(new BorderLayout());
		containerEast.add(this.stock, BorderLayout.NORTH);
		containerEast.add(new JPanel(), BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(this.puzzle, BorderLayout.CENTER);
		this.add(this.containerEast, BorderLayout.EAST);
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
//		System.out.println("[Clicked] Point: " + e.getX() + ", " + e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println("[Entered] Point: " + e.getX() + ", " + e.getY());
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.print("[Exited] ");// Point: " + e.getX() + ", " + e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("[Pressed] Point: " + e.getX() + ", " + e.getY());
		
		Vector2D mousePos = new Vector2D(e.getX(), e.getY());
		DragInfo.setDragOrigin(mousePos);
		
		DragInfo.setLastMousePos(mousePos);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println("[Released] Point: " + e.getX() + ", " + e.getY());
		Vector2D mousePos = new Vector2D(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.print("[Moved] " + e.getX() + " " + e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
//		System.out.print("[Dragged] ");
		Vector2D mousePos = new Vector2D(e.getX(), e.getY());
			
		DragInfo.setLastMousePos (mousePos);

	}

}
