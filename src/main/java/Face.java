package main.java;

public class Face {
	
	private int id;
	private String bgColor;
	private String fgColor;
	private String pattern;	
	
	public Face (int i, String bC, String fC, String p) 
	{
		this.id = i;
		this.bgColor = bC;
		this.fgColor = fC;
		this.pattern = p;
	}

	public int getId() {
		return id;
	}

	public String getBgColor() {
		return bgColor;
	}

	public String getFgColor() {
		return fgColor;
	}

	public String getPattern() {
		return pattern;
	}
	
	
}
