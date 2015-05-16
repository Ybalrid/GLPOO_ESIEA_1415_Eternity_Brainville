package main.java.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.BasicStroke;

import javax.swing.JPanel;

import main.java.model.Piece;
import main.java.controller.PieceSynthetizer;
import main.java.gui.interaction.DragTarget;

public class PiecePanel extends DragTarget {

	private Piece piece;
	private BufferedImage img;
	private double rotation; // Angle in radians
	private boolean selected;

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
			BufferedImage img;
			
			if (this.selected) {
				System.out.println("Drawing selected PiecePanel");
				img = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);
				RescaleOp rescaleOp = new RescaleOp(1.2f, 40, null);
				rescaleOp.filter(this.img, img);
			} else img = this.img;
			
			g2.rotate(-rotation, 50, 50);
			g2.drawImage(img, (pw - iw)/2, (ph - ih)/2, 100, 100, null);
			g2.rotate(rotation, 50, 50);
			
			if (this.selected) {
				g2.setColor(Color.WHITE);
				g2.setStroke(new BasicStroke(2));
				g2.drawRect(0, 0, 99, 99);
			}
		}
		g2.dispose();
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
	
	@Override
	public void setRotation(double rotation) {
		this.rotation = rotation;
		this.piece.setOrientation((int)Math.round(rotation / Math.PI*2));
	}
	
	@Override
	public void rotate(boolean clockwise) {
		this.setRotation(this.rotation + (clockwise ? -Math.PI/2 : Math.PI/2));
		this.repaint();
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		this.repaint();
	}
	
}
