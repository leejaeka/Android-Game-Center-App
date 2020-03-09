package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;


    /**
     * Grid View
     */
    private GestureDetectGridView gridView;
    /**
     * calculated column height and width based on device size
     */
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    private CountDownTimer gameCountDownTimer;

    /**
     * Current Score
     */
    static TextView currentScore;
    /**
     * Database
     */
    static DatabaseHelper myDb;

    /**
     * display buttons
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Handles Game Screen
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(StartingActivity.user + StartingActivity.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);
        addUndoButtonListener();
        addUndoSwitchesListener();

        currentScore = findViewById(R.id.tileScore);
        myDb = new DatabaseHelper(this);
        currentScore.setText(myDb.getSlidingtilesCurrentScore(StartingActivity.user));

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getComplexity());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    /**
                     * Handle device screen size
                     */
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getComplexity();
                        columnHeight = displayHeight / boardManager.getComplexity() - 80;
                        display();
                    }
                });
        auto_save();
    }

    /**
     * Go back
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getComplexity(); row++) {
            for (int col = 0; col != boardManager.getComplexity(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Activate the 3x3 grid button
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.Undo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager.undo();
            }

        });
    }

    /**
     * Activate the Undo Switch
     */
    private void addUndoSwitchesListener() {
        Button switchSwitches = findViewById(R.id.Switches);
        switchSwitches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager.changeUndoStatus();
            }

        });
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getComplexity();
            int col = nextPos % boardManager.getComplexity();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Update the current score
     */
    static void updateScore() {
        if (StartingActivity.user != null) {
            int old_score = Integer.valueOf(myDb.getSlidingtilesCurrentScore(StartingActivity.user));
            myDb.setSlidingtilesCurrentScore(StartingActivity.user, String.valueOf(String.valueOf(old_score + 1)));
            currentScore.setText(myDb.getSlidingtilesCurrentScore(StartingActivity.user));
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                boardManager = new BoardManager(boardManager.getBoard(), boardManager.getMoves(), boardManager.getMoves_q());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Save the game on 1-second intervals; no text would be shown.
     */
    private void auto_save() {
        gameCountDownTimer = new CountDownTimer(1000, 1000) {

            public void onFinish() {
                saveToFile(StartingActivity.user + StartingActivity.SAVE_FILENAME);
                saveToFile(StartingActivity.user + StartingActivity.TEMP_SAVE_FILENAME);
                gameCountDownTimer.start();
            }

            public void onTick(long millisUntilFinished) {
            }
        };

        gameCountDownTimer.start();
    }

    /**
     * Override onDestroy to also cancel auto_save timer
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameCountDownTimer.cancel();
    }

    /**
     * update/ display game
     *
     * @param o   this observable
     * @param arg this object
     */
    @Override
    public void update(Observable o, Object arg) {
        display();
    }

}
