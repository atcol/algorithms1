
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class SolverTests {
    
    /** Ordered 1 to 9 **/
    private final int[][] a1t8 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    
    private Board bGoal;
    
    private Board b8t1;
    
    /** Reverse ordered 9 to 1 **/
    private final int[][] a8t1 = {{0, 8, 7}, {6, 5, 4}, {3, 2, 1}};
    
    /*
     * hamming = 0 manhattan = 0
     */
    private final int[][] goalBoard = {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }};
    /*
     * hamming = 7 manhattan = 14
     */
    private int[][] testBoardA = {{ 4, 5, 6 }, { 3, 0, 1 }, { 2, 8, 7 }};
    // Neighbors of A
//    private int[][] testLeftA = {{ 4, 5, 6 }, { 0, 3, 1 }, { 2, 8, 7 }};
//    private int[][] testRightA = {{ 4, 5, 6 }, { 3, 1, 0 }, { 2, 8, 7 }};
//    private int[][] testUpA = {{ 4, 0, 6 }, { 3, 5, 1 }, { 2, 8, 7 } };
//    private int[][] testDownA = {{ 4, 5, 6 }, { 3, 8, 1 }, { 2, 0, 7 }};

    /*
     * hamming = 1 manhattan = 1
     */
    private int[][] testBoardB = {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 }};

//    private Board A;
//    private Board B;
//    private Board goal;
    private Board puzzle04;
    private Board bUnsolvable;
    
    @Before
    public void setUp() {

//        a1t8 = new int[3][3];
//        a8t1 = new int[3][3];
//        b1t8 = new Board(a1t8);
        b8t1 = new Board(a8t1);
        bGoal = new Board(a1t8);
        b8t1 = new Board(a8t1);
        
//        goal = new Board(goalBoard);
//        A = new Board(testBoardA);
//        B = new Board(testBoardB);
                
        final int[][] a = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
//        a[0][0] = 0;
//        a[0][1] = 1;
//        a[0][2] = 3;
//        a[1][0] = 4;
//        a[1][1] = 2;
//        a[1][2] = 5;
//        a[2][0] = 7;
//        a[2][1] = 8;
//        a[2][2] = 6;
//         0  1  3
//         4  2  5
//         7  8  6
        puzzle04 = new Board(a);
        
        final int[][] b = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
//         1  2  3
//         4  5  6
//         8  7
//        b[0][0] = 1;
//        b[0][1] = 2;
//        b[0][2] = 3;
//        b[1][0] = 4;
//        b[1][1] = 5;
//        b[1][2] = 6;
//        b[2][0] = 8;
//        b[2][1] = 7;
//        b[2][2] = 0;
        bUnsolvable = new Board(b);
    }

    @Test
    public void testSolver() {
        final Solver s = new Solver(bGoal);
        Assert.assertEquals(0, s.moves());
        Assert.assertFalse(s.solution().iterator().hasNext());
        Assert.assertTrue(s.isSolvable());
    }

    @Test
    public void testSolverUnsolved() {
        final Solver s = new Solver(b8t1);
        Assert.assertFalse(s.isSolvable());
//        TODO work this out:Assert.assertEquals(0, s.moves());
        Assert.assertTrue(s.solution().iterator().hasNext());
    }

    @Test
    public void testIsSolvable() {
        final Solver s = new Solver(bGoal);
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
    public void testNullIfNoSolution() {
        // new insolvable Solver
        // test .solution();
        final Solver s = new Solver(bUnsolvable);
        Assert.assertNull(s.solution());
    }

    @Test
    public void testExample() {
        // puzzle04.txt
        // 1  3           1  3           1  2  3        1  2  3        1  2  3
        // 4  2  5   =>   4  2  5   =>   4     5   =>   4  5      =>   4  5  6
        // 7  8  6        7  8  6        7  8  6        7  8  6        7  8

        final Board startBoard = 
                BoardTests.buildBoard(new int[]{1, 3, 0, 4, 2, 5, 7, 8, 6});
        final List<Board> expectedBoards = Arrays.asList(new Board[]{
                startBoard,
                BoardTests.buildBoard(new int[]{1, 3, 0, 4, 2, 5, 7, 8, 6}),
                BoardTests.buildBoard(new int[]{1, 2, 3, 4, 0, 5, 7, 8, 6}),
                BoardTests.buildBoard(new int[]{1, 2, 3, 4, 5, 0, 7, 8, 6}),
                bGoal});

        Solver solver = new Solver(startBoard);
        Assert.assertTrue(solver.isSolvable());
        Assert.assertEquals(4, solver.moves());
    }

    @Test
    public void testAlreadyGoal() {
        List<Board> expectedBoards = Arrays.asList(new Board[]{bGoal});
        Solver solver = new Solver(bGoal);
        Assert.assertTrue(solver.isSolvable());
        Assert.assertEquals(0, solver.moves());
    }

    @Test
    public void testInfeasible() {
        Board startBoard;
        Solver solver;

        // Goal Board, Twined
        startBoard = bGoal.twin();
        solver = new Solver(startBoard);
        assertInfeasible(solver);

        // As per http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
        startBoard = BoardTests.buildBoard(new int[]{1, 2, 3, 4, 5, 6, 8, 7, 0});
        solver = new Solver(startBoard);
        assertInfeasible(solver);
    }

    private void assertInfeasible(Solver solver) {
        Assert.assertEquals(-1, solver.moves());
        Assert.assertFalse(solver.isSolvable());
        Assert.assertEquals(null, solver.solution());
    }
}
