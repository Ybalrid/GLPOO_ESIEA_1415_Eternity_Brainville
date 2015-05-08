package main.java;

import javax.swing.JComponent;

public class DragInfo {

	/** A simple info handler for gragging parameters. */

	/** Last position of the mouse on screen. Used to perform dragging. */
	private static Vector2D lastMousePos;
	/** Position at which dragging began. */
	private static Vector2D originPos;
	private static DropTarget origin;
	private static DragTarget selected;

	public static void init () {
		origin = null;
		selected = null;
		lastMousePos = new Vector2D();
		originPos = new Vector2D();
	}

	public static Vector2D getLastMousePos() {
		return lastMousePos;
	}

	public static void setLastMousePos(Vector2D _lastMousePos) {
		lastMousePos = _lastMousePos;
	}

	public static Vector2D getOriginPos() {
		return originPos;
	}

	public static void setOriginPos(Vector2D _originPos) {
		originPos = _originPos;
	}

	public static DropTarget getOrigin() {
		return origin;
	}

	public static void setOrigin(DropTarget _origin) {
		origin = _origin;
	}

	public static DragTarget getSelected() {
		return selected;
	}

	public static void setSelected(DragTarget selectedPanel) {
		selected = selectedPanel;
	}

	public static void reset() {
		setSelected(null);
		setOrigin(null);
	}
}
