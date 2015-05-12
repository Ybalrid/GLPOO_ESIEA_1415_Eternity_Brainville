package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import main.java.bidonCSV;
import main.java.gui.MainWindow;
import main.java.controller.Game;

public class Eternity {

	public static void main(String[] args) {
		//just messing around with CSV stuff here : 
		bidonCSV.readAndPrint("bidon.csv");
		
		new Game(new MainWindow().getGamePanel());
	}
}
