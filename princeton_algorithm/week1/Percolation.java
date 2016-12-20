
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] sites;
    private boolean[] connectBottom;
    private int fullRoot;
    private boolean isPercolate;
    private int rowLen;
    private WeightedQuickUnionUF wqu;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        rowLen = n;
        sites = new int[n*n];
        connectBottom = new boolean[n*n];
        fullRoot = -1;
        isPercolate = false;
        wqu = new WeightedQuickUnionUF(n*n);
        for (int i = 0; i < n*n; i++) {
            sites[i] = -1;
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if ((i < 1) || (i > rowLen)) throw new IndexOutOfBoundsException();
        if ((j < 1) || (j > rowLen)) throw new IndexOutOfBoundsException();

        if (isOpen(i, j)) return;
        int idx = (i-1) * rowLen + (j-1);
        sites[idx] = idx;

        if (i == 1) {
            if (fullRoot == -1) fullRoot = idx;
            else wqu.union(fullRoot, idx);
        }

        if (i == rowLen) connectBottom[idx] = true;

        int root1, root2;
        root1 = wqu.find(idx);

        try {
            if (isOpen(i, j-1)) {
                root2 = wqu.find((i-1)*rowLen + j-2);
                connectBottom[root1] = connectBottom[root1] || connectBottom[root2];
                connectBottom[root2] = connectBottom[root1];
                wqu.union(idx, (i-1)*rowLen + j-2);
                root1 = root2;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (isOpen(i-1, j)) {
                root2 = wqu.find((i-2)*rowLen + j-1);
                connectBottom[root1] = connectBottom[root1] || connectBottom[root2];
                connectBottom[root2] = connectBottom[root1];
                wqu.union(idx, (i-2)*rowLen + j-1);
                root1 = root2;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (isOpen(i, j+1)) {
                root2 = wqu.find((i-1)*rowLen + j);
                connectBottom[root1] = connectBottom[root1] || connectBottom[root2];
                connectBottom[root2] = connectBottom[root1];
                wqu.union(idx, (i-1)*rowLen + j);
                root1 = root2;
            }
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            if (isOpen(i+1, j)) {
                root2 = wqu.find(i*rowLen + j-1);
                connectBottom[root1] = connectBottom[root1] || connectBottom[root2];
                connectBottom[root2] = connectBottom[root1];
                wqu.union(idx, i*rowLen + j-1);
                root1 = root2;
            }
        } catch (IndexOutOfBoundsException e) {
        }

        if (connectBottom[root1] && isFull(i, j)) isPercolate = true;
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if ((i < 1) || (i > rowLen)) throw new IndexOutOfBoundsException();
        if ((j < 1) || (j > rowLen)) throw new IndexOutOfBoundsException();
        int idx = (i-1) * rowLen + (j-1);
        return sites[idx] > -1;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if ((i < 1) || (i > rowLen)) throw new IndexOutOfBoundsException();
        if ((j < 1) || (j > rowLen)) throw new IndexOutOfBoundsException();

        if (fullRoot == -1) return false;
        if (!isOpen(i, j)) return false;

        int idx = (i-1) * rowLen + (j-1);
        return wqu.connected(fullRoot, idx);
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolate;
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int i, j;
        
        Percolation percolation = new Percolation(n);
        
        while (!percolation.percolates()) {
            i = StdRandom.uniform(1, n+1);
            j = StdRandom.uniform(1, n+1);
            percolation.open(i, j);
        }
    }
}
