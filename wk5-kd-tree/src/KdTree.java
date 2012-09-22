import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KdTree {
    
    private static class PointIterator implements Iterator<Point2D> {
        private final Iterator<Point2D> it;

        public PointIterator(final Node root, final RectHV r) {
            // all points in the set that are inside the rectangle
            final List<Point2D> points = new ArrayList<Point2D>();
            Node n = root;
            this.it = points.iterator();
        }
        
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Point2D next() {
            return it.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class Node {
        private final Point2D p;
        private final RectHV rect;
        private Node lb;
        private Node rt;

        public Node(final Point2D p, final RectHV r) {
            this.p = p;
            this.rect = r;
        }
    }

    private Node root;
    private int N;
    
    public KdTree() {
        // construct an empty set of points
        N = 0;
    }

    public boolean isEmpty() {
        // is the set empty?
        return root == null;
    }

    public int size() {
        // number of points in the set
        return N;
    }

    public void insert(final Point2D p) {
        // add the point p to the set (if it is not already in the set) {
        N++;
        if (root == null) {
            root = new Node(p, new RectHV(root.p.x(), 0, root.p.x(), N));
            return; // for maintenance safety
        } else if (!contains(p)) {
            // do somin'
            // Select which tree to start with
            Node n = root;
            Node last = root;
            if (p.x() < n.p.x()) {
                n = root.lb;
                if (n == null) {
                    last.lb =
                        new Node(p, new RectHV(last.lb.p.x(), 0, last.lb.p.x(), N));
                    return;
                }
            } else {
                n = root.rt;
                if (n == null) {
                    last.rt = 
                        new Node(p, new RectHV(0, last.rt.p.y(), N, last.rt.p.y()));
                    return;
                }
            }
            
            int i = 2;
            boolean mode = false;
            while (n != null) {
                mode = i % 2 == 0;
                last = n;
                final boolean isLess = less(mode, p, n.p);
                if (isLess) {
                    n = n.lb;
                } else {
                    // greater or same
                    n = n.rt;
                }
                i++;
            }
            
            if (mode) {
                // go right
                last.rt = new Node(p, new RectHV(0, p.y(), N, p.y()));
            } else {
                // go left
                last.lb = new Node(p, new RectHV(p.x(), 0, p.x(), N));
            }
        }
    }

    public boolean contains(final Point2D p) {
        // does the set contain the point p?
        if (isEmpty()) return false;
        if (p.equals(root.p)) return true;
        return find(1, root, p) != null;
    }
    
    private Node find(final int i, final Node n, final Point2D p) {
        if (n == null) return null;
        if (p.equals(n.p)) return n;
        
        Node node = null;
        final boolean mode = i % 2 == 0;
        final boolean isLess = less(mode, p, n.p);
        if (isLess) {
            node = find(i + 1, n.lb, p);
        } else {
            node = find(i + 1, n.rt, p);
        }
        return node;
    }
    
    public void draw() {
        // draw all of the points to standard draw
        int i = 1;
        Node n = root;
        while (n != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(n.p.x(), n.p.y());
            
            if (i % 2 == 0) StdDraw.setPenColor(StdDraw.BLUE); // horiz
            else StdDraw.setPenColor(StdDraw.RED);
            
            StdDraw.line(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
            n = n.lb;
            i++;
        }
        
        i = 1;
        n = root.rt;
        while (n != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(n.p.x(), n.p.y());
            
            if (i % 2 == 0) StdDraw.setPenColor(StdDraw.BLUE); // horiz
            else StdDraw.setPenColor(StdDraw.RED);
            
            StdDraw.line(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());
            n = n.rt;
            i++;
        }
    }

    public Iterable<Point2D> range(final RectHV rect) {
        // all points in the set that are inside the rectangle
        return new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return new PointIterator(root, rect);
            }
        };
    }

    public Point2D nearest(final Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        return null;
    }
    
    private boolean less(final boolean checkY, final Point2D p, final Point2D q) {
        if (checkY) return p.y() <= q.y();
        else return p.x() <= q.x();
    }

    public int compare(final int i, Point2D l, Point2D r) {
        if (i % 2 != 0) {
            if (l.x() < r.x()) return 1;
            else if (l.x() > r.x()) return -1;
        } else {
            if (l.y() < r.y()) return 1;
            else if (l.y() > r.y()) return -1;
        }
        return 0;
    }
}