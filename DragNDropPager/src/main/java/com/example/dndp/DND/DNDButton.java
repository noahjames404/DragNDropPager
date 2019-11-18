package com.example.dndp.DND;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.widget.Button;

import androidx.annotation.RequiresApi;

@SuppressLint("AppCompatCustomView")
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class DNDButton extends Button implements IDNDPager.Item, IDNDPager.ItemEvent {
    private int cell_width, cell_height;

    private String group_id;

    private DNDPager pager;

    private GradientDrawable gradient_drawable;

    private int color;

    private int x,y;

    private Drawable bg_image = null;

    public DNDButton(Context context) {
        super(context);
        gradient_drawable = new GradientDrawable();
    }

    public int getCellWidthRatio() {
        return cell_width;
    }

    public void setCellWidthRatio(int cell_width) {
        this.cell_width = cell_width;
    }

    public int getCellHeightRatio() {
        return cell_height;
    }

    public void setCellHeightRatio(int cell_height) {
        this.cell_height = cell_height;
    }

    public void setLastPager(DNDPager pager){
        this.pager = pager;
    }

    public DNDPager getLastPager(){
        return pager;
    }

    public String getGroupId() {
        return group_id;
    }

    public void setGroupId(String group_id) {
        this.group_id = group_id;
    }

    @Override
    public void setBackgroundColor(int color) {
        this.color = color;
        gradient_drawable.setColor(color);
        setBackground(gradient_drawable);
    }



    public int getColor(){
        return color;
    }

    public void setCornerRadius(float radius){
        gradient_drawable.setCornerRadius(radius);
        setBackground(gradient_drawable);
    }

    /**
     *
     * @param border set the width
     * @param color set the border color
     */
    public void setBorder(int border,int color){
        gradient_drawable.setStroke(border,color);
        setBackground(gradient_drawable);
    }


    public void setBackgroundImage(int border, Drawable image){
        GradientDrawable frame = gradient_drawable;
        frame.setColor(0x000000ff);
        Drawable[] layers = {image,frame};
        LayerDrawable result = new LayerDrawable(layers);
        bg_image = image;
        setBackground(result);
    }

    public Drawable getBackgroundImage(){
        return bg_image;
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setPosition(IDNDPager.Coordinates coordinates) {
        x = coordinates.getPositionX();
        y = coordinates.getPositionY();
    }

    @Override
    public int getPositionX() {
        return x;
    }

    @Override
    public int getPositionY() {
        return y;
    }

    @Override
    public void onDrag() {

    }

    @Override
    public void onDrop() {

    }



}
