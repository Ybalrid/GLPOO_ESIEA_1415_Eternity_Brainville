package main.java.gui.interaction;

import javax.swing.JComponent;

import main.java.gui.interaction.DragTarget;
import main.java.gui.interaction.DropTarget;

public class DragInfo {

	/** A simple info handler for dragging parameters. */

	// Component that contained the selection when dragging started
	private DropTarget origin;
	// Component that is dragged
	private DragTarget selection;
	// Button that was initiated dragging, for use in MouseMotion callbacks
	private int draggingButton;

	public DragInfo () {
		this.origin = null;
		this.selection = null;
		this.draggingButton = 0;
	}

	public DropTarget getOrigin() {
		return this.origin;
	}

	public void setOrigin(DropTarget _origin) {
		this.origin = _origin;
	}

	public DragTarget getSelection() {
		return this.selection;
	}

	public void setSelection(DragTarget selectionPanel) {
		this.selection = selectionPanel;
	}
	
	public int getDraggingButton() {
		return this.draggingButton;
	}

	public void setDraggingButton(int draggingButton) {
		this.draggingButton = draggingButton;
	}

	public void reset() {
		this.setSelection(null);
		this.setOrigin(null);
	}
}
