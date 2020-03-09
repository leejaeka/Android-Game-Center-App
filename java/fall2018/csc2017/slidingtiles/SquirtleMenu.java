package fall2018.csc2017.slidingtiles;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SquirtleMenu extends AppCompatActivity {
    /**
     * Play Squirtle game button
     */
    Button playbtn;
    /**
     * How to play Button
     */
    Button howtoplaybtn;
    /**
     * Scoreboard Button
     */
    Button scorebtn;
    /**
     * Current user
     */
    TextView userLoggedIn;
    /**
     * user's name
     */
    private String user;

    /**
     * Start Easy mode Button
     */
    Button easybtn;
    /**
     * Start Medium mode Button
     */
    Button mediumbtn;
    /**
     * Start Hard mode Button
     */
    Button hardbtn;

    /**
     * dialog
     */
    Dialog myDialog;

    /**
     * Handles Squirtle game menu Screen
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.squirtle_menu);
        playbtn = findViewById(R.id.playBtn);
        howtoplaybtn = findViewById(R.id.instructionsBtn);
        scorebtn = findViewById(R.id.scoreBtn);

        userLoggedIn = findViewById(R.id.userView);
        displayUser();

        myDialog = new Dialog(this);


    }

    /**
     * display current user
     */
    private void displayUser() {
        String username = getIntent().getStringExtra("Username");
        user = username;
        userLoggedIn.setText(username);
    }

    /**
     * Show easy, medium, hard mode buttons
     *
     * @param view this view
     */
    public void launchPlay(View view) {
        showPopUp();


    }

    /**
     * Take to How to play Screen
     *
     * @param view this view
     */
    public void launchInstructions(View view) {
        Intent tmp = new Intent(this, Instructions.class);
        tmp.putExtra("Mode", "squirtle");
        startActivity(tmp);

    }

    /**
     * Take to Scoreboard Screen
     *
     * @param view this view
     */
    public void launchScore(View view) {
        Intent tmp = new Intent(this, Scoreboard.class);
        tmp.putExtra("Type", "squirtle");
        startActivity(tmp);

    }

    /**
     * RESOURCE : Learned code from: https://www.youtube.com/watch?v=0DH2tZjJtm0&t=1s
     * show pop-up window for mode buttons
     */
    public void showPopUp() {
        myDialog.setContentView(R.layout.squirtle_popup_window);
        easybtn = findViewById(R.id.easybtn);
        mediumbtn = findViewById(R.id.mediumbtn);
        hardbtn = findViewById(R.id.hardbtn);
        myDialog.show();
    }

    /**
     * Activate Squirtle easy mode button
     *
     * @param view this view
     */
    public void easyButtonListener(View view) {
        Intent tmp = new Intent(this, NewGame.class);
        tmp.putExtra("level", "easy");
        tmp.putExtra("Username", user);
        myDialog.dismiss();
        startActivity(tmp);
    }

    /**
     * Activate Squirtle medium mode button
     *
     * @param view this view
     */
    public void mediumButtonListener(View view) {
        Intent tmp = new Intent(this, NewGame.class);
        tmp.putExtra("level", "medium");
        tmp.putExtra("Username", user);
        myDialog.dismiss();
        startActivity(tmp);
    }

    /**
     * Activate Squirtle hard mode button
     *
     * @param view this view
     */
    public void hardButtonListener(View view) {
        Intent tmp = new Intent(this, NewGame.class);
        tmp.putExtra("level", "hard");
        tmp.putExtra("Username", user);
        myDialog.dismiss();
        startActivity(tmp);
    }


}
