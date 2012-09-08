import java.util.Arrays;

public class Brute {
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final int N = in.readInt();
        final Point[] points = readPoints(in, N);
        Point[] segments = new Point[4];
        int x = 0;
        /**
         *  To check whether the 4 points p, q, r, and s are collinear, 
         *  check whether the slopes between p and q, between p and r, 
         *  and between p and s are all equal. 
         */
        for (int i = 0; i < N; i++) {
            final Point p = points[i];
            
            for (int j = i+1; j < N; j++) {
                final Point q = points[j];
                
                for (int k = j+1; k < N; k++) {
                    final Point r = points[k];
                    final double pqSlope = p.slopeTo(q);
                    final double prSlope = p.slopeTo(r);
                    
                    if (pqSlope == prSlope) {
                        
                        for (int l = k+1; l < N; l++) {
                            final Point s = points[l];
                            final double psSlope = p.slopeTo(s);
                            
                            if (pqSlope == psSlope) {
                                // the ugliest code I've ever written
                                segments[x] = p;
                                segments[++x] = q;
                                segments[++x] = r;
                                segments[++x] = s;
                                x = 0;
                                Arrays.sort(segments);
                                StdOut.printf("%s -> ", segments[x]);
                                StdOut.printf("%s -> ", segments[++x]);
                                StdOut.printf("%s -> ", segments[++x]);
                                StdOut.printf("%s\n", segments[++x]);
                                p.draw();
                                q.draw();
                                r.draw();
                                s.draw();
//                                p.drawTo(q);
                                p.drawTo(s);
                            }
                            x = 0;
                        }
                    }
                }
            }
            segments = new Point[4];
        }
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
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        return points;
    }
}
