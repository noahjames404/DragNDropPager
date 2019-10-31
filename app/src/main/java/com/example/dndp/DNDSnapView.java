package com.example.dndp;

import android.content.Context;
import android.view.View;

public class DNDSnapView extends View {

    /**
     * The position of view element inside the relative view.
     * */
    private int x =0,y =0;

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


}
