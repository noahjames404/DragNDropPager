package com.example.dndp.DND;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * DNDSnapView is a listener view used for drag and drop events,
 * when draggable objects enters the view, it creates a shadow lock that guide users.
 * It also inform users if the area to place the object is obstructed.
 */
public class DNDSnapView extends View implements IDNDPager.Coordinates {

    /**
     * The position of view element inside the relative view.
     * */
    private int x =0,y =0;
    /**
     * the current layout it resides in.
     */
    private RelativeLayout layout;

    public DNDSnapView(Context context) {
        super(context);
    }

    /**
     * set the current coordinates of the view
     * Note: this does not directly apply when already added to DNDPager, it only serves as an information.
     * @param x - x coordinate inside the layout
     * @param y - y coordinate inside the layout
     */
    public void setPosition(int x,int y){
        this.x = x;
        this.y = y;
    }

    /**
     * set the current coordinates of the view
     * Note: this does not directly apply when already added to DNDPager, it only serves as an information.
     * @param coordinates - interface can be used to specify the coordinates inside the layout
     */
    @Override
    public void setPosition(IDNDPager.Coordinates coordinates) {
        x = coordinates.getPositionX();
        y = coordinates.getPositionY();
    }

    /**
     * get the x position of the view inside the layout
     * @return
     */
    public int getPositionX(){
        return x;
    }

    /**
     * get the y position of the view inside the layout
     * @return
     */
    public int getPositionY(){
        return y;
    }

    /**
     * get the parent layout of the view
     * @return RelativeLayout.
     */
    public RelativeLayout getLayout(){
        return (RelativeLayout) getParent();
    }

}
