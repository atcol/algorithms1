
public class PercolationStats {
    private final double[] thresholds;
    private final int n;
    private final int t;
    
    public PercolationStats(int N, int T) {
        if (N < 0 || T < 1) {
            throw new IllegalArgumentException("N or T < 0");
        }
        // perform T independent computational experiments on an N-by-N grid
        thresholds = new double[T];
        n = N;
        t = T;
        runMonteCarloSimulation();
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    private void runMonteCarloSimulation() {
        final int N = n;
        final int T = t;
        final double totalSites = N*N;
        
        for (int i = 0; i < T; i++) {
            double noOfOpenSites = 0;
            final Percolation percolation = new Percolation(N);
            
            while (!percolation.percolates()) {
                final int randI = randInt(N);
                final int randJ = randInt(N);
                
                if (!percolation.isOpen(randI, randJ)) {
                    percolation.open(randI, randJ);
                    noOfOpenSites++;
                }
            }
            
            // Percolation complete; now record threshold
            final double threshold = noOfOpenSites / totalSites;
            thresholds[i] = threshold;
        }
    }
    
    public static void main(final String[] args) {
        if (args.length < 2) {
            StdOut.println("You must pass two arguments");
        }

        final int N = Integer.valueOf(args[0]);
        final int T = Integer.valueOf(args[1]);
        
        final PercolationStats stats = new PercolationStats(N, T);
        
        final double mean = stats.mean();
        final double stddev = stats.stddev();
        StdOut.println("mean                    = " + mean);
        StdOut.println("stddev                  = " + stddev);
        StdOut.println("95% confidence interval = " 
                +
                (mean - (1.96*stddev/Math.sqrt(T)))
                + ", "
                +
                (mean + (1.96*stddev/Math.sqrt(T))));
    }


    private static int randInt(final int noOfBlockedSites) {
        return StdRandom.uniform(noOfBlockedSites) + 1;
    }
}
