import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Fast {

    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final int N = in.readInt();
        final Point[] points = readPoints(in, N);

        final List<Point> pointList = Arrays.asList(points);
        System.out.println(pointList);
        Collections.sort(pointList, points[0].SLOPE_ORDER);
        System.out.println(pointList);

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

//        for (int i = 0; i < N; i++) {
//            final Point p = points[i];
//            
//            for (int j = 0; j < N; j++) {
//                final Point q = points[j];
//                if (p != q) {
//                    
//                }
//            }
//            
//        }
    }

    private static Point[] readPoints(final In in, final int N) {
        final Point[] points = new Point[N];
        int c = 0;
        while (c < N) {
            final int x = in.readInt();
            final int y = in.readInt();
            points[c] = new Point(x, y);
            c++;
        }
        return points;
    }
}

//            for (int j = i+1; j < N; j++) {
//                final Point q = points[j];
//                final double pqSlope = p.slopeTo(q);
//
//                for (int k = j+1; k < N; k++) {
//                    final Point r = points[k];
//                    final double prSlope = p.slopeTo(r);
//
//                    if (pqSlope == prSlope) {
//                    
//                        for (int l = k+1; l < N; l++) {
//                            final Point s = points[l];
//                            final double psSlope = p.slopeTo(s);
//                            
//                            if (prSlope == psSlope) {
//                                StdOut.printf("%s -> %s -> %s -> %s\n", p, q, r, s);
////                                p.drawTo(q);
//                                p.draw();
//                                q.draw();
//                                r.draw();
//                                s.draw();
//                                p.drawTo(s);
//                            }
//                        }
//                    }
//                }
//            }
                                
