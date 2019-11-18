package com.example.dndp.DND;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

public class DNDSnapView extends View implements IDNDPager.Coordinates {

    /**
     * The position of view element inside the relative view.
     * */
    private int x =0,y =0;

    private RelativeLayout layout;

    public DNDSnapView(Context context) {
        super(context);
    }

    public void setPosition(int x,int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void setPosition(IDNDPager.Coordinates coordinates) {
        x = coordinates.getPositionX();
        y = coordinates.getPositionY();
    }


    public int getPositionX(){
        return x;
    }

    public int getPositionY(){
        return y;
    }

    public void setLayout(RelativeLayout layout){
        this.layout = layout;
    }

    public RelativeLayout getLayout(){
        return layout;
    }

}
