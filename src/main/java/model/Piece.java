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

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Piece(").append(id).append(",[");
		for (Face face : this.faces) {
			s.append(face).append(",");
		}
		s.append("],(").append(positionX).append(",").append(positionY)
		.append("),").append(orientation);
		return s.toString();
	}
}
