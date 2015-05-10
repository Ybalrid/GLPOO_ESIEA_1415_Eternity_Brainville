package main.java.model;

public class Solution {

	private Piece[][] grid;

	public Solution(Piece[][] grid) {
		this.grid = grid;
	}

	public Piece get(int i, int j) {
		return grid[j][i];
	}

	public Piece get(int i) {
		int len = grid.length;
		return grid[i / len][i % len];
	}
}
