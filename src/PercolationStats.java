import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double MAGIC = 1.96;
    private final double[] data;
    private double mean = -1;
    private final int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials should greater than 0");
        }

        this.trials = trials;
        this.data = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int rnd = StdRandom.uniform(n * n);
                int row = rnd / n + 1;
                int col = rnd % n + 1;
                percolation.open(row, col);
            }

            this.data[i] = (double) percolation.numberOfOpenSites() / n / n;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (this.mean < 0) {
            this.mean = StdStats.mean(this.data);
        }
        return this.mean;

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.data);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - MAGIC / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + MAGIC / Math.sqrt(trials);
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(200, 100);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
