package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {
    /**
     * The board being managed.
     */
    private Board board;

    /**
     * Status of undo switch (False = Default Undo mode)
     */
    private boolean UndoStatus = true;

    /**
     * Stores every valid moves (where blank Tile is being moved)
     * 0 = last move was swap top -> bottom
     * 1 = last move was swap right -> left
     * 2 = last move was swap bottom -> top
     * 3 = last move was swap left -> right
     */
    private Stack<Integer> moves = new Stack<>();

    /**
     * Stores up to three valid moves (where blank Tile is being moved)
     * 0 = last move was swap top -> bottom
     * 1 = last move was swap right -> left
     * 2 = last move was swap bottom -> top
     * 3 = last move was swap left -> right
     */
    private ArrayList<Integer> moves_q = new ArrayList<>();


    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
        this.board.setRows(getComplexity());
        this.board.setCols(getComplexity());
    }

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board, Stack<Integer> moves, ArrayList<Integer> moves_q) {
        this.board = board;
        this.moves = moves;
        this.moves_q = moves_q;
        this.board.setRows(getComplexity());
        this.board.setCols(getComplexity());
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Return the current moves.
     */
    Stack<Integer> getMoves() {
        return this.moves;
    }

    /**
     * Return the current moves in a queue of 3.
     */
    ArrayList<Integer> getMoves_q() {
        return this.moves_q;
    }

    /**
     * Initiate a new shuffled board.
     */
    BoardManager() {
        int numTiles = Board.num_rows * Board.num_cols;
        ArrayList<Integer> temp_tile = new ArrayList<>();
        for (Integer tileNum = 0; tileNum != numTiles; tileNum++) {
            temp_tile.add(tileNum);
        }
        do {
            Collections.shuffle(temp_tile);
        }
        while (!testSolvable(temp_tile));


        List<Tile> tiles = new ArrayList<>();
        for (Integer i : temp_tile) {
            tiles.add(new Tile(i));
        }

        this.board = new Board(tiles);

    }

    /**
     * Returns the number of Inversions of given list
     */
    int inversion(ArrayList<Integer> tiles) {
        int sum = 0;
        int pointer = 0;
        for (Integer i : tiles) {
            for (int j = pointer; j < tiles.size(); j++) {
                if (tiles.get(j) < i && tiles.get(j) != 0) {
                    sum++;
                }
            }
            pointer++;
        }
        return sum;
    }

    /**
     * Return True iff the board is solvable
     * <p>
     * * A. If the grid width is odd, then the number of inversions
     * * in a solvable situation is even.
     * *
     * * B. If the grid width is even, and the blank is on an even row
     * * counting from the bottom (second-last, fourth-last etc),
     * * then the number of inversions in a solvable situation is odd.
     * *
     * * C. If the grid width is even, and the blank is on an odd row
     * * counting from the bottom (last, third-last, fifth-last etc)
     * * then the number of inversions in a solvable situation is even.
     * *
     * *  Source :
     * *  https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/
     * *  TilesSolvability.html?fbclid=IwAR0cwrqBuyFeEoWBDFkxLbjKR
     * *  Ndw0lN8Ep9gtRpcc4cBXcmXd3738NJd8KQ
     */
    boolean testSolvable(ArrayList<Integer> tiles) {
        // blankTileLocation true iff second last row, fourth last row, ...
        boolean blankTileLocation = false;
        int inversion = inversion(tiles);
        int position = 0;
        for (Integer id : tiles) {
            position++;
            if (id == 0) {
                int row = position / Board.num_rows;
                blankTileLocation = ((Board.num_rows - 1 - row) % 2 == 1);
            }
        }
        if (Board.num_rows % 2 == 1
                && inversion % 2 == 0) {
            return true;
        } else if (Board.num_rows % 2 == 0
                && blankTileLocation
                && inversion % 2 == 1) {
            return true;
        } else if (Board.num_rows % 2 == 0
                && !blankTileLocation
                && inversion % 2 == 0) {
            return true;
        }
        return false;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        int expected = 1;
        for (Tile t : board) {
            if (t.getId() != expected) {
                if (t.getId() != 0) {
                    solved = false;
                }
            }

            expected++;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / this.board.getRows();
        int col = position % this.board.getRows();
        int blankId = 0;
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == this.board.getRows() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == this.board.getRows() - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / this.board.getRows();
        int col = position % this.board.getRows();
        int blankId = 0;

        if (board.getTile(row, col) != null && board.getTile(row, col).getId() != blankId) {
            board.score += 1;
            GameActivity.updateScore();
            Tile above = row == 0 ? null : board.getTile(row - 1, col);
            Tile below = row == this.board.getRows() - 1 ? null : board.getTile(row + 1, col);
            Tile left = col == 0 ? null : board.getTile(row, col - 1);
            Tile right = col == this.board.getRows() - 1 ? null : board.getTile(row, col + 1);
            if (above != null && above.getId() == blankId) {
                board.swapTiles(row, col, row - 1, col);
                moves.push(0);
                if (moves_q.size() < 3) {
                    moves_q.add(0);
                } else {
                    Integer temp_1 = moves_q.get(1);
                    Integer temp_2 = moves_q.get(2);
                    moves_q = new ArrayList<>(3);
                    moves_q.add(temp_1);
                    moves_q.add(temp_2);
                    moves_q.add(0);
                }

            } else if (below != null && below.getId() == blankId) {
                board.swapTiles(row, col, row + 1, col);
                moves.push(2);
                if (moves_q.size() < 3) {
                    moves_q.add(2);
                } else {
                    Integer temp_1 = moves_q.get(1);
                    Integer temp_2 = moves_q.get(2);
                    moves_q = new ArrayList<>(3);
                    moves_q.add(temp_1);
                    moves_q.add(temp_2);
                    moves_q.add(2);
                }
            } else if (left != null && left.getId() == blankId) {
                board.swapTiles(row, col, row, col - 1);
                moves.push(1);
                if (moves_q.size() < 3) {
                    moves_q.add(1);
                } else {
                    Integer temp_1 = moves_q.get(1);
                    Integer temp_2 = moves_q.get(2);
                    moves_q = new ArrayList<>(3);
                    moves_q.add(temp_1);
                    moves_q.add(temp_2);
                    moves_q.add(1);
                }
            } else if (right != null && right.getId() == blankId) {
                board.swapTiles(row, col, row, col + 1);
                moves.push(3);
                if (moves_q.size() < 3) {
                    moves_q.add(3);
                } else {
                    Integer temp_1 = moves_q.get(1);
                    Integer temp_2 = moves_q.get(2);
                    moves_q = new ArrayList<>(3);
                    moves_q.add(temp_1);
                    moves_q.add(temp_2);
                    moves_q.add(3);
                }
            }
        }
    }

    /**
     * Helper method for undo. Locates current blank tile's position
     *
     * @return int Position
     */
    int findBlankId() {
        int pos = 0;
        for (Tile i : this.getBoard()) {
            if (i.getId() == 0) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    /**
     * Return the status of the undo switch
     */
    boolean getUndoStatus() {
        return UndoStatus;
    }

    /**
     * change UndoStatus
     */
    void changeUndoStatus() {
        UndoStatus = !UndoStatus;
    }

    /**
     * Revert the board to one previous state
     */
    void undo() {
        if (UndoStatus) {
            if (!moves.isEmpty()) {
                int position = findBlankId();
                Integer temp_Move = moves.pop();
                if (!moves_q.isEmpty()) {
                    moves_q.remove(moves_q.size() - 1);
                }
                int row = position / this.board.getRows();
                int col = position % this.board.getRows();
                switch (temp_Move) {
                    case 0:
                        board.swapTiles(row, col, row - 1, col);
                        break;
                    case 1:
                        board.swapTiles(row, col, row, col - 1);
                        break;
                    case 2:
                        board.swapTiles(row, col, row + 1, col);
                        break;
                    case 3:
                        board.swapTiles(row, col, row, col + 1);
                        break;
                }
            }
        } else {
            if (!moves_q.isEmpty()) {
                int position = findBlankId();
                Integer temp_Move = null;
                if (moves_q.size() == 3) {
                    temp_Move = moves_q.get(2);
                    moves_q.remove(2);
                } else if (moves_q.size() == 2) {
                    temp_Move = moves_q.get(1);
                    moves_q.remove(1);
                } else if (moves_q.size() == 1) {
                    temp_Move = moves_q.get(0);
                    moves_q.remove(0);
                }
                int row = position / this.board.getRows();
                int col = position % this.board.getRows();

                if (temp_Move != null) {
                    moves.pop();
                    switch (temp_Move) {
                        case 0:
                            board.swapTiles(row, col, row - 1, col);
                            break;
                        case 1:
                            board.swapTiles(row, col, row, col - 1);
                            break;
                        case 2:
                            board.swapTiles(row, col, row + 1, col);
                            break;
                        case 3:
                            board.swapTiles(row, col, row, col + 1);
                            break;
                    }
                }
            }
        }
    }

    /**
     * return the complexity of the game
     */
    int getComplexity() {
        return this.board.getRows();
    }

}
