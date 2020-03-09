package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class where the user can choose which game they want to play
 */
public class GameCentre extends AppCompatActivity {
    /**
     * Concentration game button
     */
    Button concentrationbtn;
    /**
     * slidingtiles game button
     */
    Button slidingtilesbtn;
    /**
     * squirtle game button
     */
    Button squirtlebtn;
    /**
     * Current User
     */
    TextView userLoggedIn;
    /**
     * User's name
     */
    private String user;

    /**
     * Handles Game Menu Screen
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamecentre);
        concentrationbtn = findViewById(R.id.concentrationBtn);
        slidingtilesbtn = findViewById(R.id.slidingTilesBtn);
        squirtlebtn = findViewById(R.id.squirtleBtn);
        userLoggedIn = findViewById(R.id.userView);
        displayUser();

    }

    /**
     * dispaly user
     */
    private void displayUser() {
        if (getIntent().hasExtra("Username")) {
            String username = getIntent().getExtras().getString("Username");
            user = username;
            userLoggedIn.setText(username);

        }
    }

    /**
     * Start Concentration Game Menu
     *
     * @param view this view
     */
    public void launchConcentration(View view) {
        Intent tmp = new Intent(this, ConcentrationMenu.class);
        Bundle data1 = new Bundle();
        data1.putString("Username", user);
        tmp.putExtras(data1);
        startActivity(tmp);

    }

    /**
     * Start SlidingTiles Game Menu
     *
     * @param view this view
     */
    public void launchSlidingTiles(View view) {
        Intent tmp = new Intent(this, StartingActivity.class);
        Bundle data1 = new Bundle();
        data1.putString("Username", user);
        tmp.putExtras(data1);
        startActivity(tmp);

    }

    /**
     * Start Squirtle Game Menu
     *
     * @param view this view
     */
    public void launchSquirtle(View view) {
        Intent tmp = new Intent(this, SquirtleMenu.class);
        tmp.putExtra("Username", user);
        startActivity(tmp);

    }

    /**
     * Take to Scoreboard Screen
     *
     * @param view this view
     */
    public void launchRecords(View view) {
        Intent tmp = new Intent(this, Result.class);
        tmp.putExtra("Username", user);
        startActivity(tmp);

    }

    /**
     * Go back
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
    }
}
