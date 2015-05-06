package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BorderLayout;

import javax.swing.JPanel;

public class CellPanel extends JPanel {

	public CellPanel(MainWindow container)
	{
		this.addMouseListener(container);
		this.addMouseMotionListener(container);
		this.setLayout(new BorderLayout());
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
