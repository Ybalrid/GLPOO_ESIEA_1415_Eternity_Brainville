package main.java.model;

import java.io.File;

public class SaveDAO extends CsvDAO {
	
	private ModelManager modelManager;
	
	public SaveDAO(File file, ModelManager mm)
	{
		super(file);
		modelManager = mm;
		System.out.println("Save file DAO created");
	}
	
	public void load()
	{
		System.out.println("Loading");
	}
	
	public void save()
	{
		System.out.println("Saving");
	}

}
