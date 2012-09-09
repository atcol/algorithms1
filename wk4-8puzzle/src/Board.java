public class Board {
	private final int[][] b;
	private final int N;
	private final int highestNumber;
	private final int manhattan;
	
	public Board(final int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		// (where blocks[i][j] = block in row i, column j)
		this.b = new int[blocks.length][blocks.length];
		this.N = b.length;
		this.highestNumber = b.length * b.length;
		for (int i = 0; i < blocks.length; i++) {
			System.arraycopy(blocks[i], 0, this.b[i], 0, blocks[i].length);
		}
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
				if (c == highestNumber) {
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
				if (c >= highestNumber) break;
				if (block != c) return false;
				c++;
			}
		}
		return true;
	} // is this board the goal board?

	public Board twin() {
		final int r1 = rand();
		return null;
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
		return null;
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
	
	private int rand() {
		int rX = StdRandom.uniform(N);
		int rY = StdRandom.uniform(N);
		while (b[rX][rY] == 0) {
			rX = StdRandom.uniform(N);
			rY = StdRandom.uniform(N);
		}
		return b[rX][rY];
	}

	private int toY(final int block, int bX) {
		return Math.abs(block - (bX * N));
	}

	private int toX(final int block) {
		return Math.round(block / N);
	}

}