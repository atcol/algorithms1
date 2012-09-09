import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BoardTests {
	
	/** Ordered 1 to 9 **/
	private final int[][] a1t8 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
	
	private Board bGoal;
	
	private Board b8t1;
	
	/** Reverse ordered 9 to 1 **/
	private final int[][] a8t1 = {{0, 8, 7}, {6, 5, 4}, {3, 2, 1}};
	
    /*
     * hamming = 0 manhattan = 0
     */
    private final int[][] goalBoard = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
    /*
     * hamming = 7 manhattan = 14
     */
    private int[][] testBoardA = { { 4, 5, 6 }, { 3, 0, 1 }, { 2, 8, 7 } };
    // Neighbors of A
    private int[][] testLeftA = { { 4, 5, 6 }, { 0, 3, 1 }, { 2, 8, 7 } };
    private int[][] testRightA = { { 4, 5, 6 }, { 3, 1, 0 }, { 2, 8, 7 } };
    private int[][] testUpA = { { 4, 0, 6 }, { 3, 5, 1 }, { 2, 8, 7 } };
    private int[][] testDownA = { { 4, 5, 6 }, { 3, 8, 1 }, { 2, 0, 7 } };

    /*
     * hamming = 1 manhattan = 1
     */
    private int[][] testBoardB = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } };

    private Board A;
    private Board B;
    private Board goal;

	
	@Before
	public void setUp() {
		bGoal = new Board(a1t8);
		b8t1 = new Board(a8t1);
		
        goal = new Board(goalBoard);
        A = new Board(testBoardA);
        B = new Board(testBoardB);
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
		Assert.assertEquals(3, bGoal.dimension());
		Assert.assertEquals(3, b8t1.dimension());
		Assert.assertEquals(5, new Board(new int[5][5]).dimension());
	}

	@Test
	public void testHamming() {
		Assert.assertEquals(0, bGoal.hamming());
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
		Assert.assertEquals(0, bGoal.manhattan());
	}

	@Test
	public void testIsGoal() {
		Assert.assertTrue(bGoal.isGoal());
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
				"3  \n" +
				"   1  3 \n"+
				"4  2  5 \n"+
				"7  8  6 \n";

		final String strT2 = 
				"3  \n" +
				"   3  1 \n"+
				"4  2  5 \n"+
				"7  8  6 \n";

		final String strT3 = 
				"3  \n" +
				"   1  3 \n"+
				"2  4  5 \n"+
				"7  8  6 \n";

		final String strT4 = 
				"3  \n" +
				"   1  3 \n"+
				"4  5  2 \n"+
				"7  8  6 \n";

		final String strT5 = 
				"3  \n" +
				"   1  3 \n"+
				"4  2  5 \n"+
				"8  7  6 \n";

		final String strT6 = 
				"3  " +
				"   1  3 \n"+
				"4  2  5 \n"+
				"7  6  8 \n";
		int foundCount = 0;
		final List<String> perms = new ArrayList<String>(6);
		perms.add(strT1);
		perms.add(strT2);
		perms.add(strT3);
		perms.add(strT4);
		perms.add(strT5);
		perms.add(strT6);

		while(foundCount != 6) {
			if (perms.contains(strT1)) {
				foundCount++;
			}
			if (perms.contains(strT2)) {
				foundCount++;
			}
			if (perms.contains(strT3)) {
				foundCount++;
			}
			if (perms.contains(strT4)) {
				foundCount++;
			}
			if (perms.contains(strT5)) {
				foundCount++;
			}
			if (perms.contains(strT6)) {
				foundCount++;
			}
		}
		Assert.assertEquals(6, foundCount);
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
		Assert.assertNotSame(bGoal, null);
		Assert.assertNotSame(bGoal, new Object());
		Assert.assertNotSame(bGoal, "");
		Assert.assertNotSame(bGoal, new Integer(1));
		Assert.assertNotSame(bGoal, b8t1);
		
		Assert.assertNotSame(b8t1, null);
		Assert.assertNotSame(b8t1, new Object());
		Assert.assertNotSame(b8t1, "");
		Assert.assertNotSame(b8t1, new Integer(1));
		Assert.assertNotSame(b8t1, bGoal);
		
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
			bGoal.toString()
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
