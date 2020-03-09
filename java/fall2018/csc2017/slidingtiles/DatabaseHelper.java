package fall2018.csc2017.slidingtiles;
/*
Adapted from:
https://www.youtube.com/watch?v=NT1qxmqH1eM&t=1s
https://www.youtube.com/watch?v=KxlLsk5j3rY&t=1s
https://www.youtube.com/watch?v=A6Jq7NVBVxU&t=1s
https://www.youtube.com/watch?v=KUq5wf3Mh0c&t=2s
https://www.youtube.com/watch?v=PA4A9IesyCg&t=141s

Majority of the code here using the SQLite Database are from the videos
 */

/*
 * The DatabaseHelper class. Stores accounts and is able to manage them.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Helper for Database (Handles user accounts)
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Database Version
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Database name
     */
    private static final String DATABASE_NAME = "accounts.db";
    /**
     * table name
     */
    private static final String TABLE_NAME = "accounts";
    /**
     * column : name
     */
    private static final String COLUMN_NAME = "name";
    /**
     * column : pass
     */
    private static final String COLUMN_PASS = "pass";
    /**
     * column : score
     */
    private static final String COLUMN_SCORE = "score";
    /**
     * column : current_score for sliding tiles game
     */
    private static final String COLUMN_SLIDINGTILES_CURRENT_SCORE = "slidingtilescurrentscore";
    /**
     * column : current_score for Squirtle game
     */
    private static final String COLUMN_SQUIRTLE_SCORE = "squirtlegamescore";
    /**
     * column : current_score for concentration game
     */
    private static final String COLUMN_CONCENTRATION_SCORE = "concentrationgamescore";
    /**
     * SQLiteDatabase
     */
    private SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table accounts (name text primary key not null, " +
            "pass text not null, score integer not null, slidingtilescurrentscore integer not null, " +
            "squirtlegamescore integer not null, concentrationgamescore integer not null);";


    /**
     * Initializer for DatabaseHelper
     *
     * @param context this context
     */
    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Initiate Table
     *
     * @param db this SqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;

    }

    /**
     * Adds the account object to the database.
     *
     * @param a Account object
     */
    void insertAccount(Account a) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from accounts";
        Cursor cursor = db.rawQuery(query, null);

        values.put(COLUMN_NAME, a.getName());
        values.put(COLUMN_PASS, a.getPass());
        values.put(COLUMN_SCORE, "-1");
        values.put(COLUMN_SLIDINGTILES_CURRENT_SCORE, "0");
        values.put(COLUMN_SQUIRTLE_SCORE, "-1");
        values.put(COLUMN_CONCENTRATION_SCORE, "-1");

        db.insert(TABLE_NAME, null, values);
        cursor.close();
        db.close();

    }

    /**
     * Search for validity for username's password inside database
     *
     * @param name username of account to search for in the database.
     * @return return the password associated with the username name.
     */
    String searchPass(String name) {
        db = this.getReadableDatabase();
        String query = "select name, pass from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        b = "not found";

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(name)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return b;

    }

    /**
     * Check for validity of target name in database
     *
     * @param name to search for.
     * @return return whether a username exists in database.
     */
    boolean checkIfNameExists(String name) {
        db = this.getReadableDatabase();
        String query = "select name from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a;

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(name)) {
                    return true;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return false;

    }

    /**
     * Get user's score
     *
     * @param name user's name
     * @return target user's score
     */
    String getScore(String name) {
        db = this.getReadableDatabase();
        String query = "select name, score from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(name)) {
                    b = cursor.getString(1);
                    return b;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return "not working";

    }

    /**
     * Setter for score column
     *
     * @param name  username
     * @param score target user's score
     */
    void setScore(String name, String score) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        db.update(TABLE_NAME, values, "name = ?", new String[]{name});
        db.close();
    }

    /**
     * Get number of total accounts created
     *
     * @return number of accounts
     */
    String numOfAccounts() {
        db = this.getReadableDatabase();
        String query = "select * from accounts";
        Cursor cursor = db.rawQuery(query, null);
        String count = Integer.toString(cursor.getCount());
        cursor.close();
        return count;

    }

    /**
     * get slidingtile's current score column
     *
     * @param name user's name
     * @return currentScore for slidingtiles game
     */
    String getSlidingtilesCurrentScore(String name) {
        db = this.getReadableDatabase();
        String query = "select name, slidingtilescurrentscore from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(name)) {
                    b = cursor.getString(1);
                    return b;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return "not working";

    }

    /**
     * setter for slidingtiles current score
     *
     * @param name  user's name
     * @param score user's current score
     */
    void setSlidingtilesCurrentScore(String name, String score) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SLIDINGTILES_CURRENT_SCORE, score);
        db.update(TABLE_NAME, values, "name = ?", new String[]{name});
        db.close();
    }

    /**
     * setter for squirtle game current score
     *
     * @param name  user's name
     * @param score user's current score
     */
    void setSquirtleScore(String name, String score) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SQUIRTLE_SCORE, score);
        db.update(TABLE_NAME, values, "name = ?", new String[]{name});
        db.close();
    }

    /**
     * get score for squirtle game
     *
     * @param name user's name
     * @return score for squirtle game
     */
    String getSquirtleScore(String name) {
        db = this.getReadableDatabase();
        String query = "select name, squirtlegamescore from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(name)) {
                    b = cursor.getString(1);
                    return b;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return "not working";

    }

    /**
     * get current score for concentration game
     *
     * @param name user's name
     * @return current score for concentration game
     */
    String getConcentrationScore(String name) {
        db = this.getReadableDatabase();
        String query = "select name, concentrationgamescore from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);

                if (a.equals(name)) {
                    b = cursor.getString(1);
                    return b;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return "not working";

    }
    HashMap<String, String> getSquirtleNameScorePair() {
        db = this.getReadableDatabase();
        String query = "select name, squirtlegamescore from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        HashMap<String, String> h = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                b = cursor.getString(1);
                if (!b.equals("-1")) {
                    h.put(a,b);
                }

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return h;

    }
    HashMap<String, String> getConcNameScorePair() {
        db = this.getReadableDatabase();
        String query = "select name, concentrationgamescore from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        HashMap<String, String> h = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                b = cursor.getString(1);
                if (!b.equals("-1")) {
                    h.put(a,b);
                }

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return h;

    }

    /**
     * setter for squirtle game current score
     *
     * @param name  user's name
     * @param score user's current score
     */
    void setConcentrationScore(String name, String score) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONCENTRATION_SCORE, score);
        db.update(TABLE_NAME, values, "name = ?", new String[]{name});
        db.close();
    }

    HashMap<String, String> getNameScorePair() {
        db = this.getReadableDatabase();
        String query = "select name, score from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b;
        HashMap<String, String> h = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                b = cursor.getString(1);
                if (!b.equals("-1")) {
                    h.put(a,b);
                }

            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return h;

    }


    /**
     * Update SQLite database db
     *
     * @param db         this database
     * @param oldVersion old database
     * @param newVersion updated database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }
}
