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

public class MainWindow extends JFrame implements ActionListener {

	private int w = 800;
	private int h = 600;

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
		PuzzlePanel puzzle = new PuzzlePanel();
		JPanel containerEast = new JPanel();
		StockPanel stock = new StockPanel();
		// size preferences only useful if not in BorderLayout.CENTER
		containerEast.setPreferredSize(new Dimension(300, 600));
		stock.setPreferredSize(new Dimension(300, 300));

		containerEast.setLayout(new BorderLayout());
		containerEast.add(stock, BorderLayout.NORTH);
		containerEast.add(new JPanel(), BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(puzzle, BorderLayout.CENTER);
		this.add(containerEast, BorderLayout.EAST);
	}

	public void actionPerformed(ActionEvent arg0)
	{
		this.dispose();
	}

}
