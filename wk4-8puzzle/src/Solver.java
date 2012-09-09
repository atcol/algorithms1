
public class Solver {
    
    private static int[][] GOAL_ARRAY = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private static final Board GOAL = new Board(GOAL_ARRAY);
    
    private final MinPQ<Board> o;
    private final MinPQ<Board> c;
    private final int moves;
    
    public Solver(final Board initial) {
        o = new MinPQ<Board>();
        o.insert(initial);
        c = new MinPQ<Board>();
        
        int m = 0;
        Board b = o.delMin();
        while (!b.equals(GOAL)) {
            m++;
            final Iterable<Board> neighbours = b.neighbors();
            for (final Board n : neighbours) {
                o.insert(n);
            }
        }
        this.moves = m;
    } // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return false;
    } // is the initial board solvable?

    public int moves() {
        return moves;
    } // min number of moves to solve initial board; -1 if no solution

    public Iterable<Board> solution() {
        return null;
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
    } // solve a slider puzzle (given below)
}