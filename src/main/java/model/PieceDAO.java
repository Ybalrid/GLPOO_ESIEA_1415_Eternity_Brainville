package main.java.model;

import java.util.List;

public class PieceDAO extends CsvDAO {
	
	private ModelManager modelManager;

	public PieceDAO(String id, ModelManager modelManager) {
		super ("res/pieces-" + id + ".csv");
		this.modelManager = modelManager;
	}
		
   public Piece[] getPieces() {
	   List<String[]> lines = this.getLinesFromFile();
	   Piece[] pieces = new Piece[lines.size()];
 	   String[] values;
 	   int id;
 	   Face[] faces;
	   
	   for (int i = 0; i < lines.size(); i++) {
		   faces = new Face[4];
		   values = lines.get(i);
		   id = new Integer(values[1]);
		   faces[0] = this.modelManager.getFace(new Integer(values[2]));
		   faces[1] = this.modelManager.getFace(new Integer(values[3]));
		   faces[2] = this.modelManager.getFace(new Integer(values[4]));
		   faces[3] = this.modelManager.getFace(new Integer(values[5]));
		   
		   pieces[i] = new Piece(id, faces, 0, 0, 0);
	   }
	   return pieces;
   }
	
}
