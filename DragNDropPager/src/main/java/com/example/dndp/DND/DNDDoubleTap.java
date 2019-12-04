package com.example.dndp.DND;

import android.view.View;

/**
 * primarily used for onTouchListners to detect double tap events
 */
public class DNDDoubleTap {
    static final String TAG ="DNDDoubleTap";

    private long last_touch_time = 0;
    private long currentTouchTime = 0;
    private int max_duration;
    private View last_view;


    /**
     * Ensure that the dblClick must occur only to that view,
     * if the user manages to dblClick from one view to another and done before the max_duration limit
     * then check if the recent view clicked is the same as the current view,
     * the last view must be equal to current view clicked to callback.
     * @param view - used to check if the last_view.
     * @param event - callback when double clicked.
     */
    void doubleTap(View view, IDNDPager.ActionEvent event){
        last_touch_time = currentTouchTime;
        currentTouchTime = System.currentTimeMillis();

        if (currentTouchTime - last_touch_time < max_duration) {
            if(last_view == view){
                event.onExecute();
            }
            last_touch_time = 0;
            currentTouchTime = 0;
        }
        last_view = view;

    }


    public int getMaxDuration() {
        return max_duration;
    }

    /**
     *
     * @param max_duration - the max duration between clicks in milliseconds
     */
    public void setMaxDuration(int max_duration) {
        this.max_duration = max_duration;
    }
}
