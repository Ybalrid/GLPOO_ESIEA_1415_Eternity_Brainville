package main.java.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MainWindow extends JFrame implements ActionListener {

	private GamePanel gamePanel;

	private JMenuBar menuBar;
	private JMenu menuPartie;
	private JMenuItem itemQuitter;
	private JMenuItem itemSauver;
	private JMenuItem itemCharger;
	private JMenuItem itemNouvelle;

	public MainWindow()
	{
		super("Eternity");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.constructMenu();
		this.gamePanel = new GamePanel();
		// JFrame.add points to JContentPane.add
		// ContentPane has BorderLayout by default
		this.add(this.gamePanel);

		this.setVisible(true);
		this.pack();
	}

	private void constructMenu()
	{
		menuBar = new JMenuBar();
		menuPartie = new JMenu("Partie");


		itemNouvelle = new JMenuItem("Nouvelle partie");
		itemNouvelle.addActionListener(this);

		menuPartie.add(itemNouvelle);
		
		itemSauver = new JMenuItem("Sauvegarder la partie");
		itemSauver.addActionListener(this);

		menuPartie.add(itemSauver);

		itemCharger = new JMenuItem("Charger la partie");
		itemCharger.addActionListener(this);

		menuPartie.add(itemCharger);
		

		itemQuitter = new JMenuItem("Quitter");
		itemQuitter.addActionListener(this);

		menuPartie.add(itemQuitter);
		
		menuBar.add(menuPartie);

		this.setJMenuBar(menuBar);
	}

	public GamePanel getGamePanel() {
		return this.gamePanel;
	}

	/*
	* Events response implementations
	*/

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == (Object)itemQuitter)
			this.dispose();
	}
}
