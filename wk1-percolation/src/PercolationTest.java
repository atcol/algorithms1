import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PercolationTest {

    @Before
    public void setUp() {

    }
    
    @Test
    public void test5x5IsOpen() {
        final Percolation p = new Percolation(5);
        p.open(1, 1);
        p.open(1, 2);
        p.open(1, 3);
        p.open(1, 4);
        p.open(1, 5);
        Assert.assertTrue(p.isOpen(1, 1));
        Assert.assertTrue(p.isOpen(1, 2));
        Assert.assertTrue(p.isOpen(1, 3));
        Assert.assertTrue(p.isOpen(1, 4));
        Assert.assertTrue(p.isOpen(1, 5));
        
        for (int i = 2; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                final boolean open = p.isOpen(i, j);
                System.out.printf("%d, %d = %s\n", i, j, open);
                Assert.assertFalse(
                    String.format("Site %d, %d should NOT be open", 
                    i, j),
                    open);
            }
        }
    }
    
    @Test
    public void test5x5PercolatesTrue() {
        final Percolation p = new Percolation(5);
        p.open(1, 1); // Open left-most edge of grid
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);
        Assert.assertTrue(p.percolates());
    }
    
    @Test
    public void testSmallGridPercolatesTrue() {
        final Percolation p = new Percolation(1);
        p.open(1, 1); // Open left-most edge of grid
        Assert.assertTrue(p.percolates());
    }
    
    @Test
    public void test5x5PercolatesFalse() {
        final int n = 5;
        final Percolation p = new Percolation(n);
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                Assert.assertFalse(String.format(
                        "%d, %d should NOT be open", i, j),
                        p.isOpen(i, j));
                Assert.assertFalse(String.format(
                        "%d, %d should NOT be full", i, j),
                        p.isFull(i, j));
            }
        }
        Assert.assertFalse(p.percolates());
    }
    
    @Test
    public void test5x5PercolatesFalseWithSomeOpen() {
        final Percolation p = new Percolation(5);
        p.open(1, 1);
        p.open(2, 2);
        Assert.assertFalse(p.percolates());
    }
    
    @Test
    public void test5x5IsFullTrue() {
        final Percolation p = new Percolation(5);
        p.open(1, 1);
        Assert.assertTrue(p.isFull(1, 1));
        p.open(2, 1);
        Assert.assertTrue(p.isFull(2, 1));
        p.open(1, 2);
        Assert.assertTrue(p.isFull(1, 2));
        p.open(2, 2);
        Assert.assertTrue(p.isFull(2, 2));
    }
    
    @Test
    public void test5x5IsFullFalse() {
        final Percolation p = new Percolation(5);
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                Assert.assertFalse(
                    String.format("isFull(%d,%d) should be false but was true",
                    i, i),
                    p.isFull(i, j));
            }
        }
        
        p.open(1, 1);
        Assert.assertTrue(p.isFull(1, 1));
    }
    
    @Test
    public void test2x2IsFull() {
        final Percolation p = new Percolation(2);
        p.open(1, 1);
        Assert.assertTrue(p.isFull(1, 1));
        p.open(2, 2); // is Open but NOT full
        Assert.assertFalse(p.isFull(2, 2));
    }
    
    @Test
    public void test2x2IsFullFalse() {
        final Percolation p = new Percolation(2);
        p.open(1, 1);
        p.open(2, 2);
        Assert.assertFalse(p.isFull(1, 2));
        Assert.assertFalse(p.isFull(2, 1));
        Assert.assertFalse(p.isFull(2, 2));
    }
    
    @Test
    public void test1x1PercolatesFalse() {
        final Percolation p = new Percolation(1);
        Assert.assertFalse(p.percolates());
    }
    
    @Test
    public void test1x1IsFullFalse() {
        final Percolation p = new Percolation(1);
        Assert.assertFalse(p.isFull(1, 1));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void test5x5IsOpenThrowsOutOfBounds() {
        new Percolation(5).isOpen(-1, 1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void test5x5IsOpenThrowsOutOfBounds2() {
        new Percolation(5).isOpen(4, -1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void test5x5IsOpenThrowsOutOfBounds3() {
        new Percolation(5).isOpen(7, 2);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void test5x5IsOpenThrowsOutOfBounds4() {
        new Percolation(5).isOpen(2, 7);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void test5x5IsFullThrowsOutOfBounds() {
        new Percolation(5).isFull(-1, 0);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void test5x5IsFullThrowsOutOfBounds2() {
        new Percolation(5).isFull(1, -1);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void test5x5IsFullThrowsOutOfBounds3() {
        new Percolation(5).isFull(7, 2);
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void test5x5IsFullThrowsOutOfBounds4() {
        new Percolation(5).isFull(2, 6);
    }
}
