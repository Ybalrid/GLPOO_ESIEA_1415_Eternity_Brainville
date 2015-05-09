package main.java.gui.interaction;

import javax.swing.JComponent;

import main.java.gui.interaction.DragTarget;
import main.java.gui.interaction.DropTarget;

public class DragInfo {

	/** A simple info handler for dragging parameters. */

	// Component that contained the selection when dragging started
	private DropTarget origin;
	// Component that is dragged
	private DragTarget selected;

	public DragInfo () {
		this.origin = null;
		this.selected = null;
	}

	public DropTarget getOrigin() {
		return this.origin;
	}

	public void setOrigin(DropTarget _origin) {
		this.origin = _origin;
	}

	public DragTarget getSelected() {
		return this.selected;
	}

	public void setSelected(DragTarget selectedPanel) {
		this.selected = selectedPanel;
	}

	public void reset() {
		this.setSelected(null);
		this.setOrigin(null);
	}
}
