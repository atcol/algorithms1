import java.util.Iterator;


public class Solver {
    
    private static final int[][] GOAL_ARRAY = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private static final Board GOAL = new Board(GOAL_ARRAY);
    private final int moves;
    private final ComparableBoard initial;
    private final MinPQ<ComparableBoard> open;
    private final MaxPQ<ComparableBoard> closed;
    
    private static final class ComparableBoard implements
            Comparable<ComparableBoard> {
        private final Board b;

        public ComparableBoard(final Board board) {
            this.b = board;
        }

        public Board getBoard() {
            return b;
        }

        @Override
        public int compareTo(final ComparableBoard that) {
            if (that == null) {
                throw new NullPointerException();
            }
            final Board thatBoard = that.getBoard();
            if (this.b == thatBoard) {
                return 0;
            }

            final int mh = this.b.manhattan();
            final int hm = this.b.hamming();
            final int thatMh = thatBoard.manhattan();
            final int thatHm = thatBoard.hamming();
            if (mh < thatMh || hm < thatHm) {
                return -1;
            } else if (mh > thatMh || hm > thatHm) {
                return 1;
            }
            return 0;
        }
        
        @Override
        public boolean equals(final Object o) {
            return this.b.equals(o);
        }
    }
    
    public Solver(final Board initial) {
        this.initial = new ComparableBoard(initial);
        open = new MinPQ<ComparableBoard>();
        open.insert(new ComparableBoard(initial));
        closed = new MaxPQ<ComparableBoard>();
        
        int m = run(open, closed);
        this.moves = m;
    } // find a solution to the initial board (using the A* algorithm)

    private int run(final MinPQ<ComparableBoard> o, final MaxPQ<ComparableBoard> c) {
        final int N = this.initial.getBoard().dimension();
        final double maxIter = Math.pow(N, 13);
        int m = 0;
        ComparableBoard b = o.delMin();
        while (!b.equals(GOAL)) {
            m++;
            final Iterable<Board> neighbours = b.getBoard().neighbors();
            for (final Board n : neighbours) {
                o.insert(new ComparableBoard(n));
            }
            c.insert(b);
            b = o.delMin();
            if (m > maxIter) {
                return -1; // hack: not solvable
            }
        }
        return m;
    }

    public boolean isSolvable() {
        if (this.initial.getBoard().isGoal()) {
            return true;
        }
        final int movesInitial = moves();
        
        final MinPQ<ComparableBoard> o = new MinPQ<ComparableBoard>();
        o.insert(new ComparableBoard(initial.getBoard().twin()));
        final int movesTwin = run(o, new MaxPQ<ComparableBoard>());
        return movesInitial >= 0 && movesTwin == -1;
    } // is the initial board solvable?

    public int moves() {
        return moves;
    } // min number of moves to solve initial board; -1 if no solution

    public Iterable<Board> solution() {
        if (moves() == -1) {
            return null;
        }
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new Iterator<Board>() {
                    private Iterator<ComparableBoard> i = closed.iterator();
                    @Override
                    public boolean hasNext() {
                        return i.hasNext();
                    }
                    @Override
                    public Board next() {
                        return i.next().getBoard();
                    }
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    } // sequence of boards in a shortest solution; null if no solution

    public static void main(final String[] args) {
        // create initial board from file
        final In in = new In(args[0]);
        final int N = in.readInt();
        final int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        final Board initial = new Board(blocks);

        // solve the puzzle
        final Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}