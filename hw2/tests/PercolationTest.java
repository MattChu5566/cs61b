import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    // TODO: Using the given tests above as a template,
    //       write some more tests and delete the fail() line
    @Test
    public void testOpenIsOpenNumberOfOpen() {
        Percolation p = new Percolation(3);
        assertThat(p.isOpen(0, 0)).isFalse();
        assertThat(p.isOpen(0, 1)).isFalse();
        assertThat(p.numberOfOpenSites()).isEqualTo(0);
        p.open(0, 0);
        assertThat(p.isOpen(0, 0)).isTrue();
        assertThat(p.isOpen(0, 1)).isFalse();
        assertThat(p.numberOfOpenSites()).isEqualTo(1);
        // open called on the open site
        p.open(0, 0);
        assertThat(p.isOpen(0, 0)).isTrue();
        assertThat(p.isOpen(0, 1)).isFalse();
        assertThat(p.numberOfOpenSites()).isEqualTo(1);
    }

    @Test
    public void testIsFull() {
        Percolation p = new Percolation(6);

        // test opening site on the first row, it should be full
        assertThat(p.isFull(0, 1)).isFalse();
        p.open(0, 1);
        assertThat(p.isFull(0, 1)).isTrue();

        // test connecting to a full site, the new open site and sites connecting to it should become full
        // let new site be 1,1
        p.open(1, 0); // left of 1,1
        p.open(1, 2); // right of 1,1
        p.open(2, 1); // bot of 1,1
        p.open(2, 0); // another connecting site
        assertThat(p.isFull(1, 0)).isFalse();
        assertThat(p.isFull(1, 2)).isFalse();
        assertThat(p.isFull(2, 1)).isFalse();
        assertThat(p.isFull(2, 0)).isFalse();
        p.open(1, 1);
        assertThat(p.isFull(1, 1)).isTrue(); // open at the bot of a full site, new open is full
        assertThat(p.isFull(1, 0)).isTrue();
        assertThat(p.isFull(1, 2)).isTrue();
        assertThat(p.isFull(2, 1)).isTrue();
        assertThat(p.isFull(2, 0)).isTrue();
        // test top connection
        p.open(3, 1);
        p.open(4, 1);
        p.open(4, 2);
        p.open(3, 3);
        assertThat(p.isFull(3, 3)).isFalse();
        p.open(4, 3);
        assertThat(p.isFull(4, 3)).isTrue();// open on the right of a full site, new open is full
        assertThat(p.isFull(3, 3)).isTrue();

        // open on the top of a full site, new open is full
        assertThat(p.isFull(2, 3)).isFalse();
        p.open(2, 3);
        assertThat(p.isFull(2, 3)).isTrue();

        // open on the left of a full site, new open is full
        assertThat(p.isFull(3, 0)).isFalse();
        p.open(3, 0);
        assertThat(p.isFull(3, 0)).isTrue();

        // test opening first row, open sites connecting to it should become full
        p.open(1, 4);
        p.open(1, 5);
        assertThat(p.isFull(1, 4)).isFalse();
        assertThat(p.isFull(1, 5)).isFalse();
        p.open(0, 4);
        assertThat(p.isFull(1, 4)).isTrue();
        assertThat(p.isFull(1, 5)).isTrue();

        // test backwash
        p.open(5, 1);
        p.open(5, 4);
        assertThat(p.isFull(5, 4)).isFalse();
        p.open(5, 3);
        assertThat(p.isFull(5, 4)).isTrue();

        // test changing row breaking connection
        p.open(4, 0);
        p.open(3, 5);
        assertThat(p.isFull(4, 0)).isTrue();
        assertThat(p.isFull(3, 5)).isFalse();
    }

    @Test
    public void testPercolates() {
        Percolation p1 = new Percolation(1);
        assertThat(p1.percolates()).isFalse();
        p1.open(0, 0);
        assertThat(p1.percolates()).isTrue();

        Percolation p3 = new Percolation(3);
        p3.open(0, 0);
        p3.open(1, 1);
        p3.open(2, 2);
        p3.open(2, 1);
        assertThat(p3.percolates()).isFalse();
        p3.open(1, 0);
        assertThat(p3.percolates()).isTrue();
    }
}
