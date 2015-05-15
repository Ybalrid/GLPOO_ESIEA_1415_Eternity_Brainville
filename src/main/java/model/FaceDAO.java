package main.java.model;

import java.util.List;

public class FaceDAO extends CsvDAO {
	
	public FaceDAO(String id) {
		super ("res/faces-" + id + ".csv");
	}
	
   public Face[] getFaces() {
	   List<String[]> lines = this.getLinesFromFile();
	   Face[] faces = new Face[lines.size()];
 	   String[] values;
 	   int id;
 	   String bgColor, fgColor, pattern;
	   
	   for (int i = 0; i < lines.size(); i++) {
		   values = lines.get(i);
		   id = new Integer(values[1]);
		   bgColor = values[2];
		   if (bgColor.equals("black")) {
			   pattern = "line";
			   fgColor = "black";
		   } else {
			   pattern = values[3];
			   fgColor = values[4];
		   }
		   faces[i] = new Face(id, bgColor, fgColor, pattern);
	   }
	   return faces;
   }

}
