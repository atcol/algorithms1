public class KdTree {
    private static class Node {
        private final Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(final Point2D p) {
            this.p = p;
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
            root = new Node(p);
            return; // for maintenance safety
        } else if (!contains(p)) {
            // do somin'
        }
    }

    public boolean contains(final Point2D p) {
        // does the set contain the point p?
        return false;
    }

    public void draw() {
        // draw all of the points to standard draw
    }

    public Iterable<Point2D> range(final RectHV rect) {
        // all points in the set that are inside the rectangle
        return null;
    }

    public Point2D nearest(final Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        return null;
    }
}