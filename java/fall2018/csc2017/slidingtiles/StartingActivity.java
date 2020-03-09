package fall2018.csc2017.slidingtiles;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    public static String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * User's name
     */
    public static String user;

    /**
     * Current user
     */
    TextView userLoggedIn;

    /**
     * Easy mode Button (3x3)
     */
    Button easybtn;
    /**
     * Medium mode Button (4x4)
     */
    Button mediumbtn;
    /**
     * Hard mode Button (5x5)
     */
    Button hardbtn;

    /**
     * Dialog
     */
    Dialog myDialog;

    /**
     * Database
     */
    DatabaseHelper myDb;

    /**
     * Handles Slidingtiles game menu screen
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_);

        userLoggedIn = findViewById(R.id.userView);
        displayUser();

        myDialog = new Dialog(this);

        myDb = new DatabaseHelper(this);

    }

    /**
     * display user's name
     */
    private void displayUser() {
        String username = getIntent().getStringExtra("Username");
        user = username;
        userLoggedIn.setText(username);
    }


    /**
     * Activate the 3x3 grid button
     */
    public void addGrid3ButtonListener(View view) {
        Board.num_rows = 3;
        Board.num_cols = 3;
        boardManager = new BoardManager();
        switchToGame();
        Board.score = 0;
        myDb.setSlidingtilesCurrentScore(user, String.valueOf(0));
        myDialog.dismiss();

    }

    /**
     * Activate the 4x4 grid button
     */
    public void addGrid4ButtonListener(View view) {
        Board.num_rows = 4;
        Board.num_cols = 4;
        boardManager = new BoardManager();
        switchToGame();
        Board.score = 0;
        myDb.setSlidingtilesCurrentScore(user, String.valueOf(0));
        myDialog.dismiss();
    }

    /**
     * Activate the 5x5 grid button
     */
    public void addGrid5ButtonListener(View view) {
        Board.num_rows = 5;
        Board.num_cols = 5;
        boardManager = new BoardManager();
        switchToGame();
        Board.score = 0;
        myDb.setSlidingtilesCurrentScore(user, String.valueOf(0));
        myDialog.dismiss();
    }

    /**
     * Show buttons
     *
     * @param view this view
     */
    public void showPopUp(View view) {
        myDialog.setContentView(R.layout.slidingtiles_popup);
        easybtn = findViewById(R.id.easybtn);
        mediumbtn = findViewById(R.id.mediumbtn);
        hardbtn = findViewById(R.id.hardbtn);
        myDialog.show();
    }

    /**
     * Activate the load button.
     */
    public void addLoadButtonListener(View view) {
        loadFromFile(user + SAVE_FILENAME);
        saveToFile(user + TEMP_SAVE_FILENAME);
        if (boardManager != null) {
            switchToGame();
            makeToastLoadedText();
        } else {
            wrongLoad();
        }

    }

    /**
     * Display a toast for no saved game
     */
    private void wrongLoad() {
        Toast.makeText(this, "There is no saved game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(user + TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("Username", user);
        saveToFile(user + TEMP_SAVE_FILENAME);
        startActivity(tmp);
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
     * Go to Leaderboard Screen
     *
     * @param view this view
     */
    public void launchLeaderboard(View view) {
        Intent tmp = new Intent(this, Scoreboard.class);
        tmp.putExtra("Type", "sliding");
        startActivity(tmp);
    }

    /**
     * Go to How to play Screen
     *
     * @param view this view
     */
    public void launchInstructions(View view) {
        Intent tmp = new Intent(this, Instructions.class);
        tmp.putExtra("Mode", "sliding");
        startActivity(tmp);

    }

    /**
     * Override onDestroy
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * Go back
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
