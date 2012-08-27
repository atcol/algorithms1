
public class Percolation {
    private final int N;
    /** Our 2d grid, false meaning closed **/
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int virtBotSiteIdx;
    private final int virtTopSiteIdx;
    private boolean hasOpen = false;

    public Percolation(final int N) {
        this.N = N;
        this.grid = new boolean[N][N];
        this.uf = new WeightedQuickUnionUF((N * N) + 2);
        // create N-by-N grid, with all sites blocked
        // boolean[] meaning all values assigned to false i.e. blocked
        
        virtTopSiteIdx = (N * N); // actually N^2 + 1 but -1 for array idx
        virtBotSiteIdx = (N * N) + 1; // N^2 + 2 but -1 for array idx
        // link all in top and bottom rows to their respective virtual sites
        for (int j = 1; j <= N; j++) {
            uf.union(toIndex(1, j), virtTopSiteIdx);
        }
        
        for (int j = 1; j <= N; j++) {
            uf.union(virtBotSiteIdx, toIndex(N, j));
        }
    }

    public void open(final int i, final int j) {
        throwIfOutOfBounds(i, j);
        // open site (row i, column j) if it is not already
        if (!isOpen(i, j)) {
            hasOpen = true;
            grid[i-1][j-1] = true;

            /* p = site @ (i,j) in UF array */
            final int p = toIndex(i, j);
            
            int newI = i - 1; // previous ROW
            int newJ = j;
            /* q is our neighbour site in the UF array */
            int q = toIndex(newI, newJ);
            if (inBoundsAndOpen(newI, newJ)) {
                uf.union(p, q);
            }
            
            newI = i + 1; // next ROW
            newJ = j;
            if (inBoundsAndOpen(newI, newJ)) {
                q = toIndex(newI, newJ);
                uf.union(p, q);
            }
            
            newI = i;
            newJ = j - 1; // previous COLUMN
            if (inBoundsAndOpen(newI, newJ)) {
                q = toIndex(newI, newJ);
                uf.union(p, q);
            }
            
            newI = i;
            newJ = j + 1; // next COLUMN
            if (inBoundsAndOpen(newI, newJ)) {
                q = toIndex(newI, newJ);
                uf.union(p, q);
            }
        }
    }

    public boolean isOpen(final int i, final int j) {
        throwIfOutOfBounds(i, j);
        // is site (row i, column j) open?
        return grid[i-1][j-1];
    }

    public boolean isFull(final int i, final int j) {
        throwIfOutOfBounds(i, j);
        if (!isOpen(i, j)) {
            // i, j can't be connected to the top if it's not open
            return false; 
        }
        // is site (row i, column j) full?
        // i.e. is site (i, j) connected to the top row?
        // is i, j connected to top site?
        return uf.connected(toIndex(i, j), virtTopSiteIdx);
    }

    public boolean percolates() {
        // is site on bottom row connected to site on top?
        return hasOpen && uf.connected(virtTopSiteIdx, virtBotSiteIdx);
    }
    
    ///////////////////////////
    // Utility methods below //
    ///////////////////////////
    
    private void throwIfOutOfBounds(final int i, final int j) {
        if (!isInBounds(i) || !isInBounds(j)) {
            throw new IndexOutOfBoundsException(
                    String.format(
                        "%d or %d out of bounds 1..N (%d)",
                        i, j, N));
        }
    }
    
    private boolean isInBounds(final int i) {
        return (i >= 1 && i <= N);
    }
    
    private int toIndex(final int row, final int col) {
        return N * (row - 1) + (col - 1);
    }
    
    private boolean inBoundsAndOpen(int newI, int newJ) {
        return isInBounds(newI) && isInBounds(newJ) 
                && isOpen(newI, newJ);
    }

}