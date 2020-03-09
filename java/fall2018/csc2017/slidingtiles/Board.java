package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.util.Observable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {
    /**
     * number of rows
     */
    static int num_rows;

    /**
     * number of cols
     */
    static int num_cols;

    /**
     * Score counter
     */
    static int score = 0;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;
    /**
     * The number of rows (extensive purposes)
     */
    private int rows;
    /**
     * The number of cols (extensive purposes)
     */
    private int cols;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();
        setRows(num_rows);
        setCols(num_cols);
        this.tiles = new Tile[getRows()][getCols()];
        for (int row = 0; row != getRows(); row++) {
            for (int col = 0; col != getCols(); col++) {
                this.tiles[row][col] = iter.next();

            }
        }
    }

    /**
     * Return the number of columns or rows of the board
     */
    int getRows() {
        return this.rows;
    }

    /**
     * Return the number of columns or rows of the board
     */
    private int getCols() {
        return this.cols;
    }

    /**
     * Set the number of rows of the board
     */
    void setRows(int row) {
        this.rows = row;
    }

    /**
     * Set the number of columns of the board
     */
    void setCols(int col) {
        this.cols = col;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        if ((row >= 0 && row <= getRows() - 1) && (col >= 0 && row <= this.cols - 1)) {
            return tiles[row][col];
        }
        return null;
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile tmp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = tmp;
        setChanged();
        notifyObservers();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator(getRows());
    }

    /**
     * Iterate through the board.
     */
    private class BoardIterator implements Iterator<Tile> {

        /**
         * (row, col) is the next Tile to return.
         */
        private int row;

        /**
         * (row, col) is the next Tile to return.
         */
        private int col;

        /**
         * dimension if the board it iterates through
         */
        private int dimension;

        /**
         * An iterator for the current board.
         */
        BoardIterator(int dimension) {
            this.row = 0;
            this.col = 0;
            this.dimension = dimension;
        }

        /**
         * Checks if iterator reached the end
         *
         * @return true iff hasn't reached the end
         */
        @Override
        public boolean hasNext() {
            return this.row != this.dimension;
        }

        /**
         * Returns the next Tile in the iteration
         *
         * @return returnts next tile
         */
        @Override
        public Tile next() {
            if (!hasNext()) {
                return null;
            }

            Tile result = tiles[this.row][this.col];
            getReadyForNext();
            return result;
        }

        /**
         * Update col and row as appropriate.
         */
        private void getReadyForNext() {
            if (this.col == this.dimension - 1) {
                this.row++;
                this.col = 0;
            } else {
                this.col++;
            }
        }
    }
}
