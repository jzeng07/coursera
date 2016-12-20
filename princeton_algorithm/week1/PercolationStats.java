import edu.princeton.cs.algs4.StdRandom;


public class PercolationStats {
    private int trials;
    private double[] fractions;
    private double miu;
    private double theta;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) throw new IllegalArgumentException();
        trials = t;
        fractions = new double[trials];
        Percolation perc;

        int i, j;

        for (int k = 0; k < trials; k++) {
            perc = new Percolation(n);
            int openCount = 0;
            while (!perc.percolates()) {
                i = StdRandom.uniform(1, n+1);
                j = StdRandom.uniform(1, n+1);
                if (!perc.isOpen(i, j)) {
                    perc.open(i, j);
                    openCount++;
                }
            }
            fractions[k] = openCount * 1.0 / (n * n);
        }
        miu = mean();
        theta = stddev();
    }

    // sample mean of percolation threshold
    public double mean() {
        if (miu > 0.0) return miu;
        double fractionSum = 0.0;
        for (int i = 0; i < trials; i++)
            fractionSum += fractions[i];
        return fractionSum / trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (theta > 0.0) return theta;

        double devSum = 0.0;
        for (int i = 0; i < trials; i++)
            devSum += Math.pow(fractions[i] - miu, 2);
        return Math.sqrt(devSum / (trials - 1));
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return (miu - (1.96 * theta / Math.sqrt(trials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (miu + (1.96 * theta / Math.sqrt(trials)));
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        int i, j;
        PercolationStats percStats = new PercolationStats(n, trials);

        System.out.println("95% confidence interval\t= " +
                           percStats.confidenceLo() +
                           ", " + percStats.confidenceHi());
        System.out.println("stddev\t\t\t= " + percStats.stddev());
        System.out.println("mean\t\t\t= " + percStats.mean());
    }
}

