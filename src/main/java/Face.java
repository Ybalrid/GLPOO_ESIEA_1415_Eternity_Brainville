package main.java;

public class Face {
	
	private int id;
	private String bgColor;
	private String fgColors;
	private String pattern;	
	
	public Face (int i, String bC, String fC, String p) 
	{
		this.id = i;
		this.bgColor = bC;
		this.fgColors = fC;
		this.pattern = p;
	}

	public int getId() {
		return id;
	}

	public String getBgColor() {
		return bgColor;
	}

	public String getFgColors() {
		return fgColors;
	}

	public String getPattern() {
		return pattern;
	}
	
	
}
