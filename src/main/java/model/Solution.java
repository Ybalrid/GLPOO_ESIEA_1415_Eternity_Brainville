package main.java.model;

import java.awt.Dimension;

public class Solution {

	private Dimension dim;
	private Piece[][] grid;

	public Solution(Piece[][] grid) {
		this.grid = grid;
		this.dim = new Dimension(grid.length, grid[0].length);
	}

	public Solution(Piece[] pieces, int width) {
		int size = pieces.length;
		this.dim = new Dimension(width, size / width);
		Piece[][] grid = new Piece[dim.height][dim.width];
		for (int i = 0; i < size; i++) {
			//System.out.println("i / this.size, i % this.size:\n " + (i / dim.width) + " " + (i % dim.width));
			grid[i / dim.width][i % dim.width] = pieces[i];
			//System.out.println("grid[i / this.size][i % this.size]:\n " + grid[i / dim.width][i % dim.width]);
		}
		this.grid = grid;
	}

	// Get a piece using two indices
	public Piece get(int i, int j) {
		return grid[j][i];
	}

	// Get a piece using one index
	public Piece get(int i) {
		return grid[i / dim.width][i % dim.width];
	}
	
	// Get width and height as a single object
	public Dimension getDim() {
		return this.dim;
	}
	
	// Get total size (width * height)
	public int getSize() {
		return this.dim.width * this.dim.height;
	}
	
}
