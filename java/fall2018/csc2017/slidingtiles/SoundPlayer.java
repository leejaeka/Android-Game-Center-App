package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Handle's game sound effects
 */
public class SoundPlayer {
    /**
     * AudioAttributes
     */
    private AudioAttributes audioAttributes;
    /**
     * maximum sound pool
     */
    final int SOUND_POOL_MAX = 3;
    /**
     * sound pool
     */
    private static SoundPool soundPool;
    /**
     * sound 1
     */
    private static int hitOrangeSound;
    /**
     * sound 2
     */
    private static int hitPinkSound;
    /**
     * sound 3
     */
    private static int hitBlackSound;

    /**
     * play sound effects
     *
     * @param context this context
     */
    public SoundPlayer(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();

        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        hitOrangeSound = soundPool.load(context, R.raw.orange, 1);
        hitPinkSound = soundPool.load(context, R.raw.pink, 1);
        hitBlackSound = soundPool.load(context, R.raw.black, 1);
    }

    /**
     * play sound 1
     */
    void playHitOrangeSound() {
        soundPool.play(hitOrangeSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    /**
     * play sound 2
     */
    void playHitPinkSound() {
        soundPool.play(hitPinkSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    /**
     * play sound 3
     */
    void playHitBlackSound() {
        soundPool.play(hitBlackSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
