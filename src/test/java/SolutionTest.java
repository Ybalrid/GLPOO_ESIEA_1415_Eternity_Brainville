package test.java;

import java.awt.Dimension;

import main.java.model.Face;
import main.java.model.Piece;
import main.java.model.Solution;

import static org.junit.Assert.*;
import org.junit.Test;

public class SolutionTest {

	@Test
	public void test() {
		Face f0 = new Face(0, "", "", "");
		Face f1 = new Face(1, "blue", "white", "zigzag");
		Face f2 = new Face(2, "purple", "red", "circle");
		Face f3 = new Face(3, "green", "blue", "triangle");
		Face f4 = new Face(4, "red", "yellow", "line");
		Piece p0 = new Piece(0, new Face[]{f0, f3, f3, f0}, 0, 0, 0);
		Piece p1 = new Piece(1, new Face[]{f0, f2, f4, f3}, 1, 0, 0);
		Piece p2 = new Piece(2, new Face[]{f0, f4, f4, f2}, 2, 0, 0);
		Piece p3 = new Piece(3, new Face[]{f0, f0, f3, f4}, 3, 0, 0);
		Piece p4 = new Piece(4, new Face[]{f3, f1, f3, f0}, 0, 1, 0);
		Piece p5 = new Piece(5, new Face[]{f4, f3, f1, f1}, 1, 1, 0);
		Piece p6 = new Piece(6, new Face[]{f4, f1, f2, f3}, 2, 1, 0);
		Piece p7 = new Piece(7, new Face[]{f3, f0, f2, f1}, 3, 1, 0);
		Piece p8 = new Piece(8, new Face[]{f3, f4, f4, f0}, 0, 2, 0);
		Piece p9 = new Piece(9, new Face[]{f1, f3, f1, f4}, 1, 2, 0);
		Piece p10 = new Piece(10, new Face[]{f2, f1, f4, f3}, 2, 2, 0);
		Piece p11 = new Piece(11, new Face[]{f2, f0, f2, f1}, 3, 2, 0);
		Piece p12 = new Piece(12, new Face[]{f4, f3, f0, f0}, 0, 3, 0);
		Piece p13 = new Piece(13, new Face[]{f1, f2, f0, f3}, 1, 3, 0);
		Piece p14 = new Piece(14, new Face[]{f4, f4, f0, f2}, 2, 3, 0);
		Piece p15 = new Piece(15, new Face[]{f2, f0, f0, f4}, 3, 3, 0);
		Piece[] pieces = {p15, p14, p13, p12, p11, p10, p9, p8, p7, p6, p5, p4, p3, p2, p1, p0};
		Solution solution = new Solution(pieces, 4);
		
		assertTrue(solution.getDim().equals(new Dimension(4,4)));
		assertTrue(solution.getSize() == 16);
		assertTrue(solution.get(4).equals(p11));
		assertTrue(solution.get(0, 1).equals(p11));
	}
}

