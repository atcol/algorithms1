import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class KdTreeTests {
    
    @Test
    public void testEmpty() {
        final KdTree kdtree = new KdTree();
        final Point2D randomPoint = buildRandomPoint();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());

        // Insert One
        kdtree.insert(randomPoint);
        Assert.assertFalse(kdtree.isEmpty());
    }
    
    @Test
    public void testSize() {
        final KdTree kdtree = new KdTree();
        final Point2D randomPoint = buildRandomPoint();

        // Empty
        Assert.assertEquals(0, kdtree.size());

        // Insert One
        kdtree.insert(randomPoint);
        Assert.assertEquals(1, kdtree.size());
    }

    @Test
    public void testContains() {
        final KdTree kdtree = new KdTree();
        final Point2D randomPoint = buildRandomPoint();

        // Empty
        Assert.assertFalse(kdtree.contains(randomPoint));

        // Insert One
        kdtree.insert(randomPoint);
        Assert.assertTrue(kdtree.contains(randomPoint));
    }
    
    @Test
    public void testInsert() {
        final KdTree kdtree = new KdTree();
        final Point2D randomPoint = buildRandomPoint();

        // Insert One
        kdtree.insert(randomPoint);
        Assert.assertTrue(kdtree.contains(randomPoint));
        Assert.assertEquals(1, kdtree.size());
    }
    
    @Test
    public void testInsert10() {
        final KdTree kdtree = new KdTree();
        final Point2D randomPoint = buildRandomPoint();

        final int N = 10;
        int count = 0;
        for (int i = 0; i < N; i++) {
            final Point2D point = buildRandomPoint();
            kdtree.insert(point);
            count++;
            Assert.assertEquals(count, kdtree.size());
//            final boolean contains = kdtree.contains(point);
//            Assert.assertTrue("Iteration " + i + " contains " + point,
//                    contains);
        }
        kdtree.insert(randomPoint);
//        Assert.assertTrue(kdtree.contains(randomPoint));
        Assert.assertEquals(11, kdtree.size());
    }
    
    @Test
    public void testInsert10k() {
        final KdTree kdtree = new KdTree();
        final Point2D randomPoint = buildRandomPoint();

        final int N = 10000;
        int count = 0;
        for (int i = 0; i < N; i++) {
            kdtree.insert(buildRandomPoint());
            count++;
            Assert.assertEquals(count, kdtree.size());
        }
        kdtree.insert(randomPoint);
//        Assert.assertTrue(kdtree.contains(randomPoint));
        Assert.assertEquals(10001, kdtree.size());
    }

    @Test
    public void testSize10() {
        final PointSET set = new PointSET();
        final KdTree kdtree = new KdTree();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());
        Assert.assertEquals(0, kdtree.size());

        Point2D point;
        for (int i = 0; i < 10; i++) {
            point = buildRandomPoint();
            set.insert(point);
            kdtree.insert(point);
        }

        Assert.assertFalse(kdtree.isEmpty());
        Assert.assertEquals(set.size(), kdtree.size());
    }

    @Test
    public void testSize100() {
        final PointSET set = new PointSET();
        final KdTree kdtree = new KdTree();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());
        Assert.assertEquals(0, kdtree.size());

        Point2D point;
        for (int i = 0; i < 100; i++) {
            point = buildRandomPoint();
            set.insert(point);
            kdtree.insert(point);
        }

        Assert.assertFalse(kdtree.isEmpty());
        Assert.assertEquals(set.size(), kdtree.size());
    }

    @Test
    public void testSize1k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());
        Assert.assertEquals(0, kdtree.size());

        Point2D point;
        for (int i = 0; i < 1000; i++) {
            point = buildRandomPoint();
            set.insert(point);
            kdtree.insert(point);
        }

        Assert.assertFalse(kdtree.isEmpty());
        Assert.assertEquals(set.size(), kdtree.size());
    }

    @Test
    public void testSize10k() {
        final PointSET set = new PointSET();
        final KdTree kdtree = new KdTree();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());
        Assert.assertEquals(0, kdtree.size());

        Point2D point;
        for (int i = 0; i < 10000; i++) {
            point = buildRandomPoint();
            set.insert(point);
            kdtree.insert(point);
        }

        Assert.assertFalse(kdtree.isEmpty());
        Assert.assertEquals(set.size(), kdtree.size());
    }
    
    @Test
    public void testContains4() {
        final KdTree kdtree = new KdTree();
        final Point2D pnt1 = new Point2D(0.1d, 0.1d);

        // Insert One
        kdtree.insert(pnt1); // inserts at root
        Assert.assertTrue(kdtree.contains(pnt1));
        Assert.assertEquals(1, kdtree.size());
        
        final Point2D pnt2 = new Point2D(0.02d, 0.02d);
        kdtree.insert(pnt2); // inserts @ root.lb
        Assert.assertTrue(kdtree.contains(pnt2));
        Assert.assertEquals(2, kdtree.size());
        
        final Point2D pnt3 = new Point2D(0.3d, 0.3d);
        kdtree.insert(pnt3); // inserts @ root.rt
        Assert.assertTrue(kdtree.contains(pnt3));
        Assert.assertEquals(3, kdtree.size());
        
        final Point2D pnt4 = new Point2D(0.004d, 0.004d);
        kdtree.insert(pnt4); // inserts @ root.lb.lb
        Assert.assertTrue(kdtree.contains(pnt4));
        Assert.assertEquals(4, kdtree.size());
    }
    
    @Test
    public void testContains10() {
        final HashSet<Point2D> set = new HashSet<Point2D>();
        final KdTree kdtree = new KdTree();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());
        Assert.assertEquals(0, kdtree.size());

        Point2D point;
        final int N = 10;
        for (int i = 0; i < N; i++) {
//            point = new Point2D(i/2, i/3);
            point = buildRandomPoint();
            set.add(point);
            kdtree.insert(point);
        }
        
        Assert.assertEquals(N, kdtree.size());

        Iterator<Point2D> i = set.iterator();
        while (i.hasNext()) {
            point = i.next();
            final boolean contains = kdtree.contains(point);
            Assert.assertTrue("Must contain " + point, contains);
        }

        Assert.assertFalse(kdtree.isEmpty());
        Assert.assertEquals(set.size(), kdtree.size());
    }

    @Test
    public void testContains1k() {
        final HashSet<Point2D> set = new HashSet<Point2D>();
        final KdTree kdtree = new KdTree();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());
        Assert.assertEquals(0, kdtree.size());

        Point2D point;
        final int N = 1000;
        for (int i = 0; i < N; i++) {
//            point = new Point2D(i/2, i/3);
            point = buildRandomPoint();
            set.add(point);
            kdtree.insert(point);
        }
        
        Assert.assertEquals(N, kdtree.size());

        Iterator<Point2D> i = set.iterator();
        while (i.hasNext()) {
            point = i.next();
            Assert.assertTrue("Must contain " + point, kdtree.contains(point));
        }

        Assert.assertFalse(kdtree.isEmpty());
        Assert.assertEquals(set.size(), kdtree.size());
    }

    @Test
    public void testContains10k() {
        HashSet<Point2D> set = new HashSet<Point2D>();
        KdTree kdtree = new KdTree();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());
        Assert.assertEquals(0, kdtree.size());

        Point2D point;
        for (int i = 0; i < 10000; i++) {
            point = buildRandomPoint();
            set.add(point);
            kdtree.insert(point);
        }

        Iterator<Point2D> i = set.iterator();
        while (i.hasNext()) {
            point = i.next();
            Assert.assertTrue(kdtree.contains(point));
        }

        Assert.assertFalse(kdtree.isEmpty());
        Assert.assertEquals(set.size(), kdtree.size());
    }

    @Test
    public void testContainsNo1k() {
        final Set<Point2D> setIn = new HashSet<Point2D>();
        final Set<Point2D> setOut = new HashSet<Point2D>();
        KdTree kdtree = new KdTree();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());
        Assert.assertEquals(0, kdtree.size());

        Point2D point;
        for (int i = 0; i < 1000; i++) {
            point = buildRandomPoint();
            setIn.add(point);
            kdtree.insert(point);
        }

        while (setOut.size() < 1000) {
            point = buildRandomPoint();
            if (!setIn.contains(point))
                setOut.add(point);
        }

        Iterator<Point2D> i = setOut.iterator();
        while (i.hasNext()) {
            point = i.next();
            Assert.assertFalse(kdtree.contains(point));
        }

        Assert.assertFalse(kdtree.isEmpty());
        Assert.assertEquals(setIn.size(), kdtree.size());
    }

    @Test
    public void testContainsNo10k() {
        HashSet<Point2D> setIn = new HashSet<Point2D>();
        HashSet<Point2D> setOut = new HashSet<Point2D>();
        KdTree kdtree = new KdTree();

        // Empty
        Assert.assertTrue(kdtree.isEmpty());
        Assert.assertEquals(0, kdtree.size());

        Point2D point;
        for (int i = 0; i < 10000; i++) {
            point = buildRandomPoint();
            setIn.add(point);
            kdtree.insert(point);
        }

        while (setOut.size() < 10000) {
            point = buildRandomPoint();
            if (!setIn.contains(point))
                setOut.add(point);
        }

        Iterator<Point2D> i = setOut.iterator();
        while (i.hasNext()) {
            point = i.next();
            Assert.assertFalse(kdtree.contains(point));
        }

        Assert.assertFalse(kdtree.isEmpty());
        Assert.assertEquals(setIn.size(), kdtree.size());
    }

    @Test
    public void testCircle10() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * circle10.txt
         */
        String filename = "data/circle10.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());
        // testing isEmpty()
        Assert.assertFalse(kdtree.isEmpty());

        // testing contains() with (0.81, 0.3)
        Assert.assertFalse(kdtree.contains(new Point2D(0.81, 0.3)));

        // testing contains() with (0.206107, 0.095492)
        Assert.assertTrue(kdtree.contains(new Point2D(0.206107, 0.095492)));

        // testing range() with [0.0, 0.81] x [0.0, 0.3]
        RectHV rect = new RectHV(0.0, 0.3, 0.0, 0.81);
        Assert.assertTrue(compareIterable(set.range(rect), kdtree.range(rect)));

        // testing nearest() with (0.81, 0.3)
        Point2D point1 = new Point2D(0.81, 0.3);
        Assert.assertEquals(set.nearest(point1), kdtree.nearest(point1));

        // testing nearest() with (0.206107, 0.095492)
        Point2D point2 = new Point2D(0.206107, 0.095492);
        Assert.assertEquals(set.nearest(point2), kdtree.nearest(point2));
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
        KdTree kdtree = new KdTree();
        kdtree.insert(point1);
        kdtree.insert(point2);
        kdtree.insert(point3);

        final RectHV rect = new RectHV(0.4, 0.3, 0.8, 0.6);
        Assert.assertTrue(compareIterable(set.range(rect), kdtree.range(rect)));
    }

    @Test
    public void testRange10k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * input10K.txt
         */
        String filename = "data/input10K.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        RectHV rect;
        // testing range() with [0.0, 0.81] x [0.0, 0.3]
        rect = new RectHV(0.0, 0.3, 0.0, 0.81);
        Assert.assertTrue(compareIterable(set.range(rect), kdtree.range(rect)));

        double x0, y0, x1, y1;
        for (int i = 0; i < 4000; i++) {
            x0 = StdRandom.random();
            y0 = StdRandom.random();
            x1 = StdRandom.random();
            y1 = StdRandom.random();

            rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0,
                    x1), Math.max(y0, y1));

            Assert.assertTrue(compareIterable(set.range(rect),
                    kdtree.range(rect)));
        }
    }

    @Test
    public void testRange20k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * input20K.txt
         */
        String filename = "data/input20K.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        RectHV rect;
        // testing range() with [0.0, 0.81] x [0.0, 0.3]
        rect = new RectHV(0.0, 0.3, 0.0, 0.81);
        Assert.assertTrue(compareIterable(set.range(rect), kdtree.range(rect)));

        double x0, y0, x1, y1;
        for (int i = 0; i < 4000; i++) {
            x0 = StdRandom.random();
            y0 = StdRandom.random();
            x1 = StdRandom.random();
            y1 = StdRandom.random();

            rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0,
                    x1), Math.max(y0, y1));

            Assert.assertTrue(compareIterable(set.range(rect),
                    kdtree.range(rect)));
        }
    }

    @Test
    public void testRange40k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * input40K.txt
         */
        String filename = "data/input40K.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        RectHV rect;
        // testing range() with [0.0, 0.81] x [0.0, 0.3]
        rect = new RectHV(0.0, 0.3, 0.0, 0.81);
        Assert.assertTrue(compareIterable(set.range(rect), kdtree.range(rect)));

        double x0, y0, x1, y1;
        for (int i = 0; i < 4000; i++) {
            x0 = StdRandom.random();
            y0 = StdRandom.random();
            x1 = StdRandom.random();
            y1 = StdRandom.random();

            rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0,
                    x1), Math.max(y0, y1));

            Assert.assertTrue(compareIterable(set.range(rect),
                    kdtree.range(rect)));
        }
    }

    @Test
    public void testRangeCircle100() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * circle100.txt
         */
        String filename = "data/circle100.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        RectHV rect;
        // testing range() with [0.0, 0.81] x [0.0, 0.3]
        rect = new RectHV(0.0, 0.3, 0.0, 0.81);
        Assert.assertTrue(compareIterable(set.range(rect), kdtree.range(rect)));

        double x0, y0, x1, y1;
        for (int i = 0; i < 4000; i++) {
            x0 = StdRandom.random();
            y0 = StdRandom.random();
            x1 = StdRandom.random();
            y1 = StdRandom.random();

            rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0,
                    x1), Math.max(y0, y1));

            Assert.assertTrue(compareIterable(set.range(rect),
                    kdtree.range(rect)));
        }
    }

    @Test
    public void testRangeCircle1k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * circle1000.txt
         */
        String filename = "data/circle1000.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        RectHV rect;
        // testing range() with [0.0, 0.81] x [0.0, 0.3]
        rect = new RectHV(0.0, 0.3, 0.0, 0.81);
        Assert.assertTrue(compareIterable(set.range(rect), kdtree.range(rect)));

        double x0, y0, x1, y1;
        for (int i = 0; i < 4000; i++) {
            x0 = StdRandom.random();
            y0 = StdRandom.random();
            x1 = StdRandom.random();
            y1 = StdRandom.random();

            rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0,
                    x1), Math.max(y0, y1));

            Assert.assertTrue(compareIterable(set.range(rect),
                    kdtree.range(rect)));
        }
    }

    @Test
    public void testRangeCircle10k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * circle10000.txt
         */
        String filename = "data/circle10000.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        RectHV rect;
        // testing range() with [0.0, 0.81] x [0.0, 0.3]
        rect = new RectHV(0.0, 0.3, 0.0, 0.81);
        Assert.assertTrue(compareIterable(set.range(rect), kdtree.range(rect)));

        double x0, y0, x1, y1;
        for (int i = 0; i < 4000; i++) {
            x0 = StdRandom.random();
            y0 = StdRandom.random();
            x1 = StdRandom.random();
            y1 = StdRandom.random();

            rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0,
                    x1), Math.max(y0, y1));

            Assert.assertTrue(compareIterable(set.range(rect),
                    kdtree.range(rect)));
        }
    }

    @Test
    public void testNearest() {
        // As per assignment,
        // http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
        Point2D point1 = new Point2D(0.1, 0.4);
        Point2D point2 = new Point2D(0.0, 0.0);
        Point2D point3 = new Point2D(0.6, 0.5);

        KdTree kdtree = new KdTree();
        kdtree.insert(point1);
        kdtree.insert(point2);

        Point2D nearest = kdtree.nearest(point3);
        Assert.assertEquals(point1, nearest);
    }

    @Test
    public void testNearest10k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * input10K.txt
         */
        String filename = "data/input10K.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        Point2D point;
        // testing nearest() with (0.81, 0.3)
        point = new Point2D(0.81, 0.3);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        // testing nearest() with (0.206107, 0.095492)
        point = new Point2D(0.206107, 0.095492);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        for (int i = 0; i < 10000; i++) {
            point = new Point2D(StdRandom.random(), StdRandom.random());
            Assert.assertEquals(set.nearest(point), kdtree.nearest(point));
        }
    }

    @Test
    public void testNearest20k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * input20K.txt
         */
        String filename = "data/input20K.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        Point2D point;
        // testing nearest() with (0.81, 0.3)
        point = new Point2D(0.81, 0.3);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        // testing nearest() with (0.206107, 0.095492)
        point = new Point2D(0.206107, 0.095492);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        for (int i = 0; i < 10000; i++) {
            point = new Point2D(StdRandom.random(), StdRandom.random());
            Assert.assertEquals(set.nearest(point), kdtree.nearest(point));
        }
    }

    @Test
    public void testNearest40k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * input40K.txt
         */
        String filename = "data/input40K.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        Point2D point;
        // testing nearest() with (0.81, 0.3)
        point = new Point2D(0.81, 0.3);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        // testing nearest() with (0.206107, 0.095492)
        point = new Point2D(0.206107, 0.095492);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        for (int i = 0; i < 10000; i++) {
            point = new Point2D(StdRandom.random(), StdRandom.random());
            Assert.assertEquals(set.nearest(point), kdtree.nearest(point));
        }
    }

    @Test
    public void testNearestCircle100() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * circle100.txt
         */
        String filename = "data/circle100.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        Point2D point;
        // testing nearest() with (0.81, 0.3)
        point = new Point2D(0.81, 0.3);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        // testing nearest() with (0.206107, 0.095492)
        point = new Point2D(0.206107, 0.095492);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        for (int i = 0; i < 10000; i++) {
            point = new Point2D(StdRandom.random(), StdRandom.random());
            Assert.assertEquals(set.nearest(point), kdtree.nearest(point));
        }
    }

    @Test
    public void testNearestCircle1k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * circle1000.txt
         */
        String filename = "data/circle1000.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        Point2D point;
        // testing nearest() with (0.81, 0.3)
        point = new Point2D(0.81, 0.3);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        // testing nearest() with (0.206107, 0.095492)
        point = new Point2D(0.206107, 0.095492);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        for (int i = 0; i < 10000; i++) {
            point = new Point2D(StdRandom.random(), StdRandom.random());
            Assert.assertEquals(set.nearest(point), kdtree.nearest(point));
        }
    }

    @Test
    public void testNearestCircle10k() {
        PointSET set = new PointSET();
        KdTree kdtree = new KdTree();

        /*
         * circle10000.txt
         */
        String filename = "data/circle10000.txt";
        In in = new In(filename);

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            set.insert(p);
        }

        // testing size()
        Assert.assertEquals(set.size(), kdtree.size());

        Point2D point;
        // testing nearest() with (0.81, 0.3)
        point = new Point2D(0.81, 0.3);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        // testing nearest() with (0.206107, 0.095492)
        point = new Point2D(0.206107, 0.095492);
        Assert.assertEquals(set.nearest(point), kdtree.nearest(point));

        for (int i = 0; i < 10000; i++) {
            point = new Point2D(StdRandom.random(), StdRandom.random());
            Assert.assertEquals(set.nearest(point), kdtree.nearest(point));
        }
    }

    public static List<Point2D> getAll(Iterable<Point2D> points) {
        List<Point2D> result = new ArrayList<Point2D>();
        for (Point2D point : points) {
            result.add(point);
        }
        return result;
    }

    public static Point2D buildRandomPoint() {
        return new Point2D(StdRandom.random(), StdRandom.random());
    }

    public static boolean compareIterable(Iterable<Point2D> points1,
            Iterable<Point2D> points2) {
        int N1 = count(points1);
        int N2 = count(points2);
        if (N1 != N2)
            return false;

        Point2D[] arr1 = new Point2D[N1];
        Point2D[] arr2 = new Point2D[N2];
        fromIterableToArray(points1, arr1);
        fromIterableToArray(points2, arr2);
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        for (int i = 0; i < N1; i++) {
            if (0 != arr1[i].compareTo(arr2[i]))
                return false;
        }
        return true;
    }

    public static <Type> void fromIterableToArray(Iterable<Type> i, Type[] a) {
        int k = 0;
        for (Type e : i) {
            a[k++] = e;
        }
    }

    public static <Type> int count(Iterable<Type> i) {
        int N = 0;
        Iterator<Type> iter = i.iterator();
        while (iter.hasNext()) {
            N++;
            iter.next();
        }
        return N;
    }
}