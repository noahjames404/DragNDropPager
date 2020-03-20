package com.example.dndp.DND;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.widget.Button;

import androidx.annotation.RequiresApi;

public class DNDButton extends Button implements IDNDPager.Item {
    protected int cell_width, cell_height;

    protected String group_id;

    protected DNDPager pager;

    protected GradientDrawable gradient_drawable;

    protected int color;

    protected int x,y;

    protected Drawable bg_image = null;

    public DNDButton(Context context) {
        super(context);
        gradient_drawable = new GradientDrawable();
    }

    /**
     * get the current width of button in ratio.
     * ratio is used instead of actual px size since the parent layout's size vary.
     * The ratio is base on the layout's number of columns defined in the {@link DNDPager} class,
     * it must be greater than or equal to one and less than or equal to the number of the parent layout's number of columns.
     * @return
     */
    public int getCellWidthRatio() {
        return cell_width;
    }

    /**
     * set the current width of button in ratio.
     * ratio is used instead of actual px size since the parent layout's size vary.
     * The ratio is base on the layout's number of columns defined in the {@link DNDPager} class,
     * it must be greater than or equal to one and less than or equal to the number of the parent layout's number of columns.
     * @return
     */
    public void setCellWidthRatio(int cell_width) {
        this.cell_width = cell_width;
    }

    /**
     * get the current width of button in ratio.
     * ratio is used instead of actual px size since the parent layout's size vary.
     * The ratio is base on the layout's number of rows defined in the {@link DNDPager} class,
     * it must be greater than or equal to one and less than or equal to the number of the parent layout's number of rowss.
     * @return
     */
    public int getCellHeightRatio() {
        return cell_height;
    }

    /**
     * set the current width of button in ratio.
     * ratio is used instead of actual px size since the parent layout's size vary.
     * The ratio is base on the layout's number of rows defined in the {@link DNDPager} class,
     * it must be greater than or equal to one and less than or equal to the number of the parent layout's number of rowss.
     * @return
     */
    public void setCellHeightRatio(int cell_height) {
        this.cell_height = cell_height;
    }

    /**
     * set the last DNDPager's origin
     * DNDButtons can migrate from one layout to another with the same {@link DNDButton#group_id} by dragging the view,
     * by doing so DNDPager's can identify if it came from a foreign layout,
     * the button is then removed from its origin and then added to the new layout and update it's new origin.
     * If the button is native to the layout then it's coordinates are updated.
     * @param pager - recent DNDPager class i.e. origin
     */
    public void setLastPager(DNDPager pager){
        this.pager = pager;
    }

    /**
     * get the last DNDPager's origin
     * DNDButtons can migrate from one layout to another with the same {@link DNDButton#group_id} by dragging the view,
     * by doing so DNDPager's can identify if it came from a foreign layout,
     * the button is then removed from its origin and then added to the new layout.
     * If the button is native to the layout then it's coordinates are updated.
     */
    public DNDPager getLastPager(){
        return pager;
    }

    /**
     * Buttons inside the same layout carries the same group_id,
     * applied when dragging a view to another layout with the same group_id.
     * If a button with a different group_id is migrated to another layout it won't be accepted and force back to it's origin.
     * @return the group_id of DNDButton.
     */
    public String getGroupId() {
        return group_id;
    }

    /**
     * set the group_id of a button.
     * Buttons inside the same layout carries the same group_id,
     * applied when dragging a view to another layout with the same group_id.
     * If a button with a different group_id is migrated to another layout it won't be accepted and force back to it's origin.
     * @param group_id - defined by DNDPager upon adding a button.
     */
    public void setGroupId(String group_id) {
        this.group_id = group_id;
    }

    /**
     * set the background color of the button
     * @param color - accepts integer as color. {@link Color#parseColor(String) can also be used to apply hex}
     */
    @Override
    public void setBackgroundColor(int color) {
        this.color = color;
        gradient_drawable.setColor(color);
        setBackground(gradient_drawable);
    }

    /**
     * @return - the background color of the button.
     */
    public int getColor(){
        return color;
    }

    /**
     * set the corners of the buttons by radius
     * @param radius
     */
    public void setCornerRadius(float radius){
        gradient_drawable.setCornerRadius(radius);
        setBackground(gradient_drawable);
    }

    /**
     * set the border of the button
     * @param border set the width
     * @param color set the border color
     */
    public void setBorder(int border,int color){
        gradient_drawable.setStroke(border,color);
        setBackground(gradient_drawable);
    }


    /**
     * set the background image the button.
     * @param border
     * @param image
     */
    public void setBackgroundImage(int border, int border_color, Drawable image){
        GradientDrawable frame = gradient_drawable;
        frame.setStroke(border,border_color);
        Drawable[] layers = {image,frame};
        LayerDrawable result = new LayerDrawable(layers);
        bg_image = image;
        setBackground(result);
    }

    /**
     * get the background image of the button
     * @return
     */
    public Drawable getBackgroundImage(){
        return bg_image;
    }

    /**
     * set the current coordinates of the button
     * Note: this does not directly apply when already added to DNDPager, it only serves as an information.
     * @param x - x coordinate inside the layout
     * @param y - y coordinate inside the layout
     */
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * set the current coordinates of the button
     * Note: this does not directly apply when already added to DNDPager, it only serves as an information.
     * @param coordinates - interface can be used to specify the coordinates inside the layout
     */
    @Override
    public void setPosition(IDNDPager.Coordinates coordinates) {
        x = coordinates.getPositionX();
        y = coordinates.getPositionY();
    }

    /**
     * get the x position of the button inside the layout
     * @return
     */
    @Override
    public int getPositionX() {
        return x;
    }

    /**
     * get the y position of the button inside the layout
     * @return
     */
    @Override
    public int getPositionY() {
        return y;
    }





}
