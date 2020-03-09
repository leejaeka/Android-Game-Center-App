package fall2018.csc2017.slidingtiles;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Handle's user's inputs (taps) for Sliding tile game
 */
class MovementController {

    /**
     * Board manager
     */
    private BoardManager boardManager = null;
    /**
     * database
     */
    DatabaseHelper myDb;

    /**
     * Initializer for MovementController
     */
    MovementController() {
    }

    /**
     * Setter for Board manager
     *
     * @param boardManager this boardmanger
     */
    void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process every taps
     *
     * @param context  this context
     * @param position target position
     * @param display  this display
     */
    void processTapMovement(Context context, int position, boolean display) {
        myDb = new DatabaseHelper(context);
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                String oldScore = myDb.getScore(StartingActivity.user);
                if (oldScore.equals("-1")) {
                    myDb.setScore(StartingActivity.user, Integer.toString(Board.score));
                    String message = "Congratulations, you solved the puzzle! You set a personal record of " +
                            Integer.toString(Board.score) + " moves. Check the PERSONAL RECORDS to see your updated score!";
                    showMessage("You Win!", message, context);
                } else if (Board.score > Integer.valueOf(oldScore)) {
                    String message = "Congratulations, you solved the puzzle! Unfortunately your score of " +
                            Integer.toString(Board.score) + " did not beat your record of " + myDb.getScore(StartingActivity.user) +
                            " moves. Better luck next time!";
                    showMessage("You Win!", message, context);
                } else {
                    myDb.setScore(StartingActivity.user, Integer.toString(Board.score));
                    String message = "Congratulations, you solved the puzzle! You set a new personal record of " +
                            Integer.toString(Board.score) + " moves, beating your previous record of " + oldScore +
                            " moves. Check the PERSONAL RECORDS to see your updated score!";
                    showMessage("You Win!", message, context);
                }
            }
        } else {
            Toast.makeText(context, "Invalid tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * show message
     *
     * @param title   title
     * @param message message
     * @param context this context
     */
    private void showMessage(String title, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
