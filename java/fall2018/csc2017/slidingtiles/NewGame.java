package fall2018.csc2017.slidingtiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * NewGame is the second game in our game centre known as the Squirtle game.
 * It was adapated from: https://www.youtube.com/watch?v=rs10f5MsKEQ&t=196s
 * Learned how to implement chronometer from: https://www.youtube.com/watch?v=RLnb4vVkftc
 */
public class NewGame extends AppCompatActivity {

    /**
     * frame layout
     */
    private FrameLayout gameFrame;
    private int frameHeight, frameWidth, initialFrameWidth;
    private LinearLayout startLayout;

    /**
     * initalize falling objects
     */
    private ImageView box, black, orange, pink;
    private Drawable imageBoxRight, imageBoxLeft;

    /**
     * size of box Squirtle that he is allowed to move.
     */
    private int boxSize;

    /**
     * x and  coordinates
     */
    private float boxX, boxY;
    private float blackX, blackY;
    private float orangeX, orangeY;
    private float pinkX, pinkY;

    /**
     * score textviews
     */
    private TextView scoreLabel, highScoreLabel;
    private int score, highScore, timeCount;
    private SharedPreferences settings;

    /**
     * call a few other classes
     */
    private Timer timer;
    private Handler handler = new Handler();
    private fall2018.csc2017.slidingtiles.SoundPlayer soundPlayer;

    /**
     * status flags
     */
    private boolean start_flg = false;
    private boolean action_flg = false;
    private boolean pink_flg = false;

    /**
     * difficultly level
     */
    public String level;

    /**
     * code used to handle on screen timer
     */
    private Chronometer chronometer;
    private boolean running;
    long time;
    private String user;
    DatabaseHelper myDb;

    /**
     * Initialize the game
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game_layout);

        chronometer = findViewById(R.id.chronometer);

        level = getIntent().getStringExtra("level");
        user = getIntent().getStringExtra("Username");

        soundPlayer = new fall2018.csc2017.slidingtiles.SoundPlayer(this);
        myDb = new DatabaseHelper(this);

        gameFrame = findViewById(R.id.gameFrame);
        startLayout = findViewById(R.id.startLayout);
        box = findViewById(R.id.box);
        black = findViewById(R.id.black);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        scoreLabel = findViewById(R.id.scoreLabel);
        highScoreLabel = findViewById(R.id.highScoreLabel);

        imageBoxLeft = getResources().getDrawable(R.drawable.box_left);
        imageBoxRight = getResources().getDrawable(R.drawable.box_right);

        // High Score
        settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore = settings.getInt("HIGH_SCORE", 0);
        String a = "High Score : " + highScore;
        highScoreLabel.setText(a);
    }

    /**
     * Handles changing the position of the objects dropping down
     */
    public void changePos() {

        // Add timeCount
        timeCount += 20;

        // Orange
        orangeY += 12;

        float orangeCenterX = orangeX + orange.getWidth() / 2;
        float orangeCenterY = orangeY + orange.getHeight() / 2;

        if (hitCheck(orangeCenterX, orangeCenterY)) {
            orangeY = frameHeight + 100;
            score += 10;
            soundPlayer.playHitOrangeSound();
        }

        if (orangeY > frameHeight) {
            orangeY = -100;
            orangeX = (float) Math.floor(Math.random() * (frameWidth - orange.getWidth()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        // Pink
        if (!pink_flg && timeCount % 10000 == 0) {
            pink_flg = true;
            pinkY = -20;
            pinkX = (float) Math.floor(Math.random() * (frameWidth - pink.getWidth()));
        }

        if (pink_flg) {
            pinkY += 20;

            float pinkCenterX = pinkX + pink.getWidth() / 2;
            float pinkCenterY = pinkY + pink.getWidth() / 2;

            if (hitCheck(pinkCenterX, pinkCenterY)) {
                pinkY = frameHeight + 30;
                score += 30;
                // Change FrameWidth
                if (initialFrameWidth > frameWidth * 110 / 100) {
                    frameWidth = frameWidth * 110 / 100;
                    changeFrameWidth(frameWidth);
                }
                soundPlayer.playHitPinkSound();
            }

            if (pinkY > frameHeight) pink_flg = false;
            pink.setX(pinkX);
            pink.setY(pinkY);
        }

        if (level.equals("easy")) {
            blackY += 18;
        } else if (level.equals("medium")) {
            blackY += 34;

        } else if (level.equals("hard")) {
            blackY += 50;
        }


        float blackCenterX = blackX + black.getWidth() / 2;
        float blackCenterY = blackY + black.getHeight() / 2;

        if (hitCheck(blackCenterX, blackCenterY)) {
            blackY = frameHeight + 100;

            // Change FrameWidth
            frameWidth = frameWidth * 80 / 100;
            changeFrameWidth(frameWidth);
            soundPlayer.playHitBlackSound();
            if (frameWidth <= boxSize) {
                gameOver();
            }

        }

        if (blackY > frameHeight) {
            blackY = -100;
            blackX = (float) Math.floor(Math.random() * (frameWidth - black.getWidth()));
        }

        black.setX(blackX);
        black.setY(blackY);

        // Move Box
        if (action_flg) {
            // Touching
            boxX += 14;
            box.setImageDrawable(imageBoxRight);
        } else {
            // Releasing
            boxX -= 14;
            box.setImageDrawable(imageBoxLeft);
        }

        // Check box position.
        if (boxX < 0) {
            boxX = 0;
            box.setImageDrawable(imageBoxRight);
        }
        if (frameWidth - boxSize < boxX) {
            boxX = frameWidth - boxSize;
            box.setImageDrawable(imageBoxLeft);
        }

        box.setX(boxX);
        String b = "Score : " + score;
        scoreLabel.setText(b);

    }

    /**
     * Check if the user has been hit
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public boolean hitCheck(float x, float y) {
        if (boxX <= x && x <= boxX + boxSize &&
                boxY <= y && y <= frameHeight) {
            return true;
        }
        return false;
    }

    /**
     * Change the frame to increase or decrease the size
     *
     * @param frameWidth width of the frame
     */
    public void changeFrameWidth(int frameWidth) {
        ViewGroup.LayoutParams params = gameFrame.getLayoutParams();
        params.width = frameWidth;
        gameFrame.setLayoutParams(params);
    }

    /**
     * Stop the game
     */
    public void gameOver() {
        // stop timer
        chronometer.stop();
        // calculate time
        time = SystemClock.elapsedRealtime() - chronometer.getBase();
        // convert time into seconds
        time = time / 1000;
        running = false;
        //Toast.makeText(this, "Lived for "+ String.valueOf(time) + " seconds", Toast.LENGTH_LONG).show();

        // Stop timer.
        timer.cancel();
        timer = null;
        start_flg = false;

        // Before showing startLayout, sleep 1 second.
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        changeFrameWidth(initialFrameWidth);

        startLayout.setVisibility(View.VISIBLE);
        box.setVisibility(View.INVISIBLE);
        black.setVisibility(View.INVISIBLE);
        orange.setVisibility(View.INVISIBLE);
        pink.setVisibility(View.INVISIBLE);

        processScore(time);

        // Update High Score
        if (score > highScore) {
            highScore = score;
            String a = "High Score : " + highScore;
            highScoreLabel.setText(a);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", highScore);
            editor.commit();


        }
    }

    /**
     * Handles the input
     *
     * @param event input that's given
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (start_flg) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;

            }
        }
        return true;
    }


    /**
     * Start a new game
     *
     * @param view view of the game
     */
    public void startGame(View view) {
        start_flg = true;
        startLayout.setVisibility(View.INVISIBLE);

        // check if chrono is not running
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            running = true;
        }


        if (frameHeight == 0) {
            frameHeight = gameFrame.getHeight();
            frameWidth = gameFrame.getWidth();
            initialFrameWidth = frameWidth;

            boxSize = box.getHeight();
            boxX = box.getX();
            boxY = box.getY();
        }

        frameWidth = initialFrameWidth;

        box.setX(0.0f);
        black.setY(3000.0f);
        orange.setY(3000.0f);
        pink.setY(3000.0f);

        blackY = black.getY();
        orangeY = orange.getY();
        pinkY = pink.getY();

        box.setVisibility(View.VISIBLE);
        black.setVisibility(View.VISIBLE);
        orange.setVisibility(View.VISIBLE);
        pink.setVisibility(View.VISIBLE);

        timeCount = 0;
        score = 0;
        String a = "Score : 0";
        scoreLabel.setText(a);


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (start_flg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }
        }, 0, 20);
    }

    /**
     * quit the game
     *
     * @param view view of the game
     */
    public void quitGame(View view) {
        finish();

    }

    /**
     * when back button pressed, terminate
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        while (timer != null) {
            timer.cancel();
            timer = null;
        }

        start_flg = false;
        if (score > highScore) {
            highScore = score;
            String a = "High Score : " + highScore;
            highScoreLabel.setText(a);

            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", highScore);
            editor.commit();
        }
        myDb.close();
        finish();

    }

    /**
     * display message in a form of a alert
     *
     * @param title   alert title
     * @param message message
     * @param context game context
     */
    private void showMessage(String title, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }

    /**
     * Process the score to determine if the current score is better or worse than
     * the user's top score
     *
     * @param time time they survived for
     */
    private void processScore(long time) {
        String oldScore = myDb.getSquirtleScore(user);
        if (oldScore.equals("-1")) {
            myDb.setSquirtleScore(user, Integer.toString(score));
            String message = "Oh no, you killed Squirtle! You set a personal record of " +
                    Integer.toString(score) + " points. You survived for " + String.valueOf(time) + " seconds. Check the PERSONAL RECORDS to see your updated score!";
            showMessage("Game Over!", message, this);
        } else if (score < Integer.valueOf(oldScore)) {
            String message = "Oh no, you killed Squirtle! Unfortunately your score of " +
                    Integer.toString(score) + " did not beat your record of " + myDb.getSquirtleScore(user) +
                    " points." + " You survived for " + String.valueOf(time) + " seconds. " + "Better luck next time!";
            showMessage("Game Over!", message, this);
        } else {
            myDb.setSquirtleScore(user, Integer.toString(score));
            String message = "Oh no, you killed Squirtle! You set a new personal record of " +
                    Integer.toString(score) + " points, beating your previous record of " + oldScore +
                    " points. You survived for " + String.valueOf(time) + " seconds. Check the PERSONAL RECORDS to see your updated score!";
            showMessage("Game Over!", message, this);
        }

    }


}
