package main.java;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.BasicStroke;

/** Creates pieces by generating graphics given textual characteristics */
/* TODO: support a scale factor ? */
public class PieceSynthetizer {
	
	public PieceSynthetizer()
	{
		
	}
	
	public static BufferedImage synthetize(Piece piece)
	{
		Face[] faces = piece.getFaces();
		BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = img.createGraphics();
		int[][] xBgPoints = {{0, 100, 50}, {100, 100, 50}, {0, 100, 50}, {0, 0, 50}};
		int[][] yBgPoints = {{0, 0, 50}, {0, 100, 50}, {100, 100, 50}, {0, 100, 50}};
		
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
		graphics.setStroke(new BasicStroke(3));
		graphics.drawRect(1, 1, 99, 99);
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
		System.out.println("translate color: " + color);
		return color;
	}
	
	private static Shape translatePattern(String patternName)
	{
		GeneralPath shape = new GeneralPath();
		switch (patternName.toLowerCase())
		{
			
			case "zigzag":
				
			case "circle":
				
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
				//shape = GeneralPath(new Rectangle()); 
			break;
		}
		//System.out.println("translate pattern: " + shape);
		return shape;
	}
	
}
