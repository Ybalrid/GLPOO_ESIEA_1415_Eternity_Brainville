package main.java.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class FaceDAO {
	
	private File file;
	private final static char SEPARATOR = ' ';
	
	public FaceDAO() {
		this.file = new File("res/faces-01.csv");
	}
	
	public FaceDAO(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}

	private List<String[]> getLinesFromFile() {

		CSVReader csvReader;
		List<String[]> lignes = null;
		
		try {
			csvReader = new CSVReader(new FileReader(file), SEPARATOR, '"', 2);
			lignes = csvReader.readAll();
			csvReader.close();
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		
		return lignes;
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
   
   public static void main(String[] args) {
	   FaceDAO fd = new FaceDAO();
	   for (Face f : fd.getFaces()) {
		   System.out.println(f);
	   }
	   
   }

}
