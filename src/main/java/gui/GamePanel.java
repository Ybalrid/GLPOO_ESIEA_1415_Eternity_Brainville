package main.java.gui;

import java.util.ArrayList;

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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.java.model.Piece;
import main.java.controller.Game;
import main.java.gui.interaction.DragInfo;
import main.java.gui.interaction.DragTarget;
import main.java.gui.interaction.DropTarget;
import main.java.gui.interaction.KeyShortcuts;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

	private Game game;
	private ArrayList<DropTarget> dropTargets;

	private PuzzlePanel puzzle;
	private JPanel containerEast;
	private StockPanel stock;

	private DragInfo dragInfo;

	public GamePanel()
	{
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

		this.createGrid();

		this.dragInfo = new DragInfo();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		new KeyShortcuts(this);
	}

	public void createGrid() {
		this.dropTargets = new ArrayList<DropTarget>(17);

		for (int i = 0; i < 16; i++) {
			CellPanel p = new CellPanel();
			p.setBackground(new Color(i * 16 + 8, i * 16 + 8, i * 16 + 8));
			this.puzzle.add(p, BorderLayout.WEST);
			this.dropTargets.add(p);
		}
		this.dropTargets.add(stock); // Must add it after cell panels!
	}

	public void createPiecePanels(Piece[] pieces) {
		int len = pieces.length;
		for (int i = 0; i < 16; i++) {
			PiecePanel p = new PiecePanel();
			p.setBackground(Color.BLACK);
			this.stock.add(p, BorderLayout.WEST);
		}
		this.stock.validate();
		for (int i = 0; i < len; i++)
		{
			((PiecePanel)this.stock.getComponent(i)).setPiece(pieces[i]);
		}
	}

	private ArrayList<PiecePanel> getOrderedPiecePanels() {
		int len = 16;
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
	public Piece[] getOrderedPieces() { // TODO
		ArrayList<PiecePanel> piecePanels = this.getOrderedPiecePanels();
		int len = piecePanels.size();
		if (len != 16) return null;

		Piece[] pieces = new Piece[len];
		for (int i = 0; i < len; i++) {
			pieces[i] = piecePanels.get(i).getPiece();
			System.out.println(pieces[i]);
		}
		return pieces;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public DragInfo getDragInfo() {
		return this.dragInfo;
	}

	public void rotateSelection(boolean clockwise) {
		((PiecePanel)this.dragInfo.getSelection()).rotate(clockwise);
	}

	/*
	* Events response implementations
	*/

	@Override
	public void mouseClicked(MouseEvent e) {
//		System.out.println("[Clicked] Point: " + e.getX() + ", " + e.getY() + " ");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println("[Entered] Point: " + e.getX() + ", " + e.getY() + " ");
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.print("[Exited] ");// Point: " + e.getX() + ", " + e.getY() + " ");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			Point globalPos = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), this);
			System.out.print("[Pressed] (" + globalPos.x + "," + globalPos.y + ") ");
	
			this.dragInfo.reset();
	
			Component pointed = this.findComponentAt(globalPos);
	
			if (pointed instanceof DragTarget && this.dragInfo.getSelection() == null) {
				DragTarget selection = (DragTarget)pointed;
				DropTarget target = (DropTarget)selection.getParent();
				this.dragInfo.setSelection(selection);
				this.dragInfo.setOrigin(target);
				selection.setLocation(globalPos.x - selection.getWidth()/2, globalPos.y - selection.getHeight()/2);
				target.remove(selection);
				target.repaint();
				this.add(selection, 0);
				this.repaint();
				selection.requestFocusInWindow();
				System.out.println("Selection: " + selection + " ");
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point globalPos = e.getPoint();
		System.out.println("[Released] (" + globalPos.x + "," + globalPos.y + ") ");

		DropTarget origin = this.dragInfo.getOrigin();
		DragTarget selection = this.dragInfo.getSelection();

		if (selection != null) {
			Point p = e.getPoint();
			DropTarget dest = origin;

			for (DropTarget target : this.dropTargets) {
				p = SwingUtilities.convertPoint(this, e.getPoint(), target);
				if (target.contains(p)) {
					dest = target;
				}
			}
			//System.out.println("Destination: " + dest);

			// Swap the two pieces if drag into already occupied DropTarget
			if (!dest.acceptMultipleChilds() && dest.getComponentCount() != 0) {
				DragTarget destContent = (DragTarget)dest.getComponent(0);
				origin.add(destContent, 0);
				dest.remove(destContent);
				dest.add(selection);
			} else { // Place the piece in the DropTarget
				dest.add(selection, 0);
			}
			origin.validate();
			dest.validate();
			this.remove(selection);
			this.repaint();
			//System.out.println("Unselection " + selection.getLocation() + " " + dest.getComponent(0));
			this.game.checkSolution();
		}
	}

	/* MouseMotionEvent */

	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.print("[Moved] " + e.getX() + " " + e.getY() + " ");
	}

	@Override
	public void mouseDragged(MouseEvent e) {	
		System.out.print("[Dragged] ");// + e.getPoint() + " ");
	
		DragTarget selection = this.dragInfo.getSelection();
		if (selection != null) {
			Point globalPos = e.getPoint();
			System.out.println("Drag selection: " + selection.getLocation() + " " + globalPos);
			selection.setLocation(globalPos.x - selection.getWidth()/2, globalPos.y - selection.getHeight()/2);

		}
	}
}
