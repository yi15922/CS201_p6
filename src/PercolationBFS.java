import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Extends PercolationDFSFast but uses BFS instead of DFS as search method
 */
public class PercolationBFS extends PercolationDFSFast {
    /**
     * Initialize a grid so that all cells are blocked.
     *
     * @param n is the size of the simulated (square) grid
     */
    public PercolationBFS(int n) {
        super(n);
    }

    /**
     * Updates appropriate sites as full, using breadth first search.
     * @param row
     * @param col
     */
    @Override
    protected void dfs(int row, int col) {

        myGrid[row][col] = FULL;
        LinkedList<Integer> cellList = new LinkedList<>();
        cellList.add(row * myGrid.length + col);
        while (cellList.size() > 0) {
            //System.out.println("Searching... ");
            int current = cellList.pop();
            int cRow = current / myGrid.length;
            int cCol = current % myGrid.length;

            int[] dRow = {-1, 1, 0, 0};
            int[] dCol = {0, 0, 1, -1};
            for (int i = 0; i < dRow.length; i++) {
                int nRow = cRow + dRow[i];
                int nCol = cCol + dCol[i];

                if (!inBounds(nRow, nCol)) {
                    continue;
                }

                if (isOpen(nRow, nCol) && !isFull(nRow, nCol)) {
                    myGrid[nRow][nCol] = FULL;
                    cellList.add(nRow * myGrid.length + nCol);
                }
            }
        }
    }
}