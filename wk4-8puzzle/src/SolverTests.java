import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class SolverTests {
	
	/** Ordered 1 to 9 **/
	private int[][] a1t8;
	
	private Board b1t8;
	
	private Board b8t1;
	
	/** Reverse ordered 9 to 1 **/
	private int[][] a8t1;

	private Board puzzle04;

	private Board bUnsolvable;
	
	@Before
	public void setUp() {

		a1t8 = new int[3][3];
		a8t1 = new int[3][3];
		b1t8 = new Board(a1t8);
		b8t1 = new Board(a8t1);
		
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
//		0  1  3
//		 4  2  5
//		 7  8  6
		puzzle04 = new Board(a);
		
		final int[][] b = new int[3][3];
//		 1  2  3
//		 4  5  6
//		 8  7
		b[0][0] = 1;
		b[0][1] = 2;
		b[0][2] = 3;
		b[1][0] = 4;
		b[1][1] = 5;
		b[1][2] = 6;
		b[2][0] = 8;
		b[2][1] = 7;
		b[2][2] = 0;
		bUnsolvable = new Board(b);
	}

	@Test
	public void testSolver() {
		final Solver s = new Solver(b1t8);
		Assert.assertTrue(s.isSolvable());
		Assert.assertEquals(0, s.moves());
		Assert.assertFalse(s.solution().iterator().hasNext());
	}

	@Test
	public void testSolverUnsolved() {
		final Solver s = new Solver(b8t1);
		Assert.assertFalse(s.isSolvable());
//		TODO work this out:Assert.assertEquals(0, s.moves());
		Assert.assertTrue(s.solution().iterator().hasNext());
	}

	@Test
	public void testIsSolvable() {
		final Solver s = new Solver(b1t8);
		Assert.assertTrue(s.isSolvable());
		final Solver s2 = new Solver(puzzle04);
		Assert.assertTrue(s2.isSolvable());
	}
	
	@Test
	public void testIsSolvableFalse() {
		final Solver s = new Solver(new Board(new int[0][0]));
		Assert.assertFalse(s.isSolvable());
		final Solver s2 = new Solver(bUnsolvable);
		Assert.assertFalse(s2.isSolvable());
	}

	@Test
	public void testMoves() {
		Assert.assertEquals(4, new Solver(puzzle04).moves());
	}

	@Test
	public void testMovesNegative1IfNoSolution() {
		final Solver s = new Solver(bUnsolvable);
		Assert.assertEquals(-1, s.moves());
	}
	
	@Test
	public void testSolution() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testNullIfNoSolution() {
		// new insolvable Solver
		// test .solution();
		final Solver s = new Solver(bUnsolvable);
		Assert.assertNull(s.solution());
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}
}
