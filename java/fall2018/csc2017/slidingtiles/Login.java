package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The login activity for the sliding puzzle tile game.
 * This should be the first activity user see when they open the app
 * Users can choose to enter their username/password which will direct them to the StartingActivity
 * or
 * Choose register which will direct them to Registration
 */

public class Login extends AppCompatActivity {
    /**
     * username3
     */
    EditText username3;
    /**
     * password3
     */
    EditText password3;
    /**
     * database
     */
    DatabaseHelper helper = new DatabaseHelper(this);
    /**
     * login Button
     */
    Button loginbtn;
    /**
     * number of tries
     */
    TextView triesbtn;
    /**
     * counter
     */
    int counter = 5;
    /**
     * number of accounts
     */
    TextView numOfAccounts;

    /**
     * Handles Login Screen
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        username3 = findViewById(R.id.username);
        password3 = findViewById(R.id.password);
        loginbtn = findViewById(R.id.login);
        triesbtn = findViewById(R.id.numOfTries);
        String t = "No. of attempts remaining: 5";
        triesbtn.setText(t);
        numOfAccounts = findViewById(R.id.numAccounts);
        String a = "No. of accounts created: " + helper.numOfAccounts();
        numOfAccounts.setText(a);
    }

    /**
     * Checks if username and password is correct. If it is correct, it sends to the
     * StartingActivity screen, else a toast pops up telling wrong login and number of attempts
     * is reduced.
     */
    public void loggingIn(View view) {
        String username4 = username3.getText().toString();
        String password4 = password3.getText().toString();

        String real_password = helper.searchPass(username4);

        if (password4.equals(real_password)) {
            Intent tmp = new Intent(this, GameCentre.class);

            Bundle data1 = new Bundle();
            data1.putString("Username", username4);
            tmp.putExtras(data1);

            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            startActivity(tmp);
        } else {
            counter--;
            String t = "No. of attempts remaining: " + Integer.toString(counter);
            triesbtn.setText(t);
            wrongLogin();
            if (counter == 0) {
                loginbtn.setEnabled(false);
            }
        }


    }

    /**
     * Display a toast for incorrect login
     */
    private void wrongLogin() {
        Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
    }

    /**
     * Sends the user to the Registration activity.
     */
    public void register(View view) {
        Intent tmp = new Intent(this, Registration.class);
        startActivity(tmp);
    }
}