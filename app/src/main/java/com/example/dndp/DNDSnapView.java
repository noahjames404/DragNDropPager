package com.example.dndp;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class DNDSnapView extends View {

    /**
     * The position of view element inside the relative view.
     * */
    private int x =0,y =0;

    private boolean is_occupied = false;

    private RelativeLayout layout;

    public DNDSnapView(Context context) {
        super(context);
    }

    public void setPosition(int x,int y){
        this.x = x;
        this.y = y;
    }

    public int getPositionX(){
        return x;
    }

    public int getPositionY(){
        return y;
    }

    public boolean isOccupied(){
        return is_occupied;
    }

    public void setOccupancy(boolean status){
        is_occupied = status;
    }

    public void setLayout(RelativeLayout layout){
        this.layout = layout;
    }

    public RelativeLayout getLayout(){
        return layout;
    }


}
