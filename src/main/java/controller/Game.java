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

/* Manages the gameplay. Main controller of the project. */
public class Game {

	private MainWindow window;
	private GamePanel gamePanel;
	private HomePanel homePanel;
	private int currentLevel;
	private final int levelCount = 5;
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
	
	public GamePanel getGamePanel(){
		return this.gamePanel;
	}
	
	public int getLevelCount() {
		return this.levelCount;
	}
	
	public void restart()
	{
		startHome();
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
		// Destroy level
		this.gamePanel.destroyPiecePanels();
		this.gamePanel.destroyGrid();
		
		// Load solution
		this.modelManager.loadFaces(levelId);
		this.modelManager.loadPieces(levelId);
		
		// Get solution, and deduce playable pieces
		Piece[] pieces = this.modelManager.getPieces(), piecesCopy = new Piece[pieces.length];
		for (int i = 0; i < pieces.length; i++)
			piecesCopy[i] = pieces[i].copy(); // Array copy

		// Create level
		this.solution = new Solution(pieces, 4);
		this.gamePanel.createGrid(piecesCopy.length);
		this.gamePanel.createPiecePanels(piecesCopy);
		
		// Set current level id
		this.currentLevel = levelId;
	}
	
	public ModelManager getModelManager() 
	{
		return this.modelManager;
	}

	/* Checks whether level has been solved.
	 * If so, transition to next one.
	 * If there is no level left, go to home screen */
	public void checkSolution() {
		Piece[] pieces = this.gamePanel.getOrderedPieces();
		if (pieces == null) return;
		int i;
		
		// Checking by identification
		for (i = 0; i < this.solution.getSize(); i++) {
			//System.out.println("Piece equality: " + pieces[i] + " " + this.solution.get(i) + " " + !pieces[i].equals(this.solution.get(i)));
			if (!pieces[i].equals(this.solution.get(i)))
				break;
		}
		//System.out.println("");
		
		// If solved, go to next
		if (i == this.solution.getSize()) {
			System.out.println("\n*** You just won the game ***\n");
			
			// Deferred call to next level
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

