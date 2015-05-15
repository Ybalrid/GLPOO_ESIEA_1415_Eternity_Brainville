package main.java.model;

import main.java.controller.Game;

/* Manage and centralises resources */
public class ModelManager {
	
	private Game game;
	private Face[] faces;
	private Piece[] pieces;
	
	public ModelManager(Game game) {
		this.game = game;
		//this.faces = new ArrayList<Face>();
		//this.pieces = new ArrayList<Piece>();
	}

	public Face[] getFaces() {
		return faces;
	}
	
	public Face getFace(int id) {
		for (Face face : faces) {
			if (face.getId() == id)
				return face;
		};
		return null;
	}

	public void setFaces(Face[] faces) {
		this.faces = faces;
	}
	
	public Piece[] getPieces() {
		return pieces;
	}

	public Piece getPiece(int id) {
		for (Piece piece : pieces) {
			if (piece.getId() == id)
				return piece;
		};
		return null;
	}

	public void setPieces(Piece[] pieces) {
		this.pieces = pieces;
	}
	
	public void loadFaces(int id) {
		this.setFaces(new FaceDAO(String.format("%02d", id)).getFaces());
	}
	
	public void loadPieces(int id) {
		this.setPieces(new PieceDAO(String.format("%02d", id), this).getPieces());
	}
	
}
