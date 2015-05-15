package main.java.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.java.model.Piece;
import main.java.controller.PieceSynthetizer;
import main.java.gui.interaction.DragTarget;

public class PiecePanel extends DragTarget {

	private Piece piece;
	private BufferedImage img;
	private double rotation; // Angle in radians


	public PiecePanel() {
		this.setPreferredSize(new Dimension(100, 100));
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

	public Piece getPiece() {
		return this.piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
		this.img = PieceSynthetizer.synthetize(piece);
		this.repaint();
	}

	public double getRotation() {
		return this.rotation;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
		this.piece.setOrientation((int)Math.round(rotation / Math.PI*2));
	}
	
	public void rotate(boolean clockwise) {
		this.setRotation(this.rotation + (clockwise ? -Math.PI/2 : Math.PI/2));
		this.repaint();
	}
}
