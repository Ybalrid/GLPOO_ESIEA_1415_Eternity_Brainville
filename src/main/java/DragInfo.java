package main.java;

public class DragInfo {

	/** A simple info handler for gragging parameters. */
	
	/** Last position of the mouse on screen. Used to perform dragging. */
	private static Vector2D lastMousePos;
	/** Position at which dragging began. */
	private static Vector2D dragOrigin;
	
	public static void init () {
		lastMousePos = new Vector2D();
		dragOrigin = new Vector2D();
	}

	public static Vector2D getLastMousePos() {
		return lastMousePos;
	}

	public static void setLastMousePos(Vector2D _lastMousePos) {
		lastMousePos = _lastMousePos;
	}

	public static Vector2D getDragOrigin() {
		return dragOrigin;
	}

	public static void setDragOrigin(Vector2D _dragOrigin) {
		dragOrigin = _dragOrigin;
	}

}
