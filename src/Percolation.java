import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    // blocked -> false, open -> true
    private boolean[] matrix;
    private int openedSites;

    private boolean virtualTopAdded;
    private boolean virtualBottomAdded;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should greater than 0");
        }

        this.n = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.matrix = new boolean[n * n];
    }
    private void addVirtualTop() {
        if (virtualTopAdded) {
            return;
        }
        for (int i = 0; i < n; i++) {
            // top row
            this.uf.union(n * n, i);
        }
        virtualTopAdded = true;
    }

    private void addVirtualBottom() {
        if (virtualBottomAdded) {
            return;
        }

        for (int i = 0; i < n; i++) {
            // bottom row
            this.uf.union(n * n + 1, (n - 1) * n + i);
        }
        virtualBottomAdded = true;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        row--;
        col--;

        if (!this.matrix[row * this.n + col]) {
            this.matrix[row * this.n + col] = true;
            if (row == 0) {
                addVirtualTop();
            }
            if (row == this.n - 1) {
                addVirtualBottom();
            }

            this.openedSites++;

            if (row > 0 && this.matrix[(row - 1) * this.n + col]) {
                uf.union(row * this.n + col, (row - 1) * this.n + col);
            }
            if (row < n - 1 && this.matrix[(row + 1) * this.n + col]) {
                uf.union(row * this.n + col, (row + 1) * this.n + col);
            }
            if (col > 0 && this.matrix[row * this.n + col - 1]) {
                uf.union(row * this.n + col, row * this.n + col - 1);
            }
            if (col < n - 1 && this.matrix[row * this.n + col + 1]) {
                uf.union(row * this.n + col, row * this.n + col + 1);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        row--;
        col--;

        return this.matrix[row * this.n + col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            row--;
            col--;

            return this.uf.connected(n * n, row * this.n + col);
        } else {
            return false;
        }
    }

    // number of open sites
    public int numberOfOpenSites() {
        return this.openedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.uf.connected(n * n, n * n + 1);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("index is not between 1 and " + this.n);
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 20;
        Percolation percolation = new Percolation(n);

        while (!percolation.percolates()) {
            int rnd = StdRandom.uniform(n * n);
            int row = rnd / n + 1;
            int col = rnd % n + 1;
            percolation.open(row, col);
        }

        System.out.println((double) percolation.openedSites / n / n);
    }
}
