import static org.junit.Assert.fail;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class PointTest {
	
	private Point p00;
	private Point p01;
	private Point p11;
	private Point p10;
	private Point p24;
	private Point p69;
	private Point p55;
	
	@Before
	public void setUp() {
		p00 = new Point(0, 0);
		p01 = new Point(0, 1);
		p11= new Point(1, 1);
		p10 = new Point(1, 0);
		p55 = new Point(5, 5);
		p24 = new Point(2, 4);
		p69 = new Point(6, 9);
	}

	@Test
	public void testPoint() {
		final Object o1 = new Point(1, 1);
		final Object o2 = new Point(0, 1);
		final Object o3 = new Point(-421, 7924);
		final Object o4 = new Point(5811, -0);
		Assert.assertNotSame(o1, o2);
		Assert.assertNotSame(o1, o3);
		Assert.assertNotSame(o1, o4);
		Assert.assertNotSame(o2, o1);
		Assert.assertNotSame(o2, o3);
		Assert.assertNotSame(o2, o4);
		Assert.assertNotSame(o3, o1);
		Assert.assertNotSame(o3, o2);
		Assert.assertNotSame(o3, o4);
		Assert.assertNotSame(o4, o1);
		Assert.assertNotSame(o4, o2);
		Assert.assertNotSame(o4, o3);
	}

	/*
	 * The SLOPE_ORDER comparator should compare points by the slopes they
	 * make with the invoking point (x0, y0). Formally, the point (x1, y1)
	 * is less than the point (x2, y2) if and only if the slope (y1 − y0) /
	 * (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0). Treat
	 * horizontal, vertical, and degenerate line segments as in the
	 * slopeTo() method.
	 */
	@Test
	public void testSlopeOrderComparator() {
		final Point[] targetArr = new Point[]{p00, p11, p10};
		Arrays.sort(targetArr, p00.SLOPE_ORDER);
		Assert.assertEquals(new Point[]{p11, p00, p10}, targetArr);
		fail("Work out what's correct here");
	}
	
	@Test
	public void testSlopOrderComparatorCompare() {
        final Point originPoint = new Point(1, 1);

        // Same slope (straight line)
        Assert.assertEquals(0, originPoint.SLOPE_ORDER.compare(new Point(2, 2), p55));

        // First slope is smaller
        Assert.assertEquals(-1, originPoint.SLOPE_ORDER.compare(new Point(5, 10), new Point(5, 25)));

        // Second slope is smaller
        Assert.assertEquals(+1, originPoint.SLOPE_ORDER.compare(new Point(5, 25), new Point(5, 10)));
	}
	
	@Test
	public void testSlopeOrderComparatorYieldsPositiveInfinity() {
		fail("Not implemented");
	}

	/*
	 * The slopeTo() method should return the slope between the invoking
	 * point (x0, y0) and the argument point (x1, y1), which is given by the
	 * formula (y1 − y0) / (x1 − x0). Treat the slope of a horizontal line
	 * segment as positive zero [added 7/29]; treat the slope of a vertical
	 * line segment as positive infinity; treat the slope of a degenerate
	 * line segment (between a point and itself) as negative infinity.
	 */
	@Test
	public void testSlopeToExpectedValues() {
//		(y1 − y0) / (x1 − x0)
		Assert.assertEquals(-0d, p11.slopeTo(p01));
		
//		 (9 - 4) / (6 - 2)
		Assert.assertEquals(1.25d, p24.slopeTo(p69));
		
//		 (1 - 9) / (1 - 6)
		Assert.assertEquals(1.6d, p69.slopeTo(p11));
		

        // Negative
		Assert.assertEquals(-2, (new Point(1, 2)).slopeTo(new Point(3, -2)), 0d);
        // Normal
		Assert.assertEquals(2.25, p11.slopeTo(new Point(5, 10)), 0d);
		Assert.assertEquals(6.0, p11.slopeTo(new Point(5, 25)), 0d);
	}
	
	@Test
	public void testSlopeToVerticalYieldsPositiveInfinity() {
		Assert.assertEquals(Double.POSITIVE_INFINITY, p00.slopeTo(p11));
		Assert.assertEquals(Double.POSITIVE_INFINITY, 
				(new Point(8, 5)).slopeTo(new Point(8, 0)), 0d);
	}
	
	@Test
	public void testSlopeToYieldsNegativeInfinity() {
		Assert.assertEquals(Double.NEGATIVE_INFINITY, p55.slopeTo(p55), 0d);
	}
	
	@Test
	public void testSlopeToHorizontalYieldsPositiveZero() {
		Assert.assertEquals(+0d, p00.slopeTo(p10));
        // Horizontal Line
		Assert.assertEquals(+0d, (new Point(0, 5)).slopeTo(new Point(8, 5)), 0d);
		Assert.assertEquals(+0d, (new Point(8, 5)).slopeTo(new Point(0, 5)), 0d);
	}

	/*
	 * The compareTo() method should compare points by their y-coordinates,
	 * breaking ties by their x-coordinates. Formally, the invoking point
	 * (x0, y0) is less than the argument point (x1, y1) if and only if
	 * either y0 < y1 or if y0 = y1 and x0 < x1.
	 */
	@Test
	public void testCompareToLess() {
		Assert.assertEquals(-1, p00.compareTo(p01));
		Assert.assertEquals(-1, p00.compareTo(p11));
		Assert.assertEquals(-1, p00.compareTo(new Point(40, 141)));
		Assert.assertEquals(-1, new Point(1, 1).compareTo(new Point(1, 2)));
		Assert.assertEquals(-1, new Point(1, 1).compareTo(new Point(2, 1)));

	}
	
	@Test
	public void testCompareToGreater() {
		Assert.assertEquals(1, p11.compareTo(p00));
		Assert.assertEquals(1, p11.compareTo(p10));
		Assert.assertEquals(1, p11.compareTo(p10));
		
		Assert.assertEquals(1, p10.compareTo(p00));// breaks tie on X
		Assert.assertEquals(1, new Point(4, 0).compareTo(p00));// breaks tie on X
		
		Assert.assertEquals(1, new Point(1, 2).compareTo(new Point(1, 1)));
		Assert.assertEquals(1, new Point(2, 1).compareTo(new Point(1, 1)));


	}
	
	@Test
	public void testCompareToEqual() {
		Assert.assertEquals(0, p00.compareTo(new Point(0, 0)));
		Assert.assertEquals(0, p01.compareTo(new Point(0, 1)));
		Assert.assertEquals(0, p11.compareTo(new Point(1, 1)));
		Assert.assertEquals(0, p10.compareTo(new Point(1, 0)));
        // Equal
		Assert.assertEquals(0, new Point(0, 1).compareTo(new Point(0, 1)));
	}
	
	@Test
	public void testCompareToBreaksTiesOnX() {
		Assert.assertEquals(1, p10.compareTo(new Point(0, 0)));// X greater
		Assert.assertEquals(1, p11.compareTo(p00));// X greater
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
