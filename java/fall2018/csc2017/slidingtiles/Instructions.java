package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Instruction Screen
 */
public class Instructions extends AppCompatActivity {

    /**
     * Back Button
     */
    Button backbtn;

    String type;

    /**
     * Handles Instruction Screen
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getStringExtra("Mode");
        if (type.equals("sliding")) {
            setContentView(R.layout.instructions);
        }
        else if(type.equals("conc")) {
            setContentView(R.layout.conc_inst);
        }
        else{
            setContentView(R.layout.squirtle_inst);
        }

        backbtn = findViewById(R.id.backbtn);

    }

    /**
     * Go back
     *
     * @param view this view
     */
    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();

    }


}
