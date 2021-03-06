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
import main.java.gui.interaction.KeyShortcuts;
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
		new KeyShortcuts(this);

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
		if(e.getSource() == itemQuitter)
			this.dispose();
		
		if(e.getSource() == itemNouvelle)
		{
			// Create new game
			this.gamePanel.getGame().startHome();
		}
		
		if(e.getSource() == itemSauver)
		{
			if (this.gamePanel.getGame().getCurrentLevelId() == 0) return;
			// If save file location unknown, open a dialog for selecting a save file,
			// save the game at the save file location
			fc.showSaveDialog(this);
			if(fc.getSelectedFile() != null)
			{
				saveDao = new SaveDAO(fc.getSelectedFile(), gamePanel.getGame().getModelManager());
				saveDao.save();
				saveDao = null;
			}
			
		}
		
		if(e.getSource() == itemCharger)
		{
			fc.showOpenDialog(this);
			if(fc.getSelectedFile() != null)
			{
				saveDao = new SaveDAO(fc.getSelectedFile(), gamePanel.getGame().getModelManager());
				saveDao.load();
				saveDao = null;
			}
			
		}
	}
}
