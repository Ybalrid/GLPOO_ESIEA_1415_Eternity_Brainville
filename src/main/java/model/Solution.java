package main.java.model;

public class Solution {

	private int size;
	private Piece[][] grid;

	public Solution(Piece[][] grid) {
		this.grid = grid;
		this.size = 16;
	}

	public Solution(Piece[] pieces) {
		this.size = 16;
		int width = 4;
		Piece[][] grid = new Piece[4][4];
		for (int i = 0; i < this.size; i++) {
			System.out.println("i / this.size, i % this.size: " + (i / width) + " " + (i % width));
			grid[i / width][i % width] = pieces[i];
			System.out.println("grid[i / this.size][i % this.size]: " + grid[i / width][i % width]);
		}
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
