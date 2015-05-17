package main.java.gui;

import java.util.ArrayList;
import java.util.Collections;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.model.Piece;
import main.java.controller.Game;
import main.java.gui.interaction.DragInfo;
import main.java.gui.interaction.DragTarget;
import main.java.gui.interaction.DropTarget;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

	private Game game;
	private ArrayList<DragTarget> dragTargets;
	private ArrayList<DropTarget> dropTargets;

	private PuzzlePanel puzzle;
	private JPanel containerEast;
	private StockPanel stock;

	public static final DragInfo dragInfo = new DragInfo();
	private boolean interactionAllowed;

	// For a GamePanel without game logic interaction
	public GamePanel() {
		// setPreferredSize rather than setSize because of layout manager
		// The contentPane excludes the menu
		this.setPreferredSize(new Dimension(720,400));
		this.setBackground(Color.GREEN);

		this.puzzle = new PuzzlePanel();
		this.containerEast = new JPanel();
		this.stock = new StockPanel();

		// size preferences only useful if not in BorderLayout.CENTER
		this.containerEast.setPreferredSize(new Dimension(320, 600));
		this.stock.setPreferredSize(new Dimension(320, 320));

		containerEast.setLayout(new BorderLayout());
		containerEast.add(this.stock, BorderLayout.NORTH);
		containerEast.add(new JPanel(), BorderLayout.CENTER);

		this.setLayout(new BorderLayout());
		this.add(this.puzzle, BorderLayout.CENTER);
		this.add(this.containerEast, BorderLayout.EAST);

		// Mouse interaction
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.interactionAllowed = true;
		this.dropTargets = new ArrayList<DropTarget>();
		this.dragTargets = new ArrayList<DragTarget>();
	}

	// For a GamePanel with game logic interaction
	public GamePanel(Game game) {
		this();
		this.game = game;
	}

	public void createGrid(int count) {

		for (int i = 0; i < count; i++) {
			CellPanel p = new CellPanel();
			p.setBackground(new Color(i * 16 + 8, i * 16 + 8, i * 16 + 8));
			this.puzzle.add(p, BorderLayout.WEST);
			this.dropTargets.add(p);
		}
		this.dropTargets.add(stock); // Must add it after cell panels!
	}
	
	public void destroyGrid() {
		
		for (int i = 0; i < this.dropTargets.size() - 1; i++) {
			DropTarget dt = this.dropTargets.get(i);
			dt.getParent().remove(dt);
		}
		this.dropTargets.clear();
		
		this.puzzle.validate();
		this.stock.validate();
	}

	public void createPiecePanels(Piece[] pieces) {
		
		int len = pieces.length;
		ArrayList<Integer> indices = new ArrayList<Integer>(len);
		for (int i = 0; i < len; i++) indices.add(i);
		Collections.shuffle(indices);
		
		for (int i = 0; i < len; i++) {
			PiecePanel p = new PiecePanel();
			p.setBackground(Color.BLACK);
			p.setPiece(pieces[i]);
			
			p.setRotation(Math.PI/2 * (int)(Math.random()*4));
			p.repaint();
			//this.stock.add(p, BorderLayout.WEST);
			this.dropTargets.get(indices.get(i)).add(p, BorderLayout.WEST);
			this.dragTargets.add(p);
		}
		this.puzzle.validate();
		//this.stock.validate();
	}
	
	public void destroyPiecePanels() {
		
		for (int i = 0; i < this.dragTargets.size(); i++) {
			DragTarget dt = this.dragTargets.get(i);
			dt.getParent().remove(dt);
		}
		this.dragTargets.clear();
		
		this.puzzle.validate();
		this.stock.validate();
	}

	private ArrayList<PiecePanel> getOrderedPiecePanels() {
		int len = this.dropTargets.size() - 1;
		ArrayList<PiecePanel> comps = new ArrayList<PiecePanel>(len);
		for (int i = 0; i < len; i++)
		{
			CellPanel cellPanel = (CellPanel)this.dropTargets.get(i);
			if (cellPanel.getComponentCount() > 0) {
				comps.add((PiecePanel)cellPanel.getComponent(0));
			}
		}
		return comps;
	}

	/**
	 * If the grid is full, return the array of Piece, ordered left-right
	 * then top-bottom.
	 * Otherwise, return null.
	 */
	public Piece[] getOrderedPieces() {
		ArrayList<PiecePanel> piecePanels = this.getOrderedPiecePanels();
		int len = piecePanels.size();
		if (len != 16) return null;

		Piece[] pieces = new Piece[len];
		for (int i = 0; i < len; i++) {
			pieces[i] = piecePanels.get(i).getPiece();
			////System.out.println(pieces[i]);
		}
		return pieces;
	}
	
	public ArrayList<DropTarget> getDropTargets() {
		return this.dropTargets;
	}
	
	public ArrayList<DragTarget> getDragTargets() {
		return this.dragTargets;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public boolean isInteractionAllowed() {
		return this.interactionAllowed;
	}
	
	public void setInteractionAllowed(boolean interactionAllowed) {
		this.interactionAllowed = interactionAllowed;
	}

	public void rotateDragTarget(DragTarget dt, boolean clockwise) {
		if (!this.isInteractionAllowed() || dt == null) return;
		dt.rotate(clockwise);
		this.update();
	}
	
	// Update game logic associated to this component 
	public void update() {
		if (this.game == null) return;
		this.game.checkSolution();
	}

	/*
	* Events response implementations
	*/

	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {

		Point globalPos = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), this);
		//System.out.print("[Pressed] " + e.getButton() + " (" + globalPos.x + "," + globalPos.y + ") ");
		if (!this.isInteractionAllowed()) return;
		
		int pressedButton = e.getButton();
		// Get what is under the cursor
		Component pointed = this.findComponentAt(globalPos);

		if (pointed instanceof DragTarget) {
			DragTarget dragTarget = (DragTarget)pointed;
			
			if (pressedButton == MouseEvent.BUTTON3) {
				// On right click, rotate
				this.rotateDragTarget(dragTarget, true);

			} else if (pressedButton == MouseEvent.BUTTON1) {
				// Reset drag info when pressing so that target remains selected until now
				this.dragInfo.reset();
				this.dragInfo.setDraggingButton(pressedButton);
				
				// Init selection
				DropTarget dropTarget = (DropTarget)dragTarget.getParent();
				this.dragInfo.setSelection(dragTarget);
				this.dragInfo.setOrigin(dropTarget);
				
				// Mouve dragTraget so that mouse is a at the center
				dragTarget.setLocation(globalPos.x - dragTarget.getWidth()/2, globalPos.y - dragTarget.getHeight()/2);
				
				// Attach to GamePanel in order to move freely
				dropTarget.remove(dragTarget);
				dropTarget.repaint();
				this.add(dragTarget, 0);
				this.repaint();
			}
		} else {
			this.dragInfo.setSelection(null);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point globalPos = e.getPoint();
		//System.out.println("[Released] " + e.getButton() + " (" + globalPos.x + "," + globalPos.y + ") ");
		if (!this.isInteractionAllowed()) return;

		DropTarget origin = this.dragInfo.getOrigin();
		DragTarget selection = this.dragInfo.getSelection();

		if (e.getButton() == this.dragInfo.getDraggingButton() && selection != null) {
			Point p = e.getPoint();
			DropTarget dest = origin;
			
			// Compute destination of the drag
			for (DropTarget target : this.dropTargets) {
				p = SwingUtilities.convertPoint(this, e.getPoint(), target);
				if (target.contains(p)) {
					dest = target;
				}
			}
			//System.out.println("Destination: " + dest);

			if (!dest.acceptMultipleChilds() && dest.getComponentCount() != 0) {
				// Swap the two DragTarget if drag into already occupied DropTarget
				DragTarget destContent = (DragTarget)dest.getComponent(0);
				origin.add(destContent, 0);
				dest.remove(destContent);
				dest.add(selection);
			} else { // Place the piece in the DropTarget
				dest.add(selection, 0);
			}
			// Update origin and destination components and detach from GamePanel
			origin.validate();
			dest.validate();
			this.remove(selection);
			this.repaint();
			
			this.dragInfo.setDraggingButton(MouseEvent.NOBUTTON);
		}
		this.update();
	}

	/* MouseMotionEvent */

	@Override public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!this.isInteractionAllowed()) return;
	
		DragTarget selection = this.dragInfo.getSelection();
		
		if (selection != null && this.dragInfo.getDraggingButton() == MouseEvent.BUTTON1) {
			Point globalPos = e.getPoint();
			//System.out.println("Drag selection: " + selection.getLocation());
			selection.setLocation(globalPos.x - selection.getWidth()/2, globalPos.y - selection.getHeight()/2);
		}
	}
}
