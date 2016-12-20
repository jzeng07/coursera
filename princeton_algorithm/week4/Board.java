import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;


public class Board {
    private int[][] blocks;
    private char N;
    private char manhattans = 0;
    private char hammings = 0;
    private char zero_idx;


    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = (char)blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zero_idx = (char)(i * N + j);
                }

                // calculate hamming
                int key = blocks[i][j];
                if (key != 0 && key != (i * N + j + 1)) {
                    hammings++;
                    int ii = (key - 1) / N;
                    int jj = (key - 1) % N;
                    manhattans += Math.abs(i - ii) + Math.abs(j - jj);
                }
            }
        }
    }

    // board dimension n
    public int dimension() {
        return (int)N;
    }

    // number of blocks out of place
    public int hamming() {
        return (int)hammings;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return (int)manhattans;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattans == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int i, j, ii, jj;
        while (true) {
            i = StdRandom.uniform(N);
            j = StdRandom.uniform(N);
            if (blocks[i][j] != 0) break;
        }

        while (true) {
            ii = StdRandom.uniform(N);
            jj = StdRandom.uniform(N);
            if ((i != ii || j != jj) && blocks[ii][jj] != 0) break;
        }

        swap(blocks, i, j, ii, jj);
        Board twinBoard = new Board(blocks);
        swap(blocks, i, j, ii, jj);
        return twinBoard;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board)y;
        if (that.dimension() != N) return false;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (this.blocks[i][j] != that.blocks[i][j]) return false;

        return true;
    }

    private void swap(int[][] blocks, int i, int j, int ii, int jj) {
        int tmp = blocks[i][j];
        blocks[i][j] = blocks[ii][jj];
        blocks[ii][jj] = tmp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborBoards = new ArrayList<Board>();
        int zero_i = zero_idx / N;
        int zero_j = zero_idx % N;

        if (zero_i > 0) {
            swap(blocks, zero_i, zero_j, zero_i-1, zero_j);
            Board upper = new Board(blocks);
            neighborBoards.add(upper);
            swap(blocks, zero_i, zero_j, zero_i-1, zero_j);
        }
        if (zero_i < N - 1) {
            swap(blocks, zero_i, zero_j, zero_i+1, zero_j);
            Board lower = new Board(blocks);
            neighborBoards.add(lower);
            swap(blocks, zero_i, zero_j, zero_i+1, zero_j);
        }
        if (zero_j > 0) {
            swap(blocks, zero_i, zero_j, zero_i, zero_j-1);
            Board left = new Board(blocks);
            neighborBoards.add(left);
            swap(blocks, zero_i, zero_j, zero_i, zero_j-1);
        }
        if (zero_j < N - 1) {
            swap(blocks, zero_i, zero_j, zero_i, zero_j+1);
            Board right = new Board(blocks);
            neighborBoards.add(right);
            swap(blocks, zero_i, zero_j, zero_i, zero_j+1);
        }
        return neighborBoards;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%d", (int)N) + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();    
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
    
        StdOut.println("Initial board: \n" + initial);
        StdOut.println("Dimension = " + initial.dimension());
        StdOut.println("Hamming = " + initial.hamming());
        StdOut.println("Manhattan = " + initial.manhattan());
        StdOut.println("Is goal = " + initial.isGoal());
        Board twin_board = initial.twin();
        StdOut.println("Twin board: \n" + twin_board);

        Board clone = new Board(blocks);
        StdOut.println("This equals to clone? " + initial.equals(clone));
        StdOut.println("This equals to twin? " + initial.equals(twin_board));
        Iterable<Board> neighbors = initial.neighbors();
        StdOut.println("All neighbors");
        for (Board neibor : neighbors) StdOut.println(neibor);
    }
}
