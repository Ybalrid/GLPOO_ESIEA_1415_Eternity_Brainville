package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PuzzlePanel extends JPanel {

	public PuzzlePanel()
	{
		this.setBackground(Color.BLACK);
		
		this.setLayout(new GridLayout(4, 4));
		for (int i = 0; i < 16; i++) {
			JPanel p = new JPanel();
			p.setBackground(new Color(i * 16, i * 16, i * 16));
			this.add(p);
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

	}
}
