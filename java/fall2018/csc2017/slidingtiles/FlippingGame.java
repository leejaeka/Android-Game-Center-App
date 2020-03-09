package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * Code based on Tihomir RAdeff's Memory Game : https://www.youtube.com/watch?v=94CWNE9ruMA&t=843s
 *
 * 1-Second Code Delay at Line 605-612:
 * //https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
 *
 * Concentration/flipping game
 */

public class FlippingGame extends AppCompatActivity {

    /**
     * Concentration game
     */
    FlippingGame concentration;

    /**
     *Timer for game (score)
     */
    private CountDownTimer gameCountDownTimer;
    /**
     * Timer for peek
     */
    private CountDownTimer peekTimer;

    /**
     * Scores (time)
     */
    TextView tv_p1, tv_p2;

    /**
     * Image views
     */
    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34;

    /**
     * array for the images
     */
    Integer[] cardsArray = {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};

    /**
     * images for flipping game
     */
    int image101, image102, image103, image104, image105, image106,
            image201, image202, image203, image204, image205, image206;

    /**
     * First move and second move
     */
    int firstCard, secondCard;
    /**
     * first click, second click
     */
    int clickedFirst, clickedSecond;
    /**
     * card's number
     */
    int cardNum = 1;
    /**
     * number of turns
     */
    int turn = 1;
    /**
     * Scores for each player
     */
    int playerPoints = 0, cpuPoints = 0;

    /**
     * sound player
     */
    private fall2018.csc2017.slidingtiles.SoundPlayer soundPlayer;

    /**
     * game  modes
     */
    String mode;

    /**
     * chronomter
     */
    private Chronometer chronometer;
    /**
     * true iff game running
     */
    private boolean running;
    /**
     * current time
     */
    private long time;
    /**
     * user's name
     */
    private String user;
    /**
     * datebase
     */
    DatabaseHelper myDb;
    Button peekbtn;
    int counter = 2;

    /**
     * Handles Concentration Game Screen
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auto_save();
        super.onCreate(savedInstanceState);
        loadFromFile(ConcentrationMenu.user + ConcentrationMenu.FLIP_TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_flipping_game);
        soundPlayer = new fall2018.csc2017.slidingtiles.SoundPlayer(this);

        peekbtn = findViewById(R.id.peekbtn);

        mode = getIntent().getStringExtra("Mode");
        user = getIntent().getStringExtra("Username");
        myDb = new DatabaseHelper(this);

        // timer
        chronometer = findViewById(R.id.chronometer);

        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }

        mappingScoreImages();

        if (mode.equals("single player")) {
            tv_p2.setText("");
            tv_p1.setText("");
        }
        if (mode.equals("two player")) {
            peekbtn.setEnabled(false);
        }

        mappingCardImages();

        setTag();

        //import and load the card images
        frontOfCardsResources();

        //shuffle the cards
        Collections.shuffle(Arrays.asList(cardsArray));

        //changing the color of the second player
        tv_p2.setTextColor(Color.GRAY);

        clickableCardImages();

    }

    /**
     * The card views
     */
    int theCardiv_11, theCardiv_12, theCardiv_13, theCardiv_14,
    theCardiv_21, theCardiv_22, theCardiv_23, theCardiv_24,
    theCardiv_31, theCardiv_32, theCardiv_33, theCardiv_34;

    /**
     * Process taps
     */
    private void clickableCardImages() {
        iv_11.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_11, theCard);
                theCardiv_11 = theCard;
            }
        });

        iv_12.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_12, theCard);
                theCardiv_12 = theCard;
            }
        });

        iv_13.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_13, theCard);
                theCardiv_13 = theCard;
            }
        });

        iv_14.setOnClickListener(new View.OnClickListener() {
            /**
             *Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_14, theCard);
                theCardiv_14 = theCard;
            }
        });

        iv_21.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_21, theCard);
                theCardiv_21 = theCard;
            }
        });

        iv_22.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_22, theCard);
                theCardiv_22 = theCard;
            }
        });

        iv_23.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_23, theCard);
                theCardiv_23 = theCard;
            }
        });

        iv_24.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_24, theCard);
                theCardiv_24 = theCard;
            }
        });

        iv_31.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_31, theCard);
                theCardiv_31 = theCard;
            }
        });

        iv_32.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_32, theCard);
                theCardiv_32 = theCard;
            }
        });

        iv_33.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_33, theCard);
                theCardiv_33 = theCard;
            }
        });

        iv_34.setOnClickListener(new View.OnClickListener() {
            /**
             * Each taps
             * @param v on this view
             */
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                setImage(iv_34, theCard);
                theCardiv_34 = theCard;
            }
        });
    }

    /**
     * Map each scores for each players
     */
    private void mappingScoreImages() {
        tv_p1 = findViewById(R.id.tv_p1);
        tv_p2 = findViewById(R.id.tv_p2);
    }

    /**
     * Map card images
     */
    private void mappingCardImages() {
        iv_11 = findViewById(R.id.iv_11);
        iv_12 = findViewById(R.id.iv_12);
        iv_13 = findViewById(R.id.iv_13);
        iv_14 = findViewById(R.id.iv_14);
        iv_21 = findViewById(R.id.iv_21);
        iv_22 = findViewById(R.id.iv_22);
        iv_23 = findViewById(R.id.iv_23);
        iv_24 = findViewById(R.id.iv_24);
        iv_31 = findViewById(R.id.iv_31);
        iv_32 = findViewById(R.id.iv_32);
        iv_33 = findViewById(R.id.iv_33);
        iv_34 = findViewById(R.id.iv_34);
    }

    /**
     * Set tags
     */
    private void setTag() {
        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");
    }

    /**
     * Set images
     * @param iv this view
     * @param card this card
     */
    public void setImage(ImageView iv, int card) {
        //assign the correct image to the correct card
        if (cardsArray[card] == 101) {
            iv.setImageResource(image101);
        } else if (cardsArray[card] == 102) {
            iv.setImageResource(image102);
        } else if (cardsArray[card] == 103) {
            iv.setImageResource(image103);
        } else if (cardsArray[card] == 104) {
            iv.setImageResource(image104);
        } else if (cardsArray[card] == 105) {
            iv.setImageResource(image105);
        } else if (cardsArray[card] == 106) {
            iv.setImageResource(image106);
        } else if (cardsArray[card] == 201) {
            iv.setImageResource(image201);
        } else if (cardsArray[card] == 202) {
            iv.setImageResource(image202);
        } else if (cardsArray[card] == 203) {
            iv.setImageResource(image203);
        } else if (cardsArray[card] == 204) {
            iv.setImageResource(image204);
        } else if (cardsArray[card] == 205) {
            iv.setImageResource(image205);
        } else if (cardsArray[card] == 206) {
            iv.setImageResource(image206);
        }

        if (cardNum == 1) {
            firstCard = cardsArray[card];
            if (firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardNum = 2;
            clickedFirst = card;
            iv.setEnabled(false);
        } else if (cardNum == 2) {
            secondCard = cardsArray[card];
            if (secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNum = 1;
            clickedSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // check if the selected images are equal
                    //if (!peekButtonPress) {
                        calculate();
                    //}
                }
            }, 1000);
        }
    }

    /**
     * Calculate if first move matches second move card
     */
    private void calculate() {
        if (firstCard == secondCard) {
            soundPlayer.playHitOrangeSound();
            if (clickedFirst == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }

            if (clickedSecond == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }

            //update player score in Two Player mode
            if (mode.equals("two player")) {
                TwoPlayerScoreUpdate();
            }
            //Single Player mode would not add score when matched

        } else {
            iv_11.setImageResource(R.drawable.ic_back);
            iv_12.setImageResource(R.drawable.ic_back);
            iv_13.setImageResource(R.drawable.ic_back);
            iv_14.setImageResource(R.drawable.ic_back);
            iv_21.setImageResource(R.drawable.ic_back);
            iv_22.setImageResource(R.drawable.ic_back);
            iv_23.setImageResource(R.drawable.ic_back);
            iv_24.setImageResource(R.drawable.ic_back);
            iv_31.setImageResource(R.drawable.ic_back);
            iv_32.setImageResource(R.drawable.ic_back);
            iv_33.setImageResource(R.drawable.ic_back);
            iv_34.setImageResource(R.drawable.ic_back);

            if (mode.equals("two player")) {
                //switch turn in Two Player
                ChangePlayerTurn();
            } else {
                //add one to score if no match, lower is better
                SinglePlayerNoMatch();
            }
        }


        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

        checkEnd();
    }

    /**
     * For Two Player mode, change the turn.
     */
    private void ChangePlayerTurn() {
        if (turn == 1) {
            turn = 2;
            tv_p1.setTextColor(Color.GRAY);
            tv_p2.setTextColor(Color.BLACK);
        } else if (turn == 2) {
            turn = 1;
            tv_p2.setTextColor(Color.GRAY);
            tv_p1.setTextColor(Color.BLACK);
        }
    }

    /**
     * For Two Player mode, update the score if match successful.
     */
    private void TwoPlayerScoreUpdate() {
        if (turn == 1) {
            playerPoints++;
            String a = "P1: " + playerPoints;
            tv_p1.setText(a);
        } else if (turn == 2) {
            cpuPoints++;
            String b = "P2: " + cpuPoints;
            tv_p2.setText(b);
        }
    }

    /**
     * For Single Player mode, if no match, add one point to the score; the lower, the better
     */
    private void SinglePlayerNoMatch() {

    }


    /**
     * Check whether the game is over
     */
    private void checkEnd() {           //UPDATE: can be checked with a loop
        if (iv_11.getVisibility() == View.INVISIBLE &&
                iv_12.getVisibility() == View.INVISIBLE &&
                iv_13.getVisibility() == View.INVISIBLE &&
                iv_14.getVisibility() == View.INVISIBLE &&
                iv_21.getVisibility() == View.INVISIBLE &&
                iv_22.getVisibility() == View.INVISIBLE &&
                iv_23.getVisibility() == View.INVISIBLE &&
                iv_24.getVisibility() == View.INVISIBLE &&
                iv_31.getVisibility() == View.INVISIBLE &&
                iv_32.getVisibility() == View.INVISIBLE &&
                iv_33.getVisibility() == View.INVISIBLE &&
                iv_34.getVisibility() == View.INVISIBLE) {
            //implement the final state, (scoreboard)
            //AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FlippingGame.this);

            // stop timer
            chronometer.stop();
            // calculate time
            time = SystemClock.elapsedRealtime() - chronometer.getBase();
            // convert time into seconds
            time = time / 1000;
            running = false;


            if (mode.equals("single player")) {
                //Toast.makeText(this, "You completed the game in " + String.valueOf(time) + " seconds", Toast.LENGTH_LONG).show();
                processScore(time);
            } else {
                if (playerPoints == cpuPoints) {
                    Toast.makeText(this, "TIE! with a time of " + String.valueOf(time) + " seconds", Toast.LENGTH_LONG).show();
                } else if (playerPoints > cpuPoints) {
                    Toast.makeText(this, "P1 wins with a time of " + String.valueOf(time) + " seconds", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "P2 wins with a time of " + String.valueOf(time) + " seconds", Toast.LENGTH_LONG).show();
                }
            }

        }

    }

    /**
     * Process the score (time)
     * @param time the score
     */
    private void processScore(long time){
        String oldScore = myDb.getConcentrationScore(user);
        if (oldScore.equals("-1")){
            myDb.setConcentrationScore(user, String.valueOf(time));
            String message = "You completed the game in " + time + " seconds." + "You set a personal record of " +
                    String.valueOf(time) + " seconds. " + "Check the PERSONAL RECORDS to see your updated score!";
            showMessage("You Win!", message, this);
        }
        else if (time > Long.valueOf(oldScore)){
            String message = "You completed the game in " + time + " seconds." +" Unfortunately your score of " +
                    String.valueOf(time) + " did not beat your record of " + myDb.getConcentrationScore(user) +
                    " seconds." + " Better luck next time!";
            showMessage("You Win!", message, this);
        }
        else{
            myDb.setConcentrationScore(user, String.valueOf(time));
            String message = "You completed the game in " + time + " seconds. " + "You set a new personal record of " +
                    String.valueOf(time) + " seconds, beating your previous record of " + oldScore +
                    " seconds." +  " Check the PERSONAL RECORDS to see your updated score!";
            showMessage("You Win!", message, this);
        }

    }

    /**
     * Display message
     * @param title title
     * @param message message
     * @param context this context
     */
    private void showMessage (String title, String message, Context context){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }

    /**
     * Each face-side card images
     */
    private void frontOfCardsResources() {
        image101 = R.drawable.ic_image101;
        image102 = R.drawable.ic_image102;
        image103 = R.drawable.ic_image103;
        image104 = R.drawable.ic_image104;
        image105 = R.drawable.ic_image105;
        image106 = R.drawable.ic_image106;
        image201 = R.drawable.ic_image201;
        image202 = R.drawable.ic_image202;
        image203 = R.drawable.ic_image203;
        image204 = R.drawable.ic_image204;
        image205 = R.drawable.ic_image205;
        image206 = R.drawable.ic_image206;
    }

    /**
     * Go back
     */
    public void onBackPressed() {   //FlippingGameActivity
        super.onBackPressed();
        finish();

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
//                concentration = new FlippingGame();
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
//
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
            System.out.println("it saved through auto save");
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
//
    /**
     * Save FlippingGame on 1-second intervals; no text would be shown.
     */
    private void auto_save() {
        gameCountDownTimer = new CountDownTimer(1000, 1000) {

            public void onFinish() {
                saveToFile(ConcentrationMenu.user + ConcentrationMenu.FLIP_SAVE_FILENAME);
                saveToFile(ConcentrationMenu.user + ConcentrationMenu.FLIP_TEMP_SAVE_FILENAME);
                gameCountDownTimer.start();
            }

            public void onTick(long millisUntilFinished) {
            }
        };

        gameCountDownTimer.start();
    }

    /**
     * Button for peek
     * @param view this view
     */
    public void peekButtonListener(View view) {

        if (iv_11.getVisibility() != View.INVISIBLE){
            setImage(iv_11, 0);
        }
        if (iv_12.getVisibility() != View.INVISIBLE){
            setImage(iv_12, 1);
        }
        if (iv_13.getVisibility() != View.INVISIBLE){
            setImage(iv_13, 2);
        }
        if (iv_14.getVisibility() != View.INVISIBLE){
            setImage(iv_14, 3);
        }
        if (iv_21.getVisibility() != View.INVISIBLE){
            setImage(iv_21, 4);
        }
        if (iv_22.getVisibility() != View.INVISIBLE){
            setImage(iv_22, 5);
        }
        if (iv_23.getVisibility() != View.INVISIBLE){
            setImage(iv_23, 6);
        }
        if (iv_24.getVisibility() != View.INVISIBLE){
            setImage(iv_24, 7);
        }
        if (iv_31.getVisibility() != View.INVISIBLE){
            setImage(iv_31, 8);
        }
        if (iv_32.getVisibility() != View.INVISIBLE){
            setImage(iv_32, 9);
        }
        if (iv_33.getVisibility() != View.INVISIBLE){
            setImage(iv_33, 10);
        }
        if (iv_34.getVisibility() != View.INVISIBLE){
            setImage(iv_34, 11);
        }
        counter --;
        cardNum = 1;
        if (counter == 0) {
            peekbtn.setEnabled(false);
        }
    }
}
