package main.java.gui.interaction;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;

import main.java.gui.MainWindow;
import main.java.gui.GamePanel;

public class KeyShortcuts {

	private MainWindow window;
	private GamePanel gamePanel;

	public KeyShortcuts(MainWindow window) {
		this.window = window;
		this.gamePanel = window.getGamePanel();

		InputMap inputMap = gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = gamePanel.getActionMap();

		// R: rotate piece clockwise
		inputMap.put(KeyStroke.getKeyStroke("R"), "rotateClockwise");
		// Shift+R: rotate piece counter-clockwise
		inputMap.put(KeyStroke.getKeyStroke("shift R"), "rotateCounterClockwise");

		actionMap.put("rotateClockwise", new RotateClockwiseAction());
		actionMap.put("rotateCounterClockwise", new RotateCounterClockwiseAction());
	}

	private class RotateClockwiseAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			System.out.println("[RotateClockwise]");
			gamePanel.rotateDragTarget(gamePanel.dragInfo.getSelection(), true);
		}
	}

	private class RotateCounterClockwiseAction extends AbstractAction {
		@Override public void actionPerformed(ActionEvent e) {
			System.out.println("[RotateCounterClockwise]");
			gamePanel.rotateDragTarget(gamePanel.dragInfo.getSelection(), false);
		}
	}
}
