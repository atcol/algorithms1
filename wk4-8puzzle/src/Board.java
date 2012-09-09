import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Board implements Comparable<Board> {

    private final int[][] b;
    private final int N;
    private final int hBlock;
    private final int manhattan;
    private final SortedSet<Board> neighbours = new TreeSet<Board>();
    
    public Board(final int[][] blocks) {
        this(blocks, true);
    }

    private Board(final int[][] blocks, final boolean copy) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        if (copy) this.b = new int[blocks.length][blocks.length];
        else this.b = blocks;
        this.N = b.length;
        this.hBlock = b.length * b.length;
        if (copy) copy(blocks, this.b);
        this.manhattan = calcManhattan();
    }
    
    private final class BoardIterable implements Iterable<Board> {
        private final Iterator<Board> setIter;
        public BoardIterable() {
            Board.this.neighbours.addAll(findNeighbours(Board.this));
            setIter = Board.this.neighbours.iterator();
        }
        @Override
        public Iterator<Board> iterator() {
            return setIter;
        }

        private Set<Board> findNeighbours(final Board t) {
            final Set<Board> nbs = new HashSet<Board>();
            final Block z = t.findZero();
            final int zeroBlock = t.b[z.x][z.y];
            if (z.x + 1 < N) {
                // move one row down
                // swap zero and block below
                final Board n = new Board(t.b);
                final int blockBelow = t.b[z.x+1][z.y];
                n.b[z.x][z.y] = blockBelow;
                n.b[z.x + 1][z.y] = zeroBlock;
                nbs.add(n);
            } 
            
            if (z.x - 1 >= 0) {
                // move one row up
                // swap zero and block above
                final int blockAbove = t.b[z.x-1][z.y];
                final Board n = new Board(t.b);
                n.b[z.x][z.y] = blockAbove;
                n.b[z.x-1][z.y] = zeroBlock;
                nbs.add(n);
            } 
            
            if (z.y + 1 < N) {
                // move one column right
                // swap zero and block to the right
                final int rightBlock = t.b[z.x][z.y+1];
                final Board n = new Board(t.b);
                n.b[z.x][z.y] = rightBlock;
                n.b[z.x][z.y+1] = zeroBlock;
                nbs.add(n);
            } 
            
            if (z.y - 1 >= 0) {
                // move one column left
                // swap zero and block to the left
                final int leftBlock = t.b[z.x][z.y-1];
                final Board n = new Board(t.b);
                n.b[z.x][z.y] = leftBlock;
                n.b[z.x][z.y-1] = zeroBlock;
                nbs.add(n);
            }
            return nbs;
        }
    }

    private static final class Block {
        private final int x;
        private final int y;
        public Block(final int theX, final int theY) {
            x = theX;
            y = theY;
        }
    }
    
    public int dimension() {
        return N;
    } // board dimension N

    public int hamming() {
        int res = 0;
        if (b[N-1][N-1] != 0) {
            res = 1;
        }
        int c = 1;
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (c == hBlock) {
                    // don't test last item as already done
                    break;
                }
                final int block = b[i][j];
                if (block != c && block != 0) {
                    res++;
                }
                c++;
            }
        }
        return res;
    } // number of blocks out of place
    
    public int manhattan() {
        return manhattan;
    }

    private int calcManhattan() {
        int total = 0;
        if (b.length > 0) {
            for (int i = 0; i < b.length; i++) {
                for (int j = 0; j < b.length; j++) {
                    final int block = b[i][j];
                    // coords for where it should be
                    if (block != 0) {
                        int bX = toX(block-1);
                        int bY = toY(block-1, bX);
                        final int dist = Math.abs(bX - i) + Math.abs(bY - j);
                        total += dist;
                    }
                }
            }
        }
        return total;
    } // sum of Manhattan distances between blocks and goal

    public boolean isGoal() {
        int c = 1;
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                final int block = b[i][j];
                if (c >= hBlock) break;
                if (block != c) return false;
                c++;
            }
        }
        return true;
    } // is this board the goal board?

    public Board twin() {
        int x = StdRandom.uniform(N);
        int y = StdRandom.uniform(N);
        while (b[x][y] == 0) {
            x = StdRandom.uniform(N);
            y = StdRandom.uniform(N);
        }
        final int block = b[x][y];
        final int[][] blocks = new int[b.length][b.length];
        copy(this.b, blocks);
        
        // now find adjacent
        if (y+1 < N) {
            // swap with block below
            final int swap = blocks[x][y+1];
            blocks[x][y+1] = block;
            blocks[x][y] = swap;
        } else if (y-1 >= 0) {
            // swap with block above
            final int swap = blocks[x][y-1];
            blocks[x][y-1] = block;
            blocks[x][y] = swap;
        }
        return new Board(blocks, false);
    } // a board obtained by exchanging two adjacent blocks in the same row

    public Iterable<Board> neighbors() {
        return new BoardIterable();
    } // all neighbouring boards

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", b[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public boolean equals(final Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        final Board that = (Board) y;
        if (this.b.length != that.b.length) return false;
        
        for (int i = 0; i < this.b.length; i++) {
            for (int j = 0; j < this.b.length; j++) {
                if (this.b[i][j] != that.b[i][j]) {
                    return false;
                }
            }
        }
        return true;
    } // does this board equal y?
    
    @Override
    public int compareTo(final Board that) {
        if (that == null) {
            throw new NullPointerException();
        }
        if (this == that) {
            return 0;
        }
        
        final int mh = manhattan();
        final int hm = hamming();
        if (mh < that.manhattan() || hm < that.hamming()) {
            return -1;
        } else if (mh > that.manhattan() || hm > that.hamming()) {
            return 1;
        }
        return 0;
    }

    private Block findZero() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < this.b.length; i++) {
            for (int j = 0; j < this.b.length; j++) {
                if (b[x][y] == 0) {
                    return new Block(x, y);
                }
                x++;
                y++;
            }
        }
        throw new IllegalStateException("Couldn't find zero-th element!");
    }

    private int toY(final int block, int bX) {
        return Math.abs(block - (bX * N));
    }

    private int toX(final int block) {
        return Math.round(block / N);
    }
    
    private void copy(final int[][] blocks, int[][] dest) {
        for (int i = 0; i < blocks.length; i++) {
            System.arraycopy(blocks[i], 0, dest[i], 0, blocks[i].length);
        }
    }
}