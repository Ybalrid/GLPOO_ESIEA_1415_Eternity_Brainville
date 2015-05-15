package main.java.model;

import java.io.File;

import main.java.controller.Game;

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
		//Piece[] pieces = modelManager.getPieces();
		Game game =  modelManager.getGame();
		
		Piece[] pieces = game.getGamePanel().getOrderedPieces();
		
		int debug = 0;
		for(Piece p : pieces)
		{
			System.out.println("Peice : " + ++debug + " id " + p.getId() + " orientation " + p.getOrientation() + " x:" + p.getPositionX() + " y:" +  p.getPositionY());
			for(Face f : p.getFaces())
			{
				System.out.println(" Face : " + f.getId() + " patern " + f.getPattern() + " " + f.getBgColor() + " " + f.getFgColor());
			}
		}
	}

}
