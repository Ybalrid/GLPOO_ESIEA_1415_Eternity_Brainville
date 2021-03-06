package main.java.model;

import java.awt.List;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
		Game game = modelManager.getGame();

		System.out.println("Loading");
		//Read the CSV file
		//Apply content to the game (create faces and pieces and so on
		//Relaunch it with theses date
		
		java.util.List<String[]> gameContent = this.getLinesFromFile(0);
		if(gameContent.size() != 16) 
		{
			JOptionPane.showMessageDialog(game.getGamePanel(),
					"Fichier invalide", "Error", 0);
			return;
		}
		
		for(String[] strings : gameContent)
			for(String string : strings)
				System.out.println(string);
		
		Piece[] pieces = new Piece[16];
		
		for(int i = 0; i < 16; i++)
		{
			//peices[i] = new Piece()
		}
	
		
		
		
	}
	
	public void save()
	{
		System.out.println("Saving");
		//Piece[] pieces = modelManager.getPieces();
		Game game =  modelManager.getGame();
		
		Piece[] pieces = game.getGamePanel().getOrderedPieces();
		//System.out.println( pieces.length);
		
		if(pieces == null)
		{
			JOptionPane.showMessageDialog(game.getGamePanel(),
					"Veuillez placer toutes les pièces sur le tableau de jeu pour sauvegarder...", "Error", 0);
			return;
		}
		
		ArrayList<String[]> gameContent = new ArrayList<String[]>();

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
			
			//for(String s : pieceDesc)
				//System.out.println(s);
			
			gameContent.add(pieceDesc);
		}
		this.writeLineFromArray(gameContent);
		System.out.println("Saved!");
	}

}
