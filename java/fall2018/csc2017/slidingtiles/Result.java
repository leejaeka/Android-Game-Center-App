package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Used to display the user's personal score. That is, once the user solves the puzzle their
 * score is updated accordingly.
 */
public class Result extends AppCompatActivity {
    /**
     * database
     */
    DatabaseHelper myDb;
    /**
     * current user
     */
    TextView userLoggedIn;
    /**
     * Best sliding tiles game score
     */
    TextView slidingScore;
    /**
     * Best concentration game score
     */
    TextView concScore;
    /**
     * Best squirtle game score
     */
    TextView squirtleScore;
    /**
     * user's name
     */
    private String user;

    /**
     * Handle's Scoreboard(Result) Screen
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        myDb = new DatabaseHelper(this);

        userLoggedIn = findViewById(R.id.userView);
        slidingScore = findViewById(R.id.sliding_score);
        concScore = findViewById(R.id.conc_score);
        squirtleScore = findViewById(R.id.squirtle_score);

        displayUser();
        displaySlidingScore();
        displayConcScore();
        displaySquirtleScore();
    }

    /**
     * Display Concentration game's best score
     */
    private void displayConcScore() {
        String result = myDb.getConcentrationScore(user);
        if (result.equals("-1")) {
            concScore.setText("Score has not been set. Try playing the game.");
        } else {
            concScore.setText(result + " seconds");
        }
    }

    /**
     * Display Sliding tiles game's best score
     */
    private void displaySlidingScore() {
        String result = myDb.getScore(user);
        if (result.equals("-1")) {
            slidingScore.setText("Score has not been set. Try playing the game.");
        } else {
            slidingScore.setText(result + " moves");
        }

    }

    /**
     * Display Squirtle game's best score
     */
    private void displaySquirtleScore() {
        String result = myDb.getSquirtleScore(user);
        if (result.equals("-1")) {
            squirtleScore.setText("Score has not been set. Try playing the game.");
        } else {
            squirtleScore.setText(result + " points");

        }

    }

    /**
     * Display user's name
     */
    private void displayUser() {
        String username = getIntent().getStringExtra("Username");
        user = username;
        userLoggedIn.setText(username);
    }

    /**
     * Go back
     */
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}
