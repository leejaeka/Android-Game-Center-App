package fall2018.csc2017.slidingtiles;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Custom GridView
 */
public class GestureDetectGridView extends GridView {
    /**
     * Minimum swipe distance
     */
    public static final int SWIPE_MIN_DISTANCE = 100;
    /**
     * gesture detector
     */
    private GestureDetector gDetector;
    /**
     * movement controller
     */
    private MovementController mController;
    /**
     * m fling confirmed
     */
    private boolean mFlingConfirmed = false;
    /**
     * x
     */
    private float mTouchX;
    /**
     * y
     */
    private float mTouchY;
    /**
     * board manager
     */
    private BoardManager boardManager;

    /**
     * Initializer for GestureDetectGridView
     *
     * @param context this context
     */
    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Initializer for GestureDetectGridView
     *
     * @param context this context
     * @param attrs   attribute
     */
    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initializer for GestureDetectGridView
     *
     * @param context      this context
     * @param attrs        attribute
     * @param defStyleAttr this def style attribute
     */
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Initiate Detect
     *
     * @param context this context
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init(final Context context) {
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            /**
             * confirm single tap
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                mController.processTapMovement(context, position, true);
                return true;
            }

            /**
             * On down
             * @param event this event
             * @return true
             */
            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }
        });
    }

    /**
     * Do this on intercept touch event
     *
     * @param ev this event
     * @return whether intercept touch event
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * Do this on touch
     *
     * @param ev this event
     * @return detect touch
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Setter for this boardmanager
     *
     * @param boardManager this boardmanager
     */
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
        mController.setBoardManager(boardManager);
    }
}
