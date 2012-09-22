import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PointSETTest {

    @Test
    public void testIsEmpty() {
        final PointSET pointSet = new PointSET();
        final Point2D randomPoint1 = buildRandomPoint();
        final Point2D randomPoint2 = buildRandomPoint();

        // Empty
        Assert.assertTrue(pointSet.isEmpty());
        
        // Insert One
        pointSet.insert(randomPoint1);
        Assert.assertFalse(pointSet.isEmpty());

        // Insert More
        pointSet.insert(randomPoint2);
        pointSet.insert(randomPoint1); // Duplicate!
        Assert.assertFalse(pointSet.isEmpty());
    }

    @Test
    public void testSize() {
        final PointSET pointSet = new PointSET();
        final Point2D randomPoint1 = buildRandomPoint();
        final Point2D randomPoint2 = buildRandomPoint();

        // Empty
        Assert.assertEquals(0, pointSet.size());
        
        // Insert One
        pointSet.insert(randomPoint1);
        Assert.assertEquals(1, pointSet.size());

        // Insert More
        pointSet.insert(randomPoint2);
        pointSet.insert(randomPoint1); // Duplicate!
        Assert.assertEquals(2, pointSet.size());
    }
    
    @Test
    public void testContains() {
        final PointSET pointSet = new PointSET();
        final Point2D randomPoint1 = buildRandomPoint();
        final Point2D randomPoint2 = buildRandomPoint();

        // Empty
        Assert.assertFalse(pointSet.contains(randomPoint1));
        
        // Insert One
        pointSet.insert(randomPoint1);
        Assert.assertTrue(pointSet.contains(randomPoint1));

        // Insert More
        pointSet.insert(randomPoint2);
        pointSet.insert(randomPoint1); // Duplicate!
        Assert.assertTrue(pointSet.contains(randomPoint2));
        Assert.assertTrue(pointSet.contains(randomPoint1));
    }
    
    @Test
    public void findNearestPoint() {
        final Point2D point1 = new Point2D(0.1, 0.4);
        final Point2D point2 = new Point2D(0.0, 0.0);
        final Point2D point3 = new Point2D(0.6, 0.5);

        final PointSET set = new PointSET();
        set.insert(point1);
        set.insert(point2);
        //set.insert(point3);

        final Point2D nearest = set.nearest(point3);
        Assert.assertEquals(point1, nearest);
    }

    @Test
    public void testRange() {
        final Point2D point1 = new Point2D(0.1, 0.4);
        final Point2D point2 = new Point2D(0.0, 0.0);
        final Point2D point3 = new Point2D(0.6, 0.5);

        final PointSET set = new PointSET();
        set.insert(point1);
        set.insert(point2);
        set.insert(point3);

        final Iterable<Point2D> pointsInRange = 
                set.range(new RectHV(0.4, 0.3, 0.8, 0.6));
        Assert.assertNotNull(pointsInRange);

        List<Point2D> points = getAll(pointsInRange);
        Assert.assertEquals(1, points.size());
        Assert.assertTrue(points.contains(point3));

    }
    
    @Test
    public void testNullNearestIfEmptySet() {
        final PointSET set = new PointSET();
        Assert.assertNull(set.nearest(buildRandomPoint()));
    }
    
    @Test
    public void testNearestNotNullOnNonEmptySet() {
        final PointSET set = new PointSET();
        set.insert(buildRandomPoint());
        Assert.assertNotNull(set.nearest(buildRandomPoint()));
    }

    public static List<Point2D> getAll(final Iterable<Point2D> points) {
        List<Point2D> result = new ArrayList<Point2D>();
        for (Point2D point : points) {
            result.add(point);
        }
        return result;
    }

    public static Point2D buildRandomPoint() {
        Point2D result = new Point2D(StdRandom.random(), StdRandom.random());
        return result;
    }
}