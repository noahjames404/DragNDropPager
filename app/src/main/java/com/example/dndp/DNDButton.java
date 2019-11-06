package com.example.dndp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PictureDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
@SuppressLint("AppCompatCustomView")
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class DNDButton extends Button implements IDNDPager.Item, IDNDPager.ItemEvent {
    private int cell_width, cell_height;

    private String group_id;

    private RelativeLayout layout;

    private GradientDrawable gradient_drawable;

    private int color;

    private int x,y;

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

    public void setLastLayout(RelativeLayout layout){
        this.layout = layout;
    }

    public RelativeLayout getLastLayout(){
        return layout;
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


    public void setImage(int border,Drawable image){
        GradientDrawable frame = gradient_drawable;
        frame.setColor(0x000000ff);
        Drawable[] layers = {image,frame};
        LayerDrawable result = new LayerDrawable(layers);
        setBackground(result);
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
