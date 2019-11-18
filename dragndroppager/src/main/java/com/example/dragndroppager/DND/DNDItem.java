package com.example.dragndroppager.DND;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class DNDItem {

    public int cell_height_ratio = 1,cell_width_ratio =1;
    /**
     * coordiantes
     */
    public int x = -1,y = -1;

    public String text = "";

    public DNDButton btn;

    public Drawable background_image = null;
    public int background_color = Color.parseColor(DNDUtils.color_palette[0][1]);
    public int page_num = -1;
    public boolean is_added = false;

    public DNDItem(){

    }

    public void validatedProperties(){
        if(is_added && btn != null){
            this.cell_height_ratio = btn.getCellHeightRatio();
            this.cell_width_ratio = btn.getCellWidthRatio();
            this.x = btn.getPositionX();
            this.y = btn.getPositionY();
            this.text = btn.getText().toString();
            this.background_image = btn.getBackgroundImage();
            this.background_color = btn.getColor();
            this.page_num = btn.getLastPager().getPageNum();
        }
    }

    public DNDItem(String text, Drawable background_image, int background_color) {
        this.cell_height_ratio = cell_height_ratio;
        this.cell_width_ratio = cell_width_ratio;
        this.text = text;
        this.background_image = background_image;
        this.background_color = background_color;
    }

    public DNDItem(String text,int cell_height_ratio, int cell_width_ratio, int x, int y,  Drawable background_image, int background_color, int page_num) {
        this.cell_height_ratio = cell_height_ratio;
        this.cell_width_ratio = cell_width_ratio;
        this.x = x;
        this.y = y;
        this.text = text;
        this.background_image = background_image;
        this.background_color = background_color;
        this.page_num = page_num;
    }

    //    public DNDButton generateButton(final int width_ratio, final int height_ratio, final int x, final int y, String btn_text, int btnbg_color, Drawable btnbg_image){
}
