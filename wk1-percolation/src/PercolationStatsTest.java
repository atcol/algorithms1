import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class PercolationStatsTest {

    private PercolationStats stats;
    
    @Before
    public void setUp() {
        stats = new PercolationStats(200, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPercolationStats() {
        /* N = -23, T = 42 */
        new PercolationStats(-23, 42);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testPercolationStats2() {
        /* N = 23, T = 0 */
        new PercolationStats(23, 0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testPercolationStats3() {
        /* N = -42, T = 0 */
        new PercolationStats(-42, 0);
    }

    @Test
    public void testMean() {
        Assert.assertTrue(stats.mean() > 0.0d);
    }

    @Test
    public void testStddev() {
        Assert.assertTrue(stats.stddev() > 0.0d);
    }
}
