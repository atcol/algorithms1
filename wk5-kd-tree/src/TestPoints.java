import java.util.Arrays;

public class TestPoints {
    private static final int MIN_POINTS = 10;
    private static final int MAX_POINTS = 1000;

    public static void main(String[] args) {

        PointSET points = new PointSET();
        RectHV rect;
        double[] x = new double[2];
        double[] y = new double[2];
        Point2D randPoint;
        Point2D nearest;

        int numPoints = StdRandom.uniform(MIN_POINTS, MAX_POINTS);

        StdOut.println("Creating " + numPoints + " random points...");

        while (numPoints > 0) {
            points.insert(new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom
                    .uniform(0.0, 1.0)));
            numPoints--;
        }

        points.draw();

        x[0] = StdRandom.uniform(0.0, 1.0);
        x[1] = StdRandom.uniform(0.0, 1.0);
        y[0] = StdRandom.uniform(0.0, 1.0);
        y[1] = StdRandom.uniform(0.0, 1.0);

        Arrays.sort(x);
        Arrays.sort(y);

        rect = new RectHV(x[0], y[0], x[1], y[1]);

        StdOut.println("Creating rectangle " + rect.toString() + "...");
        StdOut.println("Finding points within the rectangle: ");
        for (Point2D p : points.range(rect))
            StdOut.println(p.toString());

        rect.draw();

        randPoint = new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(
                0.0, 1.0));
        StdOut.println("Finding point nearest to " + randPoint.toString());
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.02);
        randPoint.draw();
        StdDraw.setPenRadius(.005);
        nearest = points.nearest(randPoint);
        randPoint.drawTo(nearest);

        StdOut.println(nearest.toString());

    }
}