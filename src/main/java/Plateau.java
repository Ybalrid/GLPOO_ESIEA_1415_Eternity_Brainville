package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Plateau extends JPanel {
	
	private Dimension dimensions;
	
	public Plateau(Dimension dim)
	{
		this.dimensions = dim;
		this.setSize(dim);
		this.setBackground(Color.BLACK);
	}
	
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.GREEN);
		g2.fillRect(50, 0, 200, 200);
		
	}
}
