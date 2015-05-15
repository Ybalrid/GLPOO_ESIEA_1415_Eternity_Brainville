package main.java.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public abstract class CsvDAO {
	
	private File file;
	private final static char SEPARATOR = ' ';
	
	public CsvDAO (String filename) {
		this.file = new File(filename);
	}
	
	protected List<String[]> getLinesFromFile() {

		CSVReader csvReader;
		List<String[]> lines = null;
		
		try {
			csvReader = new CSVReader(new FileReader(file), SEPARATOR, '"', 2);
			lines = csvReader.readAll();
			csvReader.close();
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		
		return lines;
	}
}
