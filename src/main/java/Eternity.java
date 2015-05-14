package main.java;

import main.java.controller.Game;
import main.java.gui.MainWindow;

public class Eternity {

	public static void main(String[] args) {
		new Game(new MainWindow().getGamePanel());
	}
}
