public class KdTree {
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
    }

    private final int N;
    private final Node root;
    
    public KdTree() {
        // construct an empty set of points
    }

    public boolean isEmpty() {
        // is the set empty?
        return false;
    }

    public int size() {
        // number of points in the set
        return Integer.MIN_VALUE;
    }

    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set) {
    }

    public boolean contains(Point2D p) {
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