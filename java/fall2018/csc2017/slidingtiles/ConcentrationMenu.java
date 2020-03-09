package fall2018.csc2017.slidingtiles;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
 * Game Menu Screen for Concentration Game
 */
public class ConcentrationMenu extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static String FLIP_SAVE_FILENAME = "flip_save_file.ser";
    /**
     * A temporary save file.
     */
    public static String FLIP_TEMP_SAVE_FILENAME = "flip_save_file_tmp.ser";

    /**
     * Play Button
     */
    Button playbtn;

    /**
     * How to play Button
     */
    Button howtoplaybtn;
    /**
     * Score Button
     */
    Button scorebtn;
    /**
     * Tell user has logged in
     */
    TextView userLoggedIn;
    /**
     * user's name
     */
    public static String user;
    /**
     * Help pop-up dialogs
     */
    Dialog myDialog;

    /**
     * Single Player Button
     */
    Button singlep;
    /**
     * Two Player Button
     */
    Button twops;

    /**
     * FlippingGame
     */
    private FlippingGame concentration;

    /**
     * Create Concentration Menu Screen
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.concentration_menu);
        playbtn = findViewById(R.id.playBtn);
        howtoplaybtn = findViewById(R.id.instructionsBtn);
        scorebtn = findViewById(R.id.scoreBtn);

        userLoggedIn = findViewById(R.id.userView);
        displayUser();

        myDialog = new Dialog(this);

    }

    /**
     * Display user's name
     */
    private void displayUser() {
        if (getIntent().hasExtra("Username")) {
            String username = getIntent().getExtras().getString("Username");
            user = username;
            userLoggedIn.setText(username);

        }
    }

    /**
     * Launch Concentration game
     *
     * @param view the view
     */
    public void launchInstructions(View view) {
        Intent tmp = new Intent(this, Instructions.class);
        tmp.putExtra("Mode", "conc");
        startActivity(tmp);

    }

    /**
     * Launch Score board
     *
     * @param view the view
     */
    public void launchScore(View view) {
        Intent tmp = new Intent(this, Scoreboard.class);
        tmp.putExtra("Type", "conc");
        startActivity(tmp);

    }

    /**
     * Show Pop-ups
     *
     * @param view the view
     */
    public void showPopUp(View view) {
        myDialog.setContentView(R.layout.concentration_popup_window);
        singlep = findViewById(R.id.single_player);
        twops = findViewById(R.id.two_players);
        myDialog.show();
    }

    /**
     * Link SinglePlayer Button to start Single player mode game
     *
     * @param view the view
     */
    public void singlePlayerListener(View view) {
        Intent tmp = new Intent(this, FlippingGame.class);
        tmp.putExtra("Mode", "single player");
        tmp.putExtra("Username", user);
        myDialog.dismiss();
        startActivity(tmp);
    }

    /**
     * Link TwoPlayers Button to start Two players mode game
     *
     * @param view the view
     */
    public void twoPlayersListener(View view) {
        Intent tmp = new Intent(this, FlippingGame.class);
        tmp.putExtra("Mode", "two player");
        myDialog.dismiss();
        startActivity(tmp);
    }

    /**
     * Activate the load button.
     */
    public void flippingLoadButtonListener(View view) {
        loadFromFile(user + FLIP_SAVE_FILENAME);
        saveToFile(user + FLIP_TEMP_SAVE_FILENAME);
        if (concentration != null) {
            switchToGame();
            makeToastLoadedText();
        } else {
            wrongLoad();
        }

    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, FlippingGame.class);
        tmp.putExtra("Username", user);
        saveToFile(user + FLIP_TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display a toast for no saved game
     */
    private void wrongLoad() {
        Toast.makeText(this, "There is no saved game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Load the FlippingGame from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                concentration = (FlippingGame) input.readObject();
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
     * Save the FlippingGameManager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(concentration);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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