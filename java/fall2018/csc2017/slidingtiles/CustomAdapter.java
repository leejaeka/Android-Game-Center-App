package fall2018.csc2017.slidingtiles;

/*
Taken from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Customize Adapter to handle Tile Buttons
 */
public class CustomAdapter extends BaseAdapter {
    /**
     * Array of all Tile buttons
     */
    private ArrayList<Button> mButtons;
    /**
     * Colummn width and Column height depending on device
     */
    private int mColumnWidth, mColumnHeight;

    /**
     * Initializer for CustomAdapter
     *
     * @param buttons      buttons
     * @param columnWidth  column width
     * @param columnHeight column height
     */
    CustomAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    /**
     * Get number of buttons
     *
     * @return number of buttons
     */
    @Override
    public int getCount() {
        return mButtons.size();
    }

    /**
     * Get Tile button at given position
     *
     * @param position target's position
     * @return button at given position
     */
    @Override
    public Object getItem(int position) {
        return mButtons.get(position);
    }

    /**
     * Get Item's id at given position
     *
     * @param position target's position
     * @return Item's id at given position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get View at given position
     *
     * @param position    the target's position
     * @param convertView convert view
     * @param parent      view's parent
     * @return view at given position
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (Button) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
