package com.example.dndp.DND;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;


/**
 * used for editing the size of buttons by dragging the pins, it is not applied yet.
 */
public class DNDPin extends View {

    public enum DRAG_DIRECTION{
        DRAG_HORIZONAL,
        DRAG_VERTICAL

    }
    float dX, dY;
    public DNDPin(DRAG_DIRECTION direction,Context context) {
        super(context);
        onDrag(direction);

    }

    public void onDrag(final DRAG_DIRECTION direction){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float x_direction = 0, y_direction =0;
                if(direction == direction.DRAG_HORIZONAL){
                    x_direction =  event.getRawX();
                }else if(direction == direction.DRAG_VERTICAL) {
                    y_direction =  event.getRawY();
                }
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:



                        dX = view.getX() -x_direction;
                        dY = view.getY() - y_direction;
                        break;

                    case MotionEvent.ACTION_MOVE:

                        view.animate()
                                .x(x_direction + dX)
                                .y(y_direction + dY)
                                .setDuration(0)
                                .start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }


}
