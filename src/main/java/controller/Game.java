package main.java.controller;

import main.java.gui.GamePanel;
import main.java.model.Face;
import main.java.model.Piece;
import main.java.model.Solution;

public class Game {

	private GamePanel gamePanel;
	private Solution solution; // Order of pieces: left-right, then up-down

	public Game(GamePanel gamePanel) {
		this.gamePanel = gamePanel;

		this.loadLevel();
	}

	private void loadLevel() {

		Face[] faces1 = {new Face(0, "blue", "purple", "zigzag"), new Face(0, "purple", "red", "circle"), new Face(0, "green", "blue", "triangle"), new Face(0, "red", "yellow", "")};
		Face[] faces2 = {new Face(0, "", "", ""), new Face(0, "yellow", "red", ""), new Face(0, "yellow", "green", ""), new Face(0, "purple", "red", "")};
		Face[] faces3 = {new Face(0, "purple", "red", "circle"), new Face(0, "blue", "purple", "zigzag"), new Face(0, "", "", ""), new Face(0, "", "", "")};
		Piece[] pieces = {new Piece(0, faces1, 0, 0, 0), new Piece(0, faces2, 0, 0, 0), new Piece(0, faces1, 0, 0, 0), new Piece(0, faces3, 0, 0, 0), new Piece(0, faces2, 0, 0, 0)};

		this.gamePanel.createPiecePanels(pieces);
	}

}
