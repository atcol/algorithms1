import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KdTree {
    private static final double PEN_RADIUS = 0.016d;

    private static class RangeIterator implements Iterator<Point2D> {
        private final Iterator<Point2D> it;

        public RangeIterator(final Node root, final RectHV r) {
            // all points in the set that are inside the rectangle
            final List<Point2D> points = new ArrayList<Point2D>();
            range(root, r, points);
            this.it = points.iterator();
        }
        
        private void range(final Node n, final RectHV r,
                final List<Point2D> points) {
//            if (r.contains(n.p) && n.rect.intersects(r)) points.add(n.p);
            if (r.contains(n.p)) points.add(n.p);
            if (n.lb != null) range(n.lb, r, points);
            if (n.rt != null) range(n.rt, r, points);
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
        private final boolean levelOdd;
        private final Point2D p;
        private final RectHV rect;
        private Node lb;
        private Node rt;

        public Node(final Point2D p, final boolean levelOdd, final RectHV newRect) {
            this.p = p;
            this.levelOdd = levelOdd;
            this.rect = newRect;
        }

        @Override
        public String toString() {
            return "Node [levelOdd=" + levelOdd 
                    + ", p=" + p + ", rect=" + rect
                    + ", lb=" + lb + ", rt=" + rt + "]";
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
            root = new Node(p, true, new RectHV(p.x(), 0, p.x(), 1));
            return; // for maintenance safety
        } else if (!contains(p)) {
            // Starting at n = root, 
            // if n.levelOdd, compare X
            // else compare Y
            // if p < n.p, go n.lb
            // else if >=, go n.rt

            final Node parent = parent(root, p);
            final RectHV r = parent.rect;
            final boolean lvlEven = !parent.levelOdd;
            RectHV nr = null;
            
            if (less(parent.levelOdd, p, parent.p)) {
                if (!lvlEven) nr = new RectHV(0, p.y(), r.xmin(), p.y()); // LH
                else nr = new RectHV(p.x(), r.xmin(), p.x(), r.xmin()); // LV
                parent.lb = new Node(p, lvlEven, nr);
            } else {
                if (!lvlEven) nr = new RectHV(r.xmin(), p.y(), 1, p.y()); // RH
                else nr = new RectHV(p.x(), r.ymax(), p.x(), 1); // RV
                parent.rt = new Node(p, lvlEven, nr);
            }
        }
    }

    public boolean contains(final Point2D p) {
        // does the set contain the point p?
        if (isEmpty()) return false;
        if (p.equals(root.p)) return true;
        return find(root, p) != null;
    }

    private Node find(final Node n, final Point2D p) {
        if (n == null) return null;
        if (p.equals(n.p)) return n;
        
        Node node = null;
        final boolean isLess = less(n.levelOdd, p, n.p);
        if (isLess) {
            node = find(n.lb, p);
        } else {
            node = find(n.rt, p);
        }
        return node;
    }
 
    public void draw() {
        draw(root);
    }

    private void draw(final Node n) {
        if (n == null) return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(PEN_RADIUS);
        StdDraw.point(n.p.x(), n.p.y());
        StdDraw.setPenRadius();

        if (!n.levelOdd) StdDraw.setPenColor(StdDraw.BLUE); // horiz
        else StdDraw.setPenColor(StdDraw.RED);
        final RectHV r = n.rect;
        StdDraw.line(r.xmin(), r.ymin(), r.xmax(), r.ymax());
        draw(n.lb);
        draw(n.rt);
    }

    public Iterable<Point2D> range(final RectHV rect) {
        // all points in the set that are inside the rectangle
        final Iterable<Point2D> iterable = new Iterable<Point2D>() {
            @Override
            public Iterator<Point2D> iterator() {
                return new RangeIterator(root, rect);
            }
        };
        return iterable;
    }

    public Point2D nearest(final Point2D p) {
        // a nearest neighbour in the set to p; null if set is empty
        if (isEmpty()) return null;
        return nearest(root, root.p, p);
    }

    private boolean less(final boolean levelOdd, final Point2D p, final Point2D q) {
        if (!levelOdd) return p.y() < q.y();
        else return p.x() <= q.x();
    }
 
    private Node parent(final Node n, final Point2D p) {
        if (n == null) return n;
        if (less(n.levelOdd, p, n.p)) {
            if (n.lb == null) return n;
            else return parent(n.lb, p);
        } else {
            if (n.rt == null) return n;
            else return parent(n.rt, p);
        }
    }
 
    private Point2D nearest(final Node n, final Point2D c, final Point2D p) {
        Point2D nrl = c, nrr = c;
        if (n.lb != null) if (greater(c, n.lb, p)) nrl = nearest(n.lb, n.lb.p, p);
        if (n.rt != null) if (greater(c, n.rt, p)) nrr = nearest(n.rt, n.rt.p, p);
        if (nrl.distanceTo(p) > nrr.distanceTo(p)) return nrr;
        else return nrl;
    }

    private boolean greater(final Point2D c, final Node n, final Point2D p) {
        return c.distanceTo(p) > n.p.distanceTo(p);
    }
}