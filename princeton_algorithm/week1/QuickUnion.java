

public class QuickUnion {
    private int size;
    private int[] graph;
    private int[] sz;

    QuickUnion(int size) {
        size = size;
        graph = new int[size * size];
        sz = new int[size * size];
        create_graph();
    }

    private void create_graph() {
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                graph[i*n + j] = i*n + j;
                sz[i*n + j] = 0;
        }
    }

    private int root(i) {
        while (i != graph[i]) {
            graph[i] = graph[graph[i]];
            i = graph[i];
        }
        return i;
    }

    private void union(i, j) {
        ri = root(i);
        rj = root(j);
        if sz[ri] > sz[rj] {
            graph[rj] = ri;
            sz[rj] += sz[ri];
        } else {
            graph[ri] = rj;
            sz[ri] += sz[rj];
        }
    }

    private boolean connected(i, j) {
        ri = root(i);
        rj = root(j);
        return ri == rj;
    }

    private boolean all_connected() {
        r = root(0);
        return sz[r] >= size
    }

    public static void main(String[] args) {
        QuickUnion qu = new QuickUnion()
        while (!all_connected()) {
            
        }
    }
}
