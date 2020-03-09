package fall2018.csc2017.slidingtiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * SOURCE : Learned custom adapter from: https://www.youtube.com/watch?v=FKUlw7mFXRM
 * Handles Scoreboard & Leaderboard
 */
public class Scoreboard extends AppCompatActivity {
    /**
     * Database
     */
    DatabaseHelper myDb;
    /**
     * List of Images tags
     */
    String[] IMAGES = {"#1", "#2", "#3", "#4", "#5"};
    /**
     * List of Names tags
     */
    String[] NAMES = {"Name: ", "Name: ", "Name: ", "Name: ", "Name: "};
    /**
     * List of Score tags
     */
    String[] SCORE = {"Score: ", "Score: ", "Score: ", "Score: ", "Score: "};
    /**
     * List of top 5 scores
     */
    ArrayList<Integer> scores = new ArrayList<>(5);
    /**
     * List of top 5 users with the top 5 scores
     */
    ArrayList<String> names = new ArrayList<>(5);

    String type;

    /**
     * Handles Leaderboard Screen
     *
     * @param savedInstanceState saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        type = getIntent().getStringExtra("Type");
        myDb = new DatabaseHelper(this);

        if (type.equals("squirtle")){
            getTopFiveNames();

        }
        else {
            getLowestFiveNames();
        }

        ListView listView = findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);


        myDb = new DatabaseHelper(this);
    }

    /**
     * Update top 5 players with lowest score = top score
     */
    private void getLowestFiveNames() {
        HashMap<String, String> h;
        if (type.equals("conc")){
            h = myDb.getConcNameScorePair();
        }
        else{
            h = myDb.getNameScorePair();
        }


        for (HashMap.Entry<String, String> entry : h.entrySet()) {
            if (scores.isEmpty()) {
                scores.add(Integer.valueOf(entry.getValue()));
                names.add(entry.getKey());
            } else if (scores.size() < 5) {
                int index = 0;
                ArrayList<Integer> scores_temp = new ArrayList<>();
                for (Integer i : scores) {
                    scores_temp.add(i);
                }
                for (Integer i : scores_temp) {
                    if (i > Integer.valueOf(entry.getValue())) {
                        scores.add(index, Integer.valueOf(entry.getValue()));
                        names.add(index, entry.getKey());
                        break;
                    }
                    index++;
                }
                if (index == scores.size()) {
                    scores.add(index, Integer.valueOf(entry.getValue()));
                    names.add(index, entry.getKey());
                }
            } else {
                int index_a = 0;
                ArrayList<Integer> scores_temp_b = new ArrayList<>();
                for (Integer i : scores) {
                    scores_temp_b.add(i);
                }
                for (Integer i : scores_temp_b) {
                    if (i > Integer.valueOf(entry.getValue())) {
                        scores.add(index_a, Integer.valueOf(entry.getValue()));
                        scores.remove(5);
                        names.add(index_a, entry.getKey());
                        names.remove(5);
                        break;
                    }
                    index_a++;
                }
                if (index_a == scores.size()) {
                    scores.add(index_a, Integer.valueOf(entry.getValue()));
                    scores.remove(5);
                    names.add(index_a, entry.getKey());
                    names.remove(5);
                }
            }
        }
        for (int i = 0; i < scores.size(); i++) {
            if (type.equals("conc")) {
                SCORE[i] = SCORE[i] + String.valueOf(scores.get(i)) + " seconds";
            }
            else  {
                SCORE[i] = SCORE[i] + String.valueOf(scores.get(i)) + " moves";
            }
        }
        for (int j = 0; j < scores.size(); j++) {
            if (type.equals("conc")) {
                NAMES[j] = NAMES[j] + names.get(j);
            }
            else {
                NAMES[j] = NAMES[j] + names.get(j);
            }
        }
    }

    /**
     * Update top 5 players with largest score = top score
     */
    private void getTopFiveNames() {
        HashMap<String, String> h;
        h = myDb.getSquirtleNameScorePair();


        for (HashMap.Entry<String, String> entry : h.entrySet()) {
            if (scores.isEmpty()) {
                scores.add(Integer.valueOf(entry.getValue()));
                names.add(entry.getKey());
            } else if (scores.size() < 5) {
                int index = 0;
                ArrayList<Integer> scores_temp = new ArrayList<>();
                for (Integer i : scores) {
                    scores_temp.add(i);
                }
                for (Integer i : scores_temp) {
                    if (i < Integer.valueOf(entry.getValue())) {
                        scores.add(index, Integer.valueOf(entry.getValue()));
                        names.add(index, entry.getKey());
                        break;
                    }
                    index++;
                }
                if (index == scores.size()) {
                    scores.add(index, Integer.valueOf(entry.getValue()));
                    names.add(index, entry.getKey());
                }
            } else {
                int index_a = 0;
                ArrayList<Integer> scores_temp_b = new ArrayList<>();
                for (Integer i : scores) {
                    scores_temp_b.add(i);
                }
                for (Integer i : scores_temp_b) {
                    if (i < Integer.valueOf(entry.getValue())) {
                        scores.add(index_a, Integer.valueOf(entry.getValue()));
                        scores.remove(5);
                        names.add(index_a, entry.getKey());
                        names.remove(5);
                        break;
                    }
                    index_a++;
                }
                if (index_a == scores.size()) {
                    scores.add(index_a, Integer.valueOf(entry.getValue()));
                    scores.remove(5);
                    names.add(index_a, entry.getKey());
                    names.remove(5);
                }
            }
        }
        for (int i = 0; i < scores.size(); i++) {
            SCORE[i] = SCORE[i] + String.valueOf(scores.get(i)) + " points";
        }
        for (int j = 0; j < scores.size(); j++) {
            NAMES[j] = NAMES[j] + names.get(j);
        }
    }


    /**
     * Customize Adapter to get view for each top players
     */
    class CustomAdapter extends BaseAdapter {

        /**
         * get count
         *
         * @return
         */
        @Override
        public int getCount() {
            return IMAGES.length;
        }

        /**
         * get item
         *
         * @param i int
         * @return null
         */
        @Override
        public Object getItem(int i) {
            return null;
        }

        /**
         * get item id
         *
         * @param i id
         * @return 0
         */
        @Override
        public long getItemId(int i) {
            return 0;
        }

        /**
         * get view
         *
         * @param i         int
         * @param view      this view
         * @param viewGroup view's parent
         * @return view
         */
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout, null);

            TextView textView_rank = view.findViewById(R.id.imageView);
            TextView textView_name = view.findViewById(R.id.textView_Name);
            TextView textView_Score = view.findViewById(R.id.textView_Score);

            textView_rank.setText(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            textView_Score.setText(SCORE[i]);


            return view;
        }
    }


}
