import java.util.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
    private boolean _isSolvable;
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> twin_pq;
    private int _moves = 0;
    private SearchNode cur_sn;
    private SearchNode twin_cur_sn;
    private Stack<Board> _solution = new Stack<Board>();

    private class SearchNode implements Comparable<SearchNode> {
        private Board _board;
        private SearchNode _pre_sn;
        public int moves;
        public int priority;

        public SearchNode(Board board, SearchNode pre_sn, int moves) {
            _board = board;
            _pre_sn = pre_sn;
            this.moves = moves;
            priority = this.moves + board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }

        public Board board() {
            return _board;
        }

        public SearchNode presn() {
            return _pre_sn;
        }

        public String toString() {
            return _board.toString() + "\n" + "moves = " + moves + "\npriority = " + priority + "\n";
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        Board board = initial;
        Board twin_board = board.twin();

        pq = new MinPQ<SearchNode>();
        twin_pq = new MinPQ<SearchNode>();
        SearchNode sn = new SearchNode(board, null, 0);
        SearchNode twin_sn = new SearchNode(twin_board, null, 0);
        pq.insert(sn);
        twin_pq.insert(twin_sn);
        move();
        solution();
    }

    private boolean hasBoard(Board bd, SearchNode sn) {
        if (sn == null) return false;
        SearchNode preSN = sn.presn();
        while (preSN != null) {
            if (bd.equals(preSN.board())) return true;
            preSN = preSN.presn();
        }
        return false;
    }

    private void move() {
        cur_sn = pq.delMin();
        twin_cur_sn = twin_pq.delMin();
        Board board = cur_sn.board();
        Board twin_board = twin_cur_sn.board();
        int insert = 1;
        int delMin = 1;
        while (!(board.isGoal() || twin_board.isGoal())) {
            Iterable<Board> neighbors = board.neighbors();
            for (Board neighbor : neighbors) {
                if (!hasBoard(neighbor, cur_sn)) {
                    SearchNode sn_neighbor = new SearchNode(neighbor, cur_sn, cur_sn.moves+1);
                    pq.insert(sn_neighbor);
                    insert++;
                }
            }
            Iterable<Board> twin_neighbors = twin_board.neighbors();
            for (Board twin_neighbor : twin_neighbors) {
                if (!hasBoard(twin_neighbor, twin_cur_sn)) {
                    SearchNode twin_sn_neighbor = new SearchNode(twin_neighbor, twin_cur_sn, twin_cur_sn.moves+1);
                    twin_pq.insert(twin_sn_neighbor);
                }
            }
            if (pq.isEmpty()) return;
            cur_sn = pq.delMin();
            board = cur_sn.board();
            twin_cur_sn = twin_pq.delMin();
            twin_board = twin_cur_sn.board();
            delMin++;
        }

        if (board.isGoal()) _isSolvable = true;
        else _isSolvable = false;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return _isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!_isSolvable) return -1;
        return _moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!_isSolvable) return null;
        if (!_solution.isEmpty()) return _solution;

        Stack<Board> sol = new Stack<Board>();

        int moves = 0;
        while (cur_sn != null) {
            sol.push(cur_sn.board());
            cur_sn = cur_sn.presn();
            moves++;
        }
        while (!sol.isEmpty()) _solution.push(sol.pop());
        _moves = moves - 1;

        return _solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
