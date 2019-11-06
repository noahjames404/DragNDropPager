package com.example.dndp;

import android.util.Log;
import android.view.View;

/**
 * primarily used for onTouchListners
 */
public class DNDDoubleTap {
    static final String TAG ="DNDDoubleTap";

    //variable for counting two successive up-down events
    int clickCount = 0;
    //variable for storing the time of first click
    long startTime;
    //variable for calculating the total time
    long duration;
    //constant for defining the time duration between the click that can be considered as double-tap
    static final int MAX_DURATION = 200;

    /**
     * use for onDown events
     */
    void onTap(){
        startTime = System.currentTimeMillis();
        clickCount++;
        Log.d(TAG, "onTap: ");
    }

    /**
     * use for onUp events
     * @param event executes when user double taps
     */
    void onRelease(IDNDPager.ActionEvent event){
        long time = System.currentTimeMillis() - startTime;
        duration += time;
        if(clickCount >= 2)
        {
            if(duration<= MAX_DURATION)
            {
                event.onExecute();
            }
            Log.d(TAG, "onRelease: clearing " + clickCount);
            clickCount = 0;
            duration = 0;

        }

        Log.d(TAG, "onRelease: " + clickCount);
    }


}
