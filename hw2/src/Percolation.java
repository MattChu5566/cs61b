import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // after watching hint slides/video
    private WeightedQuickUnionUF openDjs;
    private WeightedQuickUnionUF djs;
    private WeightedQuickUnionUF fullDjs;
    private int size;
    private int openRoot;
    private int virtualTop;
    private int virtualBot;
    private int fullVirtualTop;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        size = N;
        openDjs = new WeightedQuickUnionUF(N * N + 1);
        openRoot = openDjs.find(N * N);

        djs = new WeightedQuickUnionUF(N * N + 2);
        virtualTop = djs.find(N * N);
        virtualBot = djs.find(N * N + 1);

        fullDjs = new WeightedQuickUnionUF(N * N + 1);
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


    // solution before watching hint
    // needs recursion and may be slow

    // private WeightedQuickUnionUF openDjs;
    // private WeightedQuickUnionUF fullDjs;
    // private int size;
    // private int openRoot;
    // private int fullRoot;

    // public Percolation(int N) {
    //     if (N <= 0) {
    //         throw new IllegalArgumentException();
    //     }
    //     openDjs = new WeightedQuickUnionUF(N * N + 1);
    //     fullDjs = new WeightedQuickUnionUF(N * N + 1);
    //     size = N;
    //     openRoot = openDjs.find(N * N);
    //     fullRoot = fullDjs.find(N * N);
    // }

    // private int rowCol2ID(int row, int col) {
    //     return row * size + col;
    // }

    // private void checkOutOfBound(int row, int col) {
    //     if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
    //         throw new IndexOutOfBoundsException();
    //     }
    // }

    // public void open(int row, int col) {
    //     checkOutOfBound(row, col);
    //     int targetID = rowCol2ID(row, col);
    //     openDjs.union(openRoot, targetID);
        
    //     boolean neighborIsFull = row == 0
    //         || isFull(row - 1, col)
    //         || col - 1 >= 0 && isFull(row, col - 1)
    //         || col + 1 <= size - 1 && isFull(row, col + 1);
    //     if (neighborIsFull) {
    //         fullDjs.union(fullRoot, targetID);
    //         fullOpenNeighbor(row, col);
    //     }
    // }

    // public void fullOpenNeighbor(int row, int col) {
    //     int [][] openNeighbor = {
    //         {row - 1, col},
    //         {row + 1, col},
    //         {row, col - 1},
    //         {row, col + 1}
    //     };
    //     for (int[] rc: openNeighbor) {
    //         int nRow = rc[0];
    //         int nCol = rc[1];
    //         if (nRow >= 0 && nRow <= size - 1 && nCol >= 0 && nCol <= size - 1 && !isFull(nRow, nCol) && isOpen(nRow, nCol)) {
    //             int targetID = rowCol2ID(nRow, nCol);
    //             fullDjs.union(fullRoot, targetID);
    //             fullOpenNeighbor(nRow, nCol);
    //         }
    //     }
    // }

    // public boolean isOpen(int row, int col) {
    //     checkOutOfBound(row, col);
    //     int targetID = rowCol2ID(row, col);
    //     return openDjs.connected(openRoot, targetID);
    // }

    // public boolean isFull(int row, int col) {
    //     checkOutOfBound(row, col);
    //     int targetID = rowCol2ID(row, col);
    //     return fullDjs.connected(fullRoot, targetID);
    // }

    // public int numberOfOpenSites() {
    //     return size * size + 1 - openDjs.count();
    // }

    // public boolean percolates() {
    //     for (int i = 0; i < size; i++) {
    //         if (isFull(size - 1, i)) return true;
    //     }
    //     return false;
    // }
}
