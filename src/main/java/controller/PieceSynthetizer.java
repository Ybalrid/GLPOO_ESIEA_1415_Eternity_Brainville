package main.java.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Ellipse2D;
import java.awt.BasicStroke;

import main.java.model.Face;
import main.java.model.Piece;

/** Creates pieces by generating graphics given textual characteristics */
/* TODO: support a scale factor ? */
public class PieceSynthetizer {

	public static BufferedImage synthetize(Piece piece)
	{
		Face[] faces = piece.getFaces();
		BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		int[][] xBgPoints = {{0, 100, 50}, {100, 100, 50}, {0, 100, 50}, {0, 0, 50}};
		int[][] yBgPoints = {{0, 0, 50}, {0, 100, 50}, {100, 100, 50}, {0, 100, 50}};

		graphics.setStroke(new BasicStroke(0));

		for (int orientation = 0; orientation < 4; orientation++)
		{
			graphics.setColor(translateColor(faces[orientation].getBgColor()));
			graphics.fillPolygon(xBgPoints[orientation], yBgPoints[orientation], 3);

			graphics.rotate(orientation * Math.PI / 2, 50, 50);

			graphics.setColor(translateColor(faces[orientation].getFgColor()));
			graphics.fill(translatePattern(faces[orientation].getPattern()));

			graphics.setColor(Color.BLACK);
			graphics.draw(translatePattern(faces[orientation].getPattern()));

			graphics.rotate(-orientation * Math.PI / 2, 50, 50);
		}

		graphics.setColor(Color.BLACK);
		graphics.setStroke(new BasicStroke(2));
		graphics.drawRect(1, 1, 98, 98);
		graphics.setStroke(new BasicStroke(0));
		graphics.drawLine(1, 1, 99, 99);
		graphics.drawLine(99, 1, 1, 99);

		graphics.drawImage(img, 0, 0, 100, 100, null);
		return img;
	}

	private static Color translateColor(String colorName)
	{
		Color color;
		switch (colorName.toLowerCase())
		{
			case "blue": color = new Color(50, 120, 255); break;
			case "red": color = new Color(255, 50, 50); break;
			case "green": color = new Color(30, 255, 80); break;
			case "purple": color = new Color(220, 50, 220); break;
			case "yellow": color = new Color(220, 255, 50); break;
			default:
				System.out.println("Translated unknown color to black");
				color = Color.BLACK;
			break;
		}
		System.out.println("Translate color: " + color);
		return color;
	}

	private static Shape translatePattern(String patternName)
	{
		GeneralPath shape = new GeneralPath();
		switch (patternName.toLowerCase())
		{
			case "triangle":
				shape.moveTo(30, 0);
				shape.lineTo(70, 0);
				shape.lineTo(50, 20);
				shape.closePath();
			break;
			case "circle":
				shape = new GeneralPath(new Ellipse2D.Float(35, 5, 30, 30));
			break;
			case "zigzag":
				shape.moveTo(50, 0);
				shape.lineTo(35, 15);
				shape.lineTo(45, 25);
				shape.lineTo(35, 35);

				shape.lineTo(50, 50);

				shape.lineTo(65, 35);
				shape.lineTo(55, 25);
				shape.lineTo(65, 15);
				shape.lineTo(50, 0);
				shape.closePath();
			break;
			case "line":
				shape.moveTo(40, 0);
				shape.lineTo(60, 0);
				shape.lineTo(60, 40);
				shape.lineTo(40, 40);
				shape.closePath();
			break;
			default:
				System.out.println("Translated unknown pattern to line");
				shape.moveTo(40, 0);
				shape.lineTo(60, 0);
				shape.lineTo(60, 40);
				shape.lineTo(40, 40);
				shape.closePath();
				//shape = new GeneralPath(new Rectangle());
			break;
		}
		//System.out.println("Translate pattern: " + shape);
		return shape;
	}

}
