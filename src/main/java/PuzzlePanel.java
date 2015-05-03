package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PuzzlePanel extends JPanel {

	private CellPanel[] cellPanels;

	public PuzzlePanel()
	{
		
		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(4, 4));
		
		this.cellPanels = new CellPanel[16];
		for (int i = 0; i < 16; i++) {
			CellPanel p = new CellPanel(this);
			p.setBackground(new Color(i * 16, i * 16, i * 16));
			this.add(p);
			this.cellPanels[i] = p;
		}
		
		Face[] faces1 = {new Face(0, "blue", "purple", "zigzag"), new Face(0, "purple", "red", "circle"), new Face(0, "green", "blue", "triangle"), new Face(0, "red", "yellow", "")};
		Face[] faces2 = {new Face(0, "", "", ""), new Face(0, "yellow", "red", ""), new Face(0, "yellow", "green", ""), new Face(0, "purple", "red", "")};
		Piece[] pieces = {new Piece(0, faces1, 0, 0, 0), new Piece(0, faces2, 0, 0, 0)};
		
		
		for (int i = 0; i < pieces.length; i++)
		{
			this.cellPanels[i].setImg(PieceSynthetizer.synthetize(pieces[i]));
			this.cellPanels[i].repaint();
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
