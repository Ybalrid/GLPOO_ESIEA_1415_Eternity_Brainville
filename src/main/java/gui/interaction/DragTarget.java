package main.java.gui.interaction;

import javax.swing.JPanel;

public abstract class DragTarget extends JPanel {

	public abstract void setSelected(boolean selected);
	
	public abstract void setRotation(double rotation);
	public abstract void rotate(boolean clockwise);

}
