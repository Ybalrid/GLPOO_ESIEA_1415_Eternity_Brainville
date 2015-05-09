package main.java.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.java.gui.interaction.DragTarget;

public class ImagePanel extends DragTarget {

	private BufferedImage img;
	private double rotation; // Angle in radians
	private InteractiveContainer container;

	public ImagePanel(InteractiveContainer container) {
		this.addMouseListener(container);
		this.addMouseMotionListener(container);
		this.container = container;
		this.setPreferredSize(new Dimension(100,100));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		//System.out.println("cell width & height: " + this.getWidth() + " " +this.getHeight());
		if (this.img != null) {
			int pw = this.getWidth(), ph = this.getHeight();
			int iw = this.img.getWidth(), ih = this.img.getHeight();
			g2.rotate(-rotation, 50, 50);
			g2.drawImage(this.img, (pw - iw)/2, (ph - ih)/2, 100, 100, null);
			g2.rotate(rotation, 50, 50);
		}
	}

	public BufferedImage getImg() {
		return this.img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public double getRotation() {
		return this.rotation;
	}
	public void rotate(boolean clockwise) {
		this.rotation += (clockwise ? -Math.PI/2 : Math.PI/2);
		this.repaint();
	}
}
