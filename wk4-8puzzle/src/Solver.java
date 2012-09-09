public class Solver {
	private final MinPQ<Board> pq;
	public Solver(final Board initial) {
		pq = new MinPQ<Board>();
		pq.insert(initial);
	} // find a solution to the initial board (using the A* algorithm)

	public boolean isSolvable() {
		return false;
	} // is the initial board solvable?

	public int moves() {
		return 0;
	} // min number of moves to solve initial board; -1 if no solution

	public Iterable<Board> solution() {
		return null;
	} // sequence of boards in a shortest solution; null if no solution

	public static void main(final String[] args) {
	} // solve a slider puzzle (given below)
}