package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class MainWindow extends JFrame implements ActionListener {
	
	private int w = 1024;
	private int h = 768;
	
	private JMenuBar menuBar;
	private JMenu menuFichier;
	
	private JMenuItem itemQuitter;
	
	public MainWindow(String title)
	{
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(w,h));
		this.setBackground(Color.GRAY);
		
		constructMenu();
		constructContent();

		this.pack();
		this.setVisible(true);
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
		Plateau p = new Plateau(new Dimension(200, 200));
	
		this.add(p);

	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		this.dispose();
	}

}
