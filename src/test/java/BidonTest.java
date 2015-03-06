package test.java;

import static org.junit.Assert.*;
import main.java.Bidon;

import org.junit.Before;
import org.junit.Test;

public class BidonTest {
	private Bidon bidon = null;

	@Before
	public void setUp() throws Exception {
		bidon = new Bidon();
	}

	@Test
	public void testSum() {
		final int actual = bidon.sum(1, 1);
		assertEquals(2, actual);
	}

}
