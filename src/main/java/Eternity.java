package main.java;

import main.java.gui.MainWindow;
import main.java.controller.Game;

public class Eternity {

	public static void main(String[] args) {
		new Game(new MainWindow().getGamePanel());
	}
}
