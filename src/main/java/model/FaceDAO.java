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
		   if (values.length <= 1) continue;
		   id = new Integer(values[1]);
		   bgColor = values[2];
		   if (values.length <= 3) {
			   pattern = "square";
			   fgColor = bgColor;
		   } else {
			   pattern = values[3];
			   fgColor = values[4];
		   }
		   faces[i] = new Face(id, bgColor, fgColor, pattern);
	   }
	   return faces;
   }

}
