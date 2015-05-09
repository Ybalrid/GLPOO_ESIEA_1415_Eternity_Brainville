package main.java.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MainWindow extends JFrame implements ActionListener {

	private InteractiveContainer interactiveContainer;

	private JMenuBar menuBar;
	private JMenu menuPartie;
	private JMenuItem itemQuitter;

	public MainWindow()
	{
		super("Eternity");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.constructMenu();
		this.interactiveContainer = new InteractiveContainer();
		// JFrame.add points to JContentPane.add
		// ContentPane has BorderLayout by default
		this.add(this.interactiveContainer);

		this.setVisible(true);
		this.pack();
	}

	private void constructMenu()
	{
		menuBar = new JMenuBar();
		menuPartie = new JMenu("Partie");

		itemQuitter = new JMenuItem("Quitter");
		itemQuitter.addActionListener(this);

		menuPartie.add(itemQuitter);

		menuBar.add(menuPartie);

		this.setJMenuBar(menuBar);
	}

	/*
	* Events response implementations
	*/

	public void actionPerformed(ActionEvent e)
	{
		this.dispose();
	}
}
