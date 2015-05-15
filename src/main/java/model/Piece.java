package main.java.model;

public class Piece {

	private int id;
	private Face[] faces;
	private int positionX;
	private int positionY;
	private int orientation;

	public Piece (int i, Face[] f, int pX, int pY, int o)
	{
		this.id = i;
		this.faces = f;
		this.positionX = pX;
		this.positionY = pY;
		this.orientation = o;
	}

	public int getId() {
		return id;
	}

	public Face[] getFaces() {
		return faces;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public int getOrientation() {
		return orientation;
	}
	
	public void setOrientation(int orientation) {
		this.orientation = orientation % this.faces.length;
	}
	
	public Piece copy() {
		return new Piece(id, faces, positionX, positionY, orientation);
	}
	
	public boolean equals(Piece p) {
		return id == p.getId() && faces == p.getFaces() && positionX == p.getPositionX()
		&& positionY == p.getPositionY() && orientation == p.getOrientation();
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Piece(").append(id).append(",[");
		for (Face face : this.faces) {
			s.append(face.getId()).append(",");
		}
		s.append("],(").append(positionX).append(",").append(positionY)
		.append("),").append(orientation).append(")");
		return s.toString();
	}
}
