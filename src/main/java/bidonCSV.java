package main.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class bidonCSV {
 public static void readAndPrint(String path)
 {
	 	System.out.println("reading " + path );
	 	try	
	 	{
	 		CSVReader reader = new CSVReader(new FileReader(path), ',', '"', 1);
	 	    List<String[]> allRows = reader.readAll();
	 	   //Read CSV line by line and use the string array as you want
	 	    for(String[] row : allRows)
	 	        System.out.println(Arrays.toString(row));
	 	    reader.close();
	 	}
	 	    
	 	catch(FileNotFoundException e)
	 	{
	 		System.out.println("File not found !!!! :'(");
	 	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
    
}
