package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * The registration activity for the sliding puzzle tile game.
 * Gives the user an opportunity to create new accounts. They get to choose a username and password.
 * Permitted the username is not already taken.
 */

public class Registration extends AppCompatActivity {
    /**
     * Database
     */
    DatabaseHelper helper = new DatabaseHelper(this);
    /**
     * username2
     */
    EditText username2;
    /**
     * password2
     */
    EditText password2;
    /**
     * password_confirm
     */
    EditText password_confirm;


    /**
     * Handle's Registration Screen
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);
        username2 = findViewById(R.id.username);
        password2 = findViewById(R.id.password);
        password_confirm = findViewById(R.id.confirmPassword);

    }

    /**
     * Allows the user to create an account, permitted the username is not already taken.
     * Once an account is successfully created they are sent to the Login class to login.
     */
    public void createAccount(View view) {
        String username = username2.getText().toString();
        String password = password2.getText().toString();
        String password_confirm_str = password_confirm.getText().toString();

        if (!password.equals(password_confirm_str)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
        } else if (helper.checkIfNameExists(username)) {
            Toast.makeText(this, "This username is already taken! Please choose another username.", Toast.LENGTH_LONG).show();
        } else {
            Account a = new Account();
            a.setName(username);
            a.setPass(password);
            helper.insertAccount(a);
            Toast.makeText(this, "Account successfully created!", Toast.LENGTH_SHORT).show();
            Intent tmp = new Intent(this, Login.class);
            startActivity(tmp);

        }


    }

}
