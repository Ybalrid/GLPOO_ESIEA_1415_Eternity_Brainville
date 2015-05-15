package main.java;

import main.java.controller.Game;
import main.java.gui.MainWindow;

public class Eternity {

	public static void main(String[] args) {
		Game game = new Game();
		MainWindow win = new MainWindow(game);
		game.init(win);
	}
}
