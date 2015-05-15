package main.java.model;

import java.awt.Dimension;

public class Solution {

	private Dimension dim;
	private Piece[][] grid;

	public Solution(Piece[][] grid) {
		this.grid = grid;
		this.dim = new Dimension(4, 4);
	}

	public Solution(Piece[] pieces) {
		this.dim = new Dimension(4, 4);
		int size = 16;
		Piece[][] grid = new Piece[dim.height][dim.width];
		for (int i = 0; i < size; i++) {
			//System.out.println("i / this.size, i % this.size:\n " + (i / dim.width) + " " + (i % dim.width));
			grid[i / dim.width][i % dim.width] = pieces[i];
			System.out.println("grid[i / this.size][i % this.size]:\n " + grid[i / dim.width][i % dim.width]);
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
	
	public Dimension getDim() {
		return this.dim;
	}
	
	public int getSize() {
		return this.dim.width * this.dim.height;
	}
}
