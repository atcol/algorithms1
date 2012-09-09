import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BoardTests {
	
	/** Ordered 1 to 9 **/
	private int[][] a1t8;
	
	private Board b1t8;
	
	private Board b8t1;
	
	/** Reverse ordered 9 to 1 **/
	private int[][] a8t1;
	
	@Before
	public void setUp() {
		a1t8 = new int[3][3];
		a8t1 = new int[3][3];
		
		int c = 1;
		for (int i = 0; i < a1t8.length; i++) {
			for (int j = 0; j < a1t8.length; j++) {
				a1t8[i][j] = c;
				c++;
			}
		}
		a1t8[2][2] = 0;
		
		c = 9;
		for (int i = 0; i < a8t1.length; i++) {
			for (int j = 0; j < a8t1.length; j++) {
				a8t1[i][j] = c;
				c--;
			}
		}
		a8t1[0][0] = 0;
		
		b1t8 = new Board(a1t8);
		b8t1 = new Board(a8t1);
	}

	@Test
	public void testBoard() {
		final Board b = new Board(a1t8);
		final Board b2 = new Board(a1t8);
		Assert.assertEquals(b, b);
		Assert.assertEquals(b, b2);
	}

	@Test
	public void testDimension() {
		Assert.assertEquals(3, new Board(a1t8).dimension());
		Assert.assertEquals(3, new Board(a8t1).dimension());
		Assert.assertEquals(3, b1t8.dimension());
		Assert.assertEquals(3, b8t1.dimension());
		Assert.assertEquals(5, new Board(new int[5][5]).dimension());
	}

	@Test
	public void testHamming() {
		Assert.assertEquals(0, b1t8.hamming());
		Assert.assertEquals(8, b8t1.hamming());
	}

	@Test
	public void testManhattan() {
		// sum of manhattan dist for each item out of place
		final int[][] a = new int[3][3];
		a[0][0] = 8;// 1 = 1
		a[0][1] = 1;// 2 = 2
		a[0][2] = 3;// 3 = 0
		a[1][0] = 4;// 4 = 0
		a[1][1] = 0;// 5 = 2
		a[1][2] = 2;// 6 = 2
		a[2][0] = 7;// 7 = 0
		a[2][1] = 6;// 8 = 3
		a[2][2] = 5;// 0 = N/A
		final Board b = new Board(a);
		Assert.assertEquals(10, b.manhattan());
		Assert.assertEquals(0, new Board(new int[0][0]).manhattan());
		Assert.assertEquals(0, b1t8.manhattan());
	}

	@Test
	public void testIsGoal() {
		Assert.assertTrue(b1t8.isGoal());
	}
	
	@Test
	public void testIsGoalFalse() {
		Assert.assertFalse(b8t1.isGoal());
		Assert.assertFalse(new Board(new int[5][5]).isGoal());
	}

	@Test
	public void testTwin() {
		final int[][] a = new int[3][3];
		a[0][0] = 0;
		a[0][1] = 1;
		a[0][2] = 3;
		a[1][0] = 4;
		a[1][1] = 2;
		a[1][2] = 5;
		a[2][0] = 7;
		a[2][1] = 8;
		a[2][2] = 6;
		final Board b = new Board(a);
		final Board t1 = b.twin();
		final Board t2 = b.twin();
		final Board t3 = b.twin();
		final Board t4 = b.twin();
		final Board t5 = b.twin();
		Assert.assertNotSame(b, t1);
		Assert.assertNotSame(b, t2);
		Assert.assertNotSame(b, t3);
		Assert.assertNotSame(b, t4);
		Assert.assertNotSame(b, t5);
		final String strT1 = 
				"   1  3"+
				"4  2  5"+
				"7  8  6";

		final String strT2 = 
				"   3  1"+
				"4  2  5"+
				"7  8  6";

		final String strT3 = 
				"   1  3"+
				"2  4  5"+
				"7  8  6";

		final String strT4 = 
				"   1  3"+
				"4  5  2"+
				"7  8  6";

		final String strT5 = 
				"   1  3"+
				"4  2  5"+
				"8  7  6";

		final String strT6 = 
				"   1  3"+
				"4  2  5"+
				"7  6  8";
		boolean found = false;
		
		while(!found) {
			found = t1.toString().equals(strT1);
			if (found) continue;
			found = t1.toString().equals(strT2);
			if (found) continue;
			found = t1.toString().equals(strT3);
			if (found) continue;
			found = t1.toString().equals(strT4);
			if (found) continue;
			found = t1.toString().equals(strT5);
			if (found) continue;
			found = t1.toString().equals(strT6);
			if (found) continue;
			
			found = t2.toString().equals(strT1);
			if (found) continue;
			found = t2.toString().equals(strT2);
			if (found) continue;
			found = t2.toString().equals(strT3);
			if (found) continue;
			found = t2.toString().equals(strT4);
			if (found) continue;
			found = t2.toString().equals(strT5);
			if (found) continue;
			found = t2.toString().equals(strT6);
			if (found) continue;
			
			found = t3.toString().equals(strT1);
			if (found) continue;
			found = t3.toString().equals(strT2);
			if (found) continue;
			found = t3.toString().equals(strT3);
			if (found) continue;
			found = t3.toString().equals(strT4);
			if (found) continue;
			found = t3.toString().equals(strT5);
			if (found) continue;
			found = t3.toString().equals(strT6);
			if (found) continue;
			
			found = t4.toString().equals(strT1);
			if (found) continue;
			found = t4.toString().equals(strT2);
			if (found) continue;
			found = t4.toString().equals(strT3);
			if (found) continue;
			found = t4.toString().equals(strT4);
			if (found) continue;
			found = t4.toString().equals(strT5);
			if (found) continue;
			found = t4.toString().equals(strT6);
			if (found) continue;
			
			found = t5.toString().equals(strT1);
			if (found) continue;
			found = t5.toString().equals(strT2);
			if (found) continue;
			found = t5.toString().equals(strT3);
			if (found) continue;
			found = t5.toString().equals(strT4);
			if (found) continue;
			found = t5.toString().equals(strT5);
			if (found) continue;
			found = t5.toString().equals(strT6);
			if (found) continue;
		}
		Assert.assertTrue(found);
	}

	@Test
	public void testEqualsObject() {
		final Board b = new Board(a1t8);
		final Board b2 = new Board(a1t8);
		Assert.assertEquals(b, b);
		Assert.assertEquals(b, b2);
	}
	
	@Test
	public void testNotEquals() {
		Assert.assertNotSame(b1t8, null);
		Assert.assertNotSame(b1t8, new Object());
		Assert.assertNotSame(b1t8, "");
		Assert.assertNotSame(b1t8, new Integer(1));
		Assert.assertNotSame(b1t8, b8t1);
		
		Assert.assertNotSame(b8t1, null);
		Assert.assertNotSame(b8t1, new Object());
		Assert.assertNotSame(b8t1, "");
		Assert.assertNotSame(b8t1, new Integer(1));
		Assert.assertNotSame(b8t1, b1t8);
		
		Assert.assertNotSame(new Board(new int[5][5]), null);
		Assert.assertNotSame(new Board(new int[5][5]), new Object());
		Assert.assertNotSame(new Board(new int[5][5]), "");
		Assert.assertNotSame(new Board(new int[5][5]), new Integer(1));
		Assert.assertNotSame(new Board(new int[5][5]), b8t1);
	}

	@Test
	public void testNeighbors() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		Assert.assertEquals(
				"3\n" +
				" 1  2  3 \n" +
				" 4  5  6 \n" +
				" 7  8  0 \n",
			b1t8.toString()
		);
		Assert.assertEquals(
				"3\n" +
				" 0  8  7 \n" +
				" 6  5  4 \n" +
				" 3  2  1 \n",
			b8t1.toString()
		);
	}
	
	@Test
	public void testInfeasible() {
		fail("Not yet implemented");
	}
}
