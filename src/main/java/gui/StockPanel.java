package main.java.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import main.java.gui.interaction.DropTarget;

/** Panel that contains the stock of pieces */
public class StockPanel extends DropTarget {

	public StockPanel()
	{
		this.setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

	}

	@Override
	public boolean acceptMultipleChilds() {
		return true;
	}
}
