package main.java.controller;

import java.util.Arrays;

import main.java.gui.GamePanel;
import main.java.model.Face;
import main.java.model.Piece;
import main.java.model.Solution;
import main.java.model.ModelManager;

public class Game {

	private GamePanel gamePanel;
	private Solution solution; // Order of pieces: left-right, then top-bottom
	private ModelManager modelManager;

	public Game(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		gamePanel.setGame(this);
		this.modelManager = new ModelManager(this);

		this.loadLevel(1);
	}

	private void loadLevel(int levelId) {
		/*
		Face f0 = new Face(0, "", "", "");
		Face f1 = new Face(1, "blue", "white", "zigzag");
		Face f2 = new Face(2, "purple", "red", "circle");
		Face f3 = new Face(3, "green", "blue", "triangle");
		Face f4 = new Face(4, "red", "yellow", "line");
		Piece p0 = new Piece(0, new Face[]{f0, f3, f3, f0}, 0, 0, 0);
		Piece p1 = new Piece(1, new Face[]{f0, f2, f4, f3}, 1, 0, 0);
		Piece p2 = new Piece(2, new Face[]{f0, f4, f4, f2}, 2, 0, 0);
		Piece p3 = new Piece(3, new Face[]{f0, f0, f3, f4}, 3, 0, 0);
		Piece p4 = new Piece(4, new Face[]{f3, f1, f3, f0}, 0, 1, 0);
		Piece p5 = new Piece(5, new Face[]{f4, f3, f1, f1}, 1, 1, 0);
		Piece p6 = new Piece(6, new Face[]{f4, f1, f2, f3}, 2, 1, 0);
		Piece p7 = new Piece(7, new Face[]{f3, f0, f2, f1}, 3, 1, 0);
		Piece p8 = new Piece(8, new Face[]{f3, f4, f4, f0}, 0, 2, 0);
		Piece p9 = new Piece(9, new Face[]{f1, f3, f1, f4}, 1, 2, 0);
		Piece p10 = new Piece(10, new Face[]{f2, f1, f4, f3}, 2, 2, 0);
		Piece p11 = new Piece(11, new Face[]{f2, f0, f2, f1}, 3, 2, 0);
		Piece p12 = new Piece(12, new Face[]{f4, f3, f0, f0}, 0, 3, 0);
		Piece p13 = new Piece(13, new Face[]{f1, f2, f0, f3}, 1, 3, 0);
		Piece p14 = new Piece(14, new Face[]{f4, f4, f0, f2}, 2, 3, 0);
		Piece p15 = new Piece(15, new Face[]{f2, f0, f0, f4}, 3, 3, 0);

		Piece[] pieces = {p15, p14, p13, p12, p11, p10, p9, p8, p7, p6, p5, p4, p3, p2, p1, p0};
		*/
		
		this.modelManager.loadFaces(levelId);
		this.modelManager.loadPieces(levelId);
		
		Piece[] pieces = this.modelManager.getPieces(), piecesCopy = new Piece[pieces.length];
		// Array copy
		for (int i = 0; i < pieces.length; i++) piecesCopy[i] = pieces[i].copy();

		this.solution = new Solution(pieces);
		this.gamePanel.createPiecePanels(piecesCopy);
	}

	public void checkSolution() {
		Piece[] pieces = this.gamePanel.getOrderedPieces();
		if (pieces == null) return;
		int i;
		
		for (i = 0; i < this.solution.getSize(); i++) {
			//System.out.println("Piece equality: " + pieces[i] + " " + this.solution.get(i) + " " + !pieces[i].equals(this.solution.get(i)));
			if (!pieces[i].equals(this.solution.get(i)))
				break;
		}
		System.out.println("");
		if (i == this.solution.getSize()) {
			System.out.println("\n*** You just won the game ***\n");
		}
	}

}

