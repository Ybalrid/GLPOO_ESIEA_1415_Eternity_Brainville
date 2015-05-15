package main.java.model;

import java.awt.List;
import java.io.File;
import java.util.ArrayList;

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
		//Read the CSV file
		//Apply content to the game (create faces and pieces and so on
		//Relaunch it with theses data
	}
	
	public void save()
	{
		System.out.println("Saving");
		//Piece[] pieces = modelManager.getPieces();
		Game game =  modelManager.getGame();
		
		Piece[] pieces = game.getGamePanel().getOrderedPieces();
		ArrayList<String[]> gameContent = new ArrayList<String[]>();

		int debug = 0;
		for(Piece p : pieces)
		{
			//Piece ID, Piece Orient, F1 ID, F1 Pat, F1 bg, F1 FG, F2 ID, F2 pat... etc..
			String[] pieceDesc = new String[18];
			
			//Integer id = new Integer(p.getId());
			
			pieceDesc[0] = new Integer(p.getId()).toString();
			pieceDesc[1] = new Integer(p.getOrientation()).toString();
			for(int i = 0; i < 4; i++)
			{
				pieceDesc[2 + 0 + 4*i] = new Integer(p.getFaces()[i].getId()).toString();
				pieceDesc[2 + 1 + 4*i] = p.getFaces()[i].getPattern();
				pieceDesc[2 + 2 + 4*i] = p.getFaces()[i].getBgColor();
				pieceDesc[2 + 3 + 4*i] = p.getFaces()[i].getFgColor();
			}
			
			for(String s : pieceDesc)
				System.out.println(s);
			
			gameContent.add(pieceDesc);
			/*System.out.println("Peice : " + ++debug + " id " + p.getId() + " orientation " + p.getOrientation() + " x:" + p.getPositionX() + " y:" +  p.getPositionY());
			for(Face f : p.getFaces())
			{
				System.out.println(" Face : " + f.getId() + " patern " + f.getPattern() + " " + f.getBgColor() + " " + f.getFgColor());
			}*/
			
			
		}
		
		this.writeLineFromArray(gameContent);
		
		//Get all information in text form
		//create an Array<Strings> that represent the content of the CSV to write
		//wite it with 
		//this.writeLineFromArray(array);
	}

}
