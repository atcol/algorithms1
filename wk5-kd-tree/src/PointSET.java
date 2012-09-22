import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private static final class PointIterator implements Iterator<Point2D> {
        private final Set<Point2D> nrPoints;
        private final Iterator<Point2D> iterator;
        
        public PointIterator(final Set<Point2D> points) {
            this.nrPoints = points;
            this.iterator = nrPoints.iterator();
        }
        
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Point2D next() {
            return iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private final Set<Point2D> points = new TreeSet<Point2D>();

    public PointSET() {
    }

    public boolean isEmpty() {
        // is the set empty?
        return points.isEmpty();
    }

    public int size() {
        // number of points in the set
        return points.size();
    }

    public void insert(final Point2D p) {
        // add the point p to the set (if it is not already in the set)
        if (!contains(p)) {
            points.add(p);
        }
    }

    public boolean contains(final Point2D p) {
        // does the set contain the point p?
        return points.contains(p);
    }

    public void draw() {
        // draw all of the points to standard draw
        for (final Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(final RectHV rect) {
        // all points in the set that are inside the rectangle
        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                final Set<Point2D> nrPoints = new TreeSet<Point2D>();
                for (final Point2D p : points) {
                    if (rect.contains(p)) {
                        nrPoints.add(p);
                    }
                }
                return new PointIterator(nrPoints);
            }
        };
    }

    public Point2D nearest(final Point2D t) {
        // a nearest neighbour in the set to p; null if set is empty
        if (isEmpty()) {
            return null;
        }
        Point2D n = null;
        for (final Point2D p : points) {
            if (n == null) {
                n = p;
                continue;
            }
            if (p.distanceTo(t) < n.distanceTo(t)) {
                n = p;
            }
        }
        return n;
    }
}