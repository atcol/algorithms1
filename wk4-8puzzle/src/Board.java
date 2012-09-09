import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Board {
	private final int[][] b;
	private final int N;
	private final int hBlock;
	private final int manhattan;
	private final Set<Board> neighbours = new HashSet<Board>();
	
	private Comparator<Board> COMPARATOR = new Comparator<Board>() {
		@Override
		public int compare(final Board b1, final Board b2) {
			final Board t = Board.this;
			return 0;
		}
	};
	
	public Board(final int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		// (where blocks[i][j] = block in row i, column j)
		this(blocks, true);
	}

	private Board(final int[][] blocks, final boolean copy) {
		if (copy) this.b = new int[blocks.length][blocks.length];
		else this.b = blocks;
		this.N = b.length;
		this.hBlock = b.length * b.length;
		if (copy) copy(blocks, this.b);
		this.manhattan = calcManhattan();
	}
	
	public int dimension() {
		return N;
	} // board dimension N

	public int hamming() {
		int res = b[N-1][N-1] == 0 ? 0 : 1;
		int c = 1;
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if (c == hBlock) {
					// don't test last item as already done
					break;
				}
				if (b[i][j] != c) {
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

	public Iterable<Board> neighbors() {
		return new Iterable<Board>() {
			@Override
			public Iterator<Board> iterator() {
				return null;
			}
		};
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