package com.example.dndp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class DNDButton extends Button {

    private int cell_width, cell_height;

    private String group_id;

    private RelativeLayout layout;

    public DNDButton(Context context) {
        super(context);
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

    public ViewGroup.MarginLayoutParams getParams(){
       ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
       return params;
    }
}
