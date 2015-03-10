package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Plateau extends JPanel {
	
	private JFrame frame;
	private int w, h;
	public Plateau()
	{
		frame = new JFrame();
		frame.setResizable(false);
	}
	
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.GREEN);
		g2.fillRect(0, 0, w, h);
		System.out.println("Size : " + w + " " + h);
		
	}

	public void changeSize(int w, int h)
	{
		this.w = w;
		this.h = h;
		frame.setSize(w, h);
	}

	
}
