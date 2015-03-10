package main.java;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class MainWindow extends JPanel {
	
	private JFrame frame;
	private int w = 1024;
	private int h = 768;
	
	
	private JMenuBar menuBar;
	private JMenu menuFichier;
	
	private JMenuItem quitter_;
	
	public MainWindow()
	{
		frame = new JFrame("Eternity");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(w,h));
		frame.setBackground(Color.GRAY);
		
		constructMenu();
		constructContent();

		frame.setVisible(true);
	}
	
	private void constructMenu()
	{
		
		menuBar = new JMenuBar();
		menuFichier = new JMenu("Fichier");
		
		quitter_ = new JMenuItem("Quitter");
		quitter_.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				frame.dispose();
			}
		});		
		
		menuFichier.add(quitter_);
		
		menuBar.add(menuFichier);

	    frame.setJMenuBar(menuBar);
	}
	
	private void constructContent()
	{
		Plateau p = new Plateau();
		p.changeSize(200, 200);
	
		frame.add(p);
		p.setVisible(true);
		}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.BLUE);
		g2.fillRect(0, 0, 1024, 768);
	}

}
