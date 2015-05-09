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

import main.java.model.Face;
import main.java.model.Piece;
import main.java.model.PieceSynthetizer;
import main.java.gui.interaction.DragInfo;
import main.java.gui.interaction.DragTarget;
import main.java.gui.interaction.DropTarget;
import main.java.gui.interaction.KeyShortcuts;

public class InteractiveContainer extends JPanel implements MouseListener, MouseMotionListener {

	private ImagePanel[] imgPanels;
	private ArrayList<DropTarget> dropTargets;

	private PuzzlePanel puzzle;
	private JPanel containerEast;
	private StockPanel stock;

	private DragInfo dragInfo;

	public InteractiveContainer()
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

		this.dropTargets = new ArrayList<DropTarget>(17);
		this.dropTargets.add(stock);

		for (int i = 0; i < 16; i++) {
			CellPanel p = new CellPanel();
			p.setBackground(new Color(i * 16 + 15, i * 16 + 15, i * 16 + 15));
			this.puzzle.add(p, BorderLayout.WEST);
			this.dropTargets.add(p);
		}

		this.imgPanels = new ImagePanel[16];
		for (int i = 0; i < 7; i++) {
			ImagePanel p = new ImagePanel(this);
			p.setBackground(Color.BLACK);
			this.stock.add(p, BorderLayout.WEST);
			this.imgPanels[i] = p;
		}

		this.createPieces();

		this.dragInfo = new DragInfo();
		this.addMouseListener(this);
		new KeyShortcuts(this);
	}

	private void createPieces() {

		Face[] faces1 = {new Face(0, "blue", "purple", "zigzag"), new Face(0, "purple", "red", "circle"), new Face(0, "green", "blue", "triangle"), new Face(0, "red", "yellow", "")};
		Face[] faces2 = {new Face(0, "", "", ""), new Face(0, "yellow", "red", ""), new Face(0, "yellow", "green", ""), new Face(0, "purple", "red", "")};
		Face[] faces3 = {new Face(0, "purple", "red", "circle"), new Face(0, "blue", "purple", "zigzag"), new Face(0, "", "", ""), new Face(0, "", "", "")};
		Piece[] pieces = {new Piece(0, faces1, 0, 0, 0), new Piece(0, faces2, 0, 0, 0), new Piece(0, faces1, 0, 0, 0), new Piece(0, faces3, 0, 0, 0), new Piece(0, faces2, 0, 0, 0)};

		for (int i = 0; i < pieces.length; i++)
		{
			this.imgPanels[i].setImg(PieceSynthetizer.synthetize(pieces[i]));
			this.imgPanels[i].repaint();
		}
	}

	public DragInfo getDragInfo() {
		return this.dragInfo;
	}

	public void rotateSelected(boolean clockwise) {
		((ImagePanel)this.dragInfo.getSelected()).rotate(clockwise);
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
		System.out.println("[Pressed] Point: " + e.getPoint() + " ");

		this.dragInfo.reset();

		if (e.getComponent() instanceof DragTarget && this.dragInfo.getSelected() == null) {
			DragTarget selected = (DragTarget)e.getComponent();
			DropTarget target = (DropTarget)selected.getParent();
			this.dragInfo.setSelected(selected);
			this.dragInfo.setOrigin(target);
			Point globalPos = SwingUtilities.convertPoint(selected, e.getPoint(), this);
			selected.setLocation(globalPos.x - selected.getWidth()/2, globalPos.y - selected.getHeight()/2);
			target.remove(selected);
			target.repaint();
			this.add(selected, 0);
			this.repaint();
			System.out.println("Selected: " + selected + " ");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("[Released] Point: " + e.getPoint() + " ");

		DropTarget origin = this.dragInfo.getOrigin();
		DragTarget selected = this.dragInfo.getSelected();

		if (selected != null) {
			Point p = e.getPoint();
			DropTarget dest = origin;

			for (DropTarget target : this.dropTargets) {
				p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), target);
				if (target.contains(p)) {
					dest = target;
				}
			}
			System.out.println("Destination: " + dest);

			// Swap the two pieces if drag into already occupied DropTarget
			if (!dest.acceptMultipleChilds() && dest.getComponentCount() != 0) {
				DragTarget destContent = (DragTarget)dest.getComponent(0);
				origin.add(destContent, 0);
				origin.validate();
				dest.remove(destContent);
				dest.add(selected);
				dest.validate();
			} else { // Place the piece in the DropTarget
				dest.add(selected, 0);
				dest.validate();
			}
			this.remove(selected);
			this.repaint();
			//this.dragInfo.reset();
			System.out.println("Unselected " + selected.getLocation() + " " + dest.getComponent(0));
		}
	}

	/* MouseMotionEvent */

	@Override
	public void mouseMoved(MouseEvent e) {
//		System.out.print("[Moved] " + e.getX() + " " + e.getY() + " ");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.print("[Dragged] " + e.getPoint() + " ");

		DragTarget selected = this.dragInfo.getSelected();
		if (selected == e.getComponent()) {
			System.out.println("Drag selected: " + selected.getLocation());
			Point globalPos = SwingUtilities.convertPoint(selected, e.getPoint(), this);
			selected.setLocation(globalPos.x - selected.getWidth()/2, globalPos.y - selected.getHeight()/2);
		}

	}

}
