/**
 * extends PercolationDFS, but is a faster implementation
 */
public class PercolationDFSFast extends PercolationDFS{

    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationDFSFast(int n) {
        super(n);
    }

    /**
     * Faster implementation of updatOnOpen,
     * does not clear every space and call dfs on all top row,
     * but only on cells that should be full.
     * @param row
     * @param col
     */
    @Override
    protected void updateOnOpen(int row, int col) {
        if(row == 0) {
            dfs(row, col);
            return;
        }
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, 1, -1};
        for (int i = 0; i < dRow.length; i++) {
            int nRow = row + dRow[i];
            int nCol = col + dCol[i];
            if (! inBounds(nRow,nCol)) {
                continue;
            }

            if (myGrid[nRow][nCol] == FULL) {
                dfs(row, col);
                return;
            }
        }

    }

}
