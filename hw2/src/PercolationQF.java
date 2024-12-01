
import edu.princeton.cs.algs4.QuickFindUF;

public class PercolationQF {
  private QuickFindUF openDjs;
    private QuickFindUF djs;
    private QuickFindUF fullDjs;
    private int size;
    private int openRoot;
    private int virtualTop;
    private int virtualBot;
    private int fullVirtualTop;

    public PercolationQF(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        size = N;
        openDjs = new QuickFindUF(N * N + 1);
        openRoot = openDjs.find(N * N);

        djs = new QuickFindUF(N * N + 2);
        virtualTop = djs.find(N * N);
        virtualBot = djs.find(N * N + 1);

        fullDjs = new QuickFindUF(N * N + 1);
        fullVirtualTop = fullDjs.find(N * N);
    }

    private int rowCol2ID(int row, int col) {
        return row * size + col;
    }

    private void checkOutOfBound(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void open(int row, int col) {
        checkOutOfBound(row, col);
        int targetID = rowCol2ID(row, col);
        openDjs.union(openRoot, targetID);

        if (row == 0) {
            djs.union(virtualTop, targetID);
            fullDjs.union(fullVirtualTop, targetID);
        }
        if (row == size - 1) {
            djs.union(virtualBot, targetID);
        }

        int [][] neighbor = {
            {row - 1, col},
            {row + 1, col},
            {row, col - 1},
            {row, col + 1}
        };
        for (int[] rc: neighbor) {
            int nRow = rc[0];
            int nCol = rc[1];
            if (nRow >= 0 && nRow <= size - 1 && nCol >= 0 && nCol <= size - 1 && isOpen(nRow, nCol)) {
                int nTargetID = rowCol2ID(nRow, nCol);
                djs.union(nTargetID, targetID);
                fullDjs.union(nTargetID, targetID);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        checkOutOfBound(row, col);
        int targetID = rowCol2ID(row, col);
        return openDjs.connected(openRoot, targetID);
    }

    public boolean isFull(int row, int col) {
        checkOutOfBound(row, col);
        int targetID = rowCol2ID(row, col);
        return fullDjs.connected(fullVirtualTop, targetID);
    }

    public int numberOfOpenSites() {
        return size * size + 1 - openDjs.count();
    }

    public boolean percolates() {
        return djs.connected(virtualTop, virtualBot);
    }
}
