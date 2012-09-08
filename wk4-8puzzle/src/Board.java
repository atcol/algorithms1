public class Board {
    public Board(int[][] blocks) {}        // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
		return 0;}              // board dimension N
    public int hamming() {
		return 0;}                // number of blocks out of place
    public int manhattan() {
		return 0;}              // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
		return false;}             // is this board the goal board?
    public Board twin() {
		return null;}                 // a board obtained by exchanging two adjacent blocks in the same row
    public boolean equals(Object y) {
		return false;}     // does this board equal y?
    public Iterable<Board> neighbors() {
		return null;}  // all neighboring boards
    public String toString() {
		return null;}            // string representation of the board (in the output format specified below)
}