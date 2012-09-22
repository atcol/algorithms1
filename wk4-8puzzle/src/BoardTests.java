import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
    private final int[][] goalBoard = {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 }};
    /*
     * hamming = 7 manhattan = 14
     */
    private int[][] testBoardA = {{ 4, 5, 6 }, { 3, 0, 1 }, { 2, 8, 7 }};
    // Neighbors of A
    private int[][] testLeftA = {{ 4, 5, 6 }, { 0, 3, 1 }, { 2, 8, 7 }};
    private int[][] testRightA = {{ 4, 5, 6 }, { 3, 1, 0 }, { 2, 8, 7 }};
    private int[][] testUpA = {{ 4, 0, 6 }, { 3, 5, 1 }, { 2, 8, 7 } };
    private int[][] testDownA = {{ 4, 5, 6 }, { 3, 8, 1 }, { 2, 0, 7 }};

    /*
     * hamming = 1 manhattan = 1
     */
    private int[][] testBoardB = {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 }};

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
        Assert.assertTrue(goal.dimension() == goalBoard.length);
    }

    @Test
    public void testHamming() {
        Assert.assertEquals(0, bGoal.hamming());
//        Assert.assertEquals(8, b8t1.hamming());
        Assert.assertEquals(0, goal.hamming());
        Assert.assertEquals(7, A.hamming());
        Assert.assertEquals(1, B.hamming());
    }

    @Test
    public void testManhattan() {
        // sum of manhattan dist for each item out of place
        final int[][] a = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        final Board b = new Board(a);
        Assert.assertEquals(10, b.manhattan());
        Assert.assertEquals(0, new Board(new int[0][0]).manhattan());
        Assert.assertEquals(0, new Board(this.goalBoard).manhattan());
    }
    
    @Test
    public void testManhattan2() {
        Board testBoard;

        // Goal Board
        Assert.assertEquals(0, bGoal.manhattan());

        // As per:
        // http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
        testBoard = buildBoard(new int[] { 8, 1, 3, 4, 0, 2, 7, 6, 5 });
        Assert.assertEquals(10, testBoard.manhattan());

        // As per actual grading script, 2x2
        testBoard = buildBoard(new int[] { 3, 0, 2, 1 });
        Assert.assertEquals(5, testBoard.manhattan());

        // As per actual grading script, 3x3
        testBoard = buildBoard(new int[] { 8, 2, 4, 0, 7, 5, 6, 1, 3 });
        Assert.assertEquals(17, testBoard.manhattan());

        // As per actual grading script, 4x4
        testBoard = buildBoard(new int[] { 9, 0, 5, 7, 12, 15, 14, 13, 1, 6, 3,
                2, 11, 4, 8, 10 });
        Assert.assertEquals(45, testBoard.manhattan());

        // As per actual grading script, 5x5
        testBoard = buildBoard(new int[] { 12, 9, 8, 10, 18, 1, 14, 3, 19, 0,
                7, 15, 6, 23, 24, 17, 4, 16, 20, 11, 21, 5, 22, 2, 13 });
        Assert.assertEquals(67, testBoard.manhattan());

        // As per actual grading script, 9x9
        testBoard = buildBoard(new int[] { 1, 59, 51, 18, 47, 66, 44, 32, 31,
                50, 65, 54, 13, 74, 56, 30, 79, 14, 12, 8, 75, 11, 76, 72, 4,
                37, 3, 5, 71, 61, 53, 21, 33, 16, 7, 64, 23, 15, 25, 28, 46,
                68, 78, 70, 58, 69, 36, 40, 77, 39, 9, 55, 45, 19, 43, 0, 29,
                67, 57, 41, 2, 63, 73, 27, 52, 22, 60, 20, 6, 34, 17, 38, 10,
                24, 49, 42, 80, 62, 26, 48, 35 });
        Assert.assertEquals(489, testBoard.manhattan());

        // As per actual grading script, 10x10
        testBoard = buildBoard(new int[] { 59, 13, 66, 61, 25, 69, 63, 98, 62,
                91, 72, 35, 49, 20, 24, 50, 45, 67, 38, 11, 93, 21, 8, 5, 4,
                22, 82, 81, 47, 1, 87, 19, 95, 86, 40, 43, 80, 92, 12, 58, 53,
                74, 70, 23, 31, 15, 85, 14, 60, 48, 3, 29, 2, 44, 17, 96, 16,
                75, 42, 73, 78, 39, 56, 94, 65, 30, 89, 90, 64, 26, 84, 99, 83,
                54, 33, 52, 77, 34, 46, 0, 76, 88, 32, 10, 41, 28, 9, 79, 97,
                6, 55, 36, 7, 71, 68, 18, 51, 57, 37, 27, });
        Assert.assertEquals(658, testBoard.manhattan());

    }

    @Test
    public void testIsGoal() {
        Assert.assertTrue(bGoal.isGoal());
    }
    
    @Test
    public void testIsGoalFalse() {
        Assert.assertFalse(b8t1.isGoal());
        Assert.assertFalse(new Board(new int[5][5]).isGoal());
        Assert.assertFalse("Board A reports that it is the goal board.", A.isGoal());
        Assert.assertFalse("Board B reports that it is the goal board.", B.isGoal());
    }
    
    @Test
    public void testTwin2x2() {
        final int[][] easyPuzArray = {{2, 0}, {1, 3}};
        final int[][] easyPuzArrayTwin = {{2, 0}, {3, 1}};
        final Board b = new Board(easyPuzArray);
        final Board twinBoard = new Board(easyPuzArrayTwin);
        Assert.assertEquals(twinBoard, b.twin());
        Assert.assertEquals(b, twinBoard.twin());
    }
    
    @Test
    public void testTwin3x3() {
        final int[][] brdArray = {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 }};
        final Board b = new Board(brdArray);
        
        final Board twin = new Board(
              new int[][] {{ 2, 1, 3 }, { 4, 5, 6 }, { 7, 0, 8 }});
        for (int i = 0; i < 100; i++) {
            final Board actualTwin = b.twin();
            Assert.assertEquals("actualTwin = " + actualTwin,
                    twin, actualTwin);
        }

        // Now test that the switch is on 2nd row because 0 is on 1st
        final int[][] brdArray2 = {{ 0, 2, 3 }, { 4, 5, 6 }, { 7, 1, 8 }};
        final Board b2 = new Board(brdArray2);
        final Board twin2 = new Board(
              new int[][] {{ 0, 2, 3 }, { 5, 4, 6 }, { 7, 1, 8 }});
        for (int i = 0; i < 100; i++) {
            final Board actualTwin = b2.twin();
            Assert.assertEquals("actualTwin = " + actualTwin,
                    twin2, actualTwin);
        }
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
                "3  \n" 
                        +
                "   1  3 \n"
                        +
                "4  2  5 \n"
                        +
                "7  8  6 \n";

        final String strT2 = 
                "3  \n"
                        +
                "   3  1 \n"
                        +
                "4  2  5 \n"
                        +
                "7  8  6 \n";

        final String strT3 = 
                "3  \n" 
                        +
                "   1  3 \n"
                        +
                "2  4  5 \n"
                        +
                "7  8  6 \n";

        final String strT4 = 
                "3  \n" 
                        +
                "   1  3 \n"
                        +
                "4  5  2 \n"
                        +
                "7  8  6 \n";

        final String strT5 = 
                "3  \n" 
                        +
                "   1  3 \n"
                        +
                "4  2  5 \n"
                        +
                "8  7  6 \n";

        final String strT6 = 
                "3  " 
                        +
                "   1  3 \n"
                        +
                "4  2  5 \n"
                        +
                "7  6  8 \n";
        int foundCount = 0;
        final List<String> perms = new ArrayList<String>(6);
        perms.add(strT1);
        perms.add(strT2);
        perms.add(strT3);
        perms.add(strT4);
        perms.add(strT5);
        perms.add(strT6);

        while (foundCount != 6) {
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
        Assert.assertEquals(goal, b);
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
    public void testNeighbours() {
        int counter = 0, multiple = 0;
        // Neighbors of A
        Iterator<Board> itA = A.neighbors().iterator();
        Board aLeft = new Board(testLeftA);
        Board aRight = new Board(testRightA);
        Board aUp = new Board(testUpA);
        Board aDown = new Board(testDownA);
        // has left,right,up,down?

        boolean l = false, r = false, u = false, d = false;
        // ----------Test neighbors of A-------
        while (itA.hasNext()) {
            Board b = itA.next();

            // test for 'left'
            if (b.equals(aLeft)) {
                if (!l) {
                    l = true;
                    multiple++;
                } else {
                    Assert.fail("Multiple 'left' neighbors.");
                }
            }

            // test for 'right'
            if (b.equals(aRight)) {
                if (!r) {
                    r = true;
                    multiple++;
                } else {
                    Assert.fail("Multiple 'right' neighbors.");
                }
            }

            // test for 'up'
            if (b.equals(aUp)) {
                if (!u) {
                    u = true;
                    multiple++;
                } else {
                    Assert.fail("Multiple 'up' neighbors.");
                }
            }

            // test for 'down'
            if (b.equals(aDown)) {
                if (!d) {
                    d = true;
                    multiple++;
                } else {
                    Assert.fail("Multiple 'down' neighbors.");
                }
            }

            // count the number of neighbors
            counter++;

            Assert.assertFalse(
                    "Some of the neighboring sites are equal (impossible).",
                    multiple > counter);

        }
        // Check the number of neighbors
        Assert.assertFalse("The number of neighbors is more than the actual number.",
                counter > 4);
        // check specific neighbors
        Assert.assertTrue("There should be a 'left' neighbor.", l);
        Assert.assertTrue("There should be a 'right' neighbor.", r);
        Assert.assertTrue("There should be an 'up' neighbor.", u);
        Assert.assertTrue("There should be a 'down' neighbor.", d);
    }

    @Test
    public void testNeighboursCC() {
        // Test 1
        int[][] noUpRight = { { 1, 0 }, { 2, 3 } };
        int[][] nleft = { { 0, 1 }, { 2, 3 } };
        int[][] nDown = { { 1, 3 }, { 2, 0 } };
        // Test 2
        int[][] noDownLeft = { { 1, 2 }, { 0, 3 } };
        int[][] nUp = { { 0, 2 }, { 1, 3 } };
        int[][] nRight = { { 1, 2 }, { 3, 0 } };

        // Test 1
        Board b = new Board(noUpRight);
        Board bLeft = new Board(nleft);
        Board bDown = new Board(nDown);
        Iterator<Board> it = b.neighbors().iterator();

        boolean boolLeft = false, boolDown = false;
        int counter = 0;

        while (it.hasNext()) {
            Board x = it.next();
            counter++;

            if (x.equals(bLeft)) {
                if (!boolLeft) {
                    boolLeft = true;
                } else {
                    Assert.fail("Multiple \"left\" neighbors.");
                }
            }

            if (x.equals(bDown)) {
                if (!boolDown) {
                    boolDown = true;
                } else {
                    Assert.fail("Multiple \"down\" neighbors.");
                }
            }
        }

        Assert.assertTrue("(Test 1)Wrong number of neighbors returned: "
                + counter, counter == 2);

        // Test 2
        b = new Board(noDownLeft);
        Board bRight = new Board(nRight);
        Board bUp = new Board(nUp);
        it = b.neighbors().iterator();

        boolean boolRight = false, boolUp = false;
        counter = 0;

        while (it.hasNext()) {
            Board x = it.next();
            counter++;

            if (x.equals(bRight)) {
                if (!boolRight) {
                    boolRight = true;
                } else {
                    Assert.fail("Multiple \"right\" neighbors.");
                }
            }

            if (x.equals(bUp)) {
                if (!boolUp) {
                    boolUp = true;
                } else {
                    Assert.fail("Multiple \"up\" neighbors.");
                }
            }
        }

        Assert.assertTrue("(Test 2)Wrong number of neighbors returned: "
                + counter, counter == 2);
    }

    @Test
    public void testToString() {
        Assert.assertEquals(
                "3\n"
                    +
                " 1  2  3 \n" 
                    +
                " 4  5  6 \n" 
                    +
                " 7  8  0 \n",
            bGoal.toString()
        );
        Assert.assertEquals(
                "3\n" 
                    +
                " 0  8  7 \n" 
                    +
                " 6  5  4 \n" 
                    +
                " 3  2  1 \n",
            b8t1.toString()
        );
    }
    
//    @Test
//    public void testCompareToLT() {
//        // A hamming is 7, B's is 1
//        Assert.assertEquals(-1, B.compareTo(A));
//        Assert.assertEquals(-1, bGoal.compareTo(A));
//    }
//    
//    @Test
//    public void testCompareToGT() {
//        Assert.assertEquals(1, A.compareTo(B));
//        Assert.assertEquals(1, A.compareTo(bGoal));
//    }
//    
//    @Test
//    public void testCompareToEqual() {
//        Assert.assertEquals(0, A.compareTo(A));
//        Assert.assertEquals(0, bGoal.compareTo(bGoal));
//    }

    public static Board buildBoard(int[] data) {
        int N = (int) Math.sqrt(data.length);
        int[][] blocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int dataIndex = (i * N) + j;
                blocks[i][j] = data[dataIndex];
            }
        }

        Board result = new Board(blocks);
        return result;
    }
}
