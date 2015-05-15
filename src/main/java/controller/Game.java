package main.java.controller;

import java.util.Timer;
import java.util.TimerTask;

import main.java.gui.MainWindow;
import main.java.gui.GamePanel;
import main.java.gui.HomePanel;
import main.java.model.Face;
import main.java.model.Piece;
import main.java.model.Solution;
import main.java.model.ModelManager;

public class Game {

	private MainWindow window;
	private GamePanel gamePanel;
	private HomePanel homePanel;
	private int currentLevel;
	private final int levelCount = 4;
	public static final Timer TIMER = new Timer();
	
	private Solution solution; // Order of pieces: left-right, then top-bottom
	private ModelManager modelManager;

	public Game() {
		this.modelManager = new ModelManager(this);
	}
	
	public void init(MainWindow window) {
		this.window = window;
		this.gamePanel = window.getGamePanel();
		this.homePanel = window.getHomePanel();
		
		this.startHome();
		//this.loadLevel(1);
	}
	
	public int getLevelCount() {
		return this.levelCount;
	}

	public void startGame() {
		this.currentLevel = 0;
		this.window.loadGamePanel();
	}
	
	public void startHome() {
		this.currentLevel = 0;
		this.window.loadHomePanel();
	}	

	public void loadLevel(int levelId) {
		this.gamePanel.destroyPiecePanels();
		this.gamePanel.destroyGrid();
		
		this.modelManager.loadFaces(levelId);
		this.modelManager.loadPieces(levelId);
		
		Piece[] pieces = this.modelManager.getPieces(), piecesCopy = new Piece[pieces.length];
		// Array copy
		for (int i = 0; i < pieces.length; i++) piecesCopy[i] = pieces[i].copy();

		this.solution = new Solution(pieces);
		this.gamePanel.createGrid(piecesCopy.length);
		this.gamePanel.createPiecePanels(piecesCopy);
		
		this.currentLevel = levelId;
	}

	public void checkSolution() {
		Piece[] pieces = this.gamePanel.getOrderedPieces();
		if (pieces == null) return;
		int i;
		
		for (i = 0; i < this.solution.getSize(); i++) {
			//System.out.println("Piece equality: " + pieces[i] + " " + this.solution.get(i) + " " + !pieces[i].equals(this.solution.get(i)));
			if (!pieces[i].equals(this.solution.get(i)))
				break;
		}
		System.out.println("");
		if (i == this.solution.getSize()) {
			System.out.println("\n*** You just won the game ***\n");
			
			TIMER.schedule(new TimerTask() {
				@Override public void run() {
					if (currentLevel == levelCount) {
						startHome();
					} else {
						loadLevel(currentLevel + 1);
					}
				}
			}, 2000);
		}
	}
}

