package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Container;

import javax.swing.JPanel;

public class ImagePanel extends DragTarget {

	private BufferedImage img;
	private MainWindow container;

	public ImagePanel(MainWindow container) {
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
			g2.drawImage(this.img, (pw - iw)/2, (ph - ih)/2, 100, 100, null);
		}
	}

	public BufferedImage getImg() {
		return this.img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
}
