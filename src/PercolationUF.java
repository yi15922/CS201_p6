import java.util.Arrays;

/**
 * Implements the IPercolate interface, uses union find.
 */
public class PercolationUF implements IPercolate {

    private IUnionFind myFinder;
    private boolean[][] myGrid;
    private final int VTOP;
    private final int VBOTTOM;
    private int myOpenCount = 0;
    private int mySize;

    /**
     * Initializes a PercolationUF object with a finder and a size
     *
     * @param finder specified finder
     * @param size creates a size x size grid
     */
    public PercolationUF(IUnionFind finder, int size){

        myGrid = new boolean[size][size];

        for (boolean[] row : myGrid)
            Arrays.fill(row, false);

        VTOP = size*size;
        VBOTTOM = size*size + 1;
        finder.initialize(size*size + 2);
        myFinder = finder;
        mySize = size;
    }


    /**
     * Opens the site and connects it to nearby open cells
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     */
    @Override
    public void open(int row, int col) {
        if (!inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        if (myGrid[row][col] == true) {
            return;
        }

        myGrid[row][col] = true;
        myOpenCount += 1;
        if (row == 0) {
            //System.out.printf("Connecting %d with top ", getIndex(row, col));
            myFinder.union(getIndex(row, col), VTOP);
        }
        if (row == mySize - 1) {
            myFinder.union(getIndex(row, col), VBOTTOM);
        }

        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, 1, -1};
        for (int i = 0; i < dRow.length; i++) {
            int nRow = row + dRow[i];
            int nCol = col + dCol[i];
            if (!inBounds(nRow, nCol)) {
                continue;
            }
            //System.out.println(isOpen(nRow, nCol));
            if (isOpen(nRow, nCol)) {
                //System.out.println("Connecting nearby");
                myFinder.union(getIndex(nRow, nCol), getIndex(row, col));
            }
        }
    }


    /**
     * Checks if the specified site is open
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     * @return boolean of whether the site is open
     */
    @Override
    public boolean isOpen(int row, int col) {
        if (!inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        //System.out.println(myGrid[row][col]);
        return myGrid[row][col];
    }


    /**
     * Checks if the specified site is full by checking if it
     * is connected to VTOP.
     *
     * @param row
     *            row index in range [0,N-1]
     * @param col
     * @return boolean of whether the site is full
     */
    @Override
    public boolean isFull(int row, int col) {
        if (!inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myFinder.connected(getIndex(row, col), VTOP);
    }

    private int getIndex (int row, int col){
        return row * myGrid.length + col;
    }

    /**
     * Checks if the system percolates by checking if both
     * VTOP and VBOTTOM are in the same set.
     * @return boolean of whether the system percolates.
     */
    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    /**
     * Gets the number of open sites.
     * @return number of open sites
     */
    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }


    /**
     * Checks if the specified coordinates is in bounds
     * @param row
     * @param col
     * @return booelan of whether the coordinates are in bounds
     */
    protected boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }
}
