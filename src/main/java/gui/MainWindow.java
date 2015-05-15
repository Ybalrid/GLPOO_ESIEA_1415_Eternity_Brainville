package main.java.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import main.java.controller.Game;
import main.java.model.SaveDAO;

public class MainWindow extends JFrame implements ActionListener {

	private GamePanel gamePanel;
	private HomePanel homePanel;

	private JMenuBar menuBar;
	private JMenu menuPartie;
	private JMenuItem itemQuitter;
	private JMenuItem itemSauver;
	private JMenuItem itemCharger;
	private JMenuItem itemNouvelle;
	private JFileChooser fc;
	
	private SaveDAO saveDao;
	
	public MainWindow(Game game)
	{
		super("Eternity");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		this.constructMenu();
		this.gamePanel = new GamePanel(game);
		this.homePanel = new HomePanel(game);

		this.setVisible(true);
		this.pack();
	}

	private void constructMenu()
	{
		//Create menu bar
		menuBar = new JMenuBar();
		
		//Create menu "Partie"
		menuPartie = new JMenu("Partie");

		itemNouvelle = new JMenuItem("Nouvelle partie");
		itemNouvelle.addActionListener(this);
		menuPartie.add(itemNouvelle);
		
		menuPartie.addSeparator();
		
		itemSauver = new JMenuItem("Sauvegarder la partie");
		itemSauver.addActionListener(this);
		menuPartie.add(itemSauver);

		itemCharger = new JMenuItem("Charger la partie");
		itemCharger.addActionListener(this);
		menuPartie.add(itemCharger);
		
		menuPartie.addSeparator();

		itemQuitter = new JMenuItem("Quitter");
		itemQuitter.addActionListener(this);
		menuPartie.add(itemQuitter);
		
		menuBar.add(menuPartie);
		this.setJMenuBar(menuBar);
		
		fc = new JFileChooser();
		//fc.addActionListener(this);
	}

	public void loadGamePanel() {
		// JFrame.add points to JContentPane.add
		// ContentPane has BorderLayout by default
		this.remove(this.homePanel);
		this.add(this.gamePanel);
		this.repaint();
		this.pack();
	}
	
	public void loadHomePanel() {
		this.remove(this.gamePanel);
		this.add(this.homePanel);
		this.repaint();
		this.pack();
	}

	public GamePanel getGamePanel() {
		return this.gamePanel;
	}
	
	public HomePanel getHomePanel() {
		return this.homePanel;
	}

	/*
	* Events response implementations
	*/
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == (Object)itemQuitter)
			this.dispose();
		
		if(e.getSource() == (Object)itemNouvelle)
		{
			//create new game
			this.gamePanel.getGame().restart();
		}
		
		if(e.getSource() == (Object)itemSauver)
		{
			//If save file location unknown, open a dialog for selecting a save file,
			//Save the game at the save file location
			fc.showSaveDialog(this);
			System.out.println("debug showOpenDialog");
			System.out.println("Information from the file : " + fc.getSelectedFile());
			
			if(fc.getSelectedFile() != null)
			{
				System.out.println("apparently a valid file has been selected...");
				saveDao = new SaveDAO(fc.getSelectedFile(), gamePanel.getGame().getModelManager());
				saveDao.save();
			}
			
		}
		
		if(e.getSource() == (Object)itemCharger)
		{
			fc.showOpenDialog(this);
			System.out.println("debug showOpenDialog");
			System.out.println("Information from the file : " + fc.getSelectedFile());
			if(fc.getSelectedFile() != null)
			{
				System.out.println("apparently a valid file has been selected...");
				saveDao = new SaveDAO(fc.getSelectedFile(), gamePanel.getGame().getModelManager());
				saveDao.load();
			}
			
		}
	}
}
