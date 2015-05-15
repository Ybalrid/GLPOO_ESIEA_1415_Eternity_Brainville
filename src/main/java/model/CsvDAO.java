package main.java.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public abstract class CsvDAO {
	
	private File file;
	private final static char SEPARATOR = ' ';
	
	public CsvDAO (String filename) {
		this.file = new File(filename);
	}
	
	public CsvDAO(File fileObject)
	{
		this.file = fileObject;
	}
	
	protected List<String[]> getLinesFromFile() {
		return getLinesFromFile(2);
	}

	protected List<String[]> getLinesFromFile(int startLine) {

		CSVReader csvReader;
		List<String[]> lines = null;
		
		try {
			csvReader = new CSVReader(new FileReader(file), SEPARATOR, '"', startLine);
			lines = csvReader.readAll();
			csvReader.close();
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
		
		return lines;
	}
	
	protected void writeLineFromArray(List<String[]> array)
	{
		CSVWriter csvWriter;
		
		try
		{
			csvWriter = new CSVWriter(new FileWriter(file), SEPARATOR, '"');
			csvWriter.writeAll(array);
			csvWriter.close();
			
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }	
	}
}
