import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF uf2;
    // blocked -> false, open -> true
    private boolean[] matrix;
    private int openedSites;

    private final int virtualTop;
    private final int virtualBottom;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should greater than 0");
        }

        this.n = n;
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.uf2 = new WeightedQuickUnionUF(n * n + 1);
        this.matrix = new boolean[n * n];
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        row--;
        col--;

        if (!this.matrix[row * this.n + col]) {
            this.matrix[row * this.n + col] = true;
            this.openedSites++;

            if (row == 0) {
                uf.union(row * this.n + col, virtualTop);
                uf2.union(row * this.n + col, virtualTop);
            }
            if (row == n - 1) {
                uf.union(row * this.n + col, virtualBottom);
            }
            if (row > 0 && this.matrix[(row - 1) * this.n + col]) {
                uf.union(row * this.n + col, (row - 1) * this.n + col);
                uf2.union(row * this.n + col, (row - 1) * this.n + col);
            }
            if (row < n - 1 && this.matrix[(row + 1) * this.n + col]) {
                uf.union(row * this.n + col, (row + 1) * this.n + col);
                uf2.union(row * this.n + col, (row + 1) * this.n + col);
            }
            if (col > 0 && this.matrix[row * this.n + col - 1]) {
                uf.union(row * this.n + col, row * this.n + col - 1);
                uf2.union(row * this.n + col, row * this.n + col - 1);
            }
            if (col < n - 1 && this.matrix[row * this.n + col + 1]) {
                uf.union(row * this.n + col, row * this.n + col + 1);
                uf2.union(row * this.n + col, row * this.n + col + 1);
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

            return this.uf2.connected(virtualTop, row * this.n + col);
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
        return this.uf.connected(virtualTop, virtualBottom);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("index is not between 1 and " + this.n);
        }
    }

    private void printMatrix() {
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i * n + j] ? 1 : 0);
            }
            System.out.println();
        }
        System.out.println();
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
