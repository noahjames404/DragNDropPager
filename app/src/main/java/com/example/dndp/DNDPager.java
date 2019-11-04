package com.example.dndp;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Debug;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DNDPager {


    public static final String TAG ="DNDPager";
    Context context;
    /**
     * The number of rows & columns from each page to support.
     * When using multiple DNDPager with the same group id, ensure that
     * row_num & col_num values are the same from each class.
     */
    private int row_num, col_num;
    /**
     * The size of the layout, this is updated using updateLayoutSize(layout,IDNDPager)
     * */
    private double layout_width, layout_height;
    /**
     * This variables depends on row num & col num & layout size.
     */
    private double cell_width, cell_height;

    /**
     * The layout to be populated.
     * */
    private RelativeLayout layout;

    /**
     * The current_drag_view is the current view being drag, this can also be pass from
     * one layout to another, the same principle applies with drop_shadow_view;
     */
    private static View current_drag_view,drop_shadow_view;

    /**
       draggable views are only allowed to migrate to another layout
       if it has the same group_id
     */
    private String group_id;

    /**
     * The margin in width & height of a button, this avoids overlapping buttons in close sections.
     */
    private double margin_percentage = 0.05;


    /**
     * background color of the layout, since the layout's background color would be override by the grid snap view.
     */
    private int background_color = Color.WHITE;

    /**
        @param layout items are populated in this layout.
     */
    public DNDPager(RelativeLayout layout, final int row_num, final int col_num, String group_id,Context context){
        this.layout = layout;
        this.context = context;
        this.row_num = row_num;
        this.col_num = col_num;
        this.group_id = group_id;
    }

    /**
     * Changes are applied and start to load the views.
     */
    public void render(){
        updateLayoutSize(layout, new IDNDPager() {
            @Override
            public void onSizeChange(double width, double height) {
                layout_height = height;
                layout_width = width;
                cell_height = getCellSize(row_num,height);
                cell_width = getCellSize(col_num,width);

                generateSnapGrid();
                generateButton(1,1,0,0);
                generateButton(1,1,1,0);
                generateButton(1,1,2,0);
            }
        });
    }

    /**
        populate the layout with snap views,
        this are hidden from the user's vision.
     */

    private void generateSnapGrid(){

        for(int y =0; y < row_num; y++)
            for (int x = 0; x < col_num; x++) {
                DNDSnapView snap_view = new DNDSnapView(context);
                snap_view.setLayout(layout);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        (int)cell_width,
                        (int)cell_height
                );
                int x_margin =(int)(x * cell_width);
                int y_margin =(int)(y * cell_height);
                params.setMargins(x_margin, y_margin,0,0);
                /**
                    guide only
                 */
//                snap_view.setBackgroundColor((y + x) % 2== 0? Color.parseColor("#74b9ff"): Color.parseColor("#0984e3"));
                snap_view.setBackgroundColor(background_color);
                snap_view.setLayoutParams(params);
                snap_view.setPosition(x,y);

                snap_view.setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View view, DragEvent dragEvent) {
                        ColorDrawable bg_color = (ColorDrawable)view.getBackground();
                        ViewGroup.MarginLayoutParams vlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                        DNDSnapView snap_view = (DNDSnapView) view;
                        RelativeLayout current_layout;

                        DNDButton btn = (DNDButton) current_drag_view;

                        switch (dragEvent.getAction()){
                            case DragEvent.ACTION_DRAG_STARTED:
                                current_layout = snap_view.getLayout();
                                btn.getBackground().setAlpha(100);
                                break;
                            case DragEvent.ACTION_DRAG_ENTERED:

                                drop_shadow_view = new View(context);
                                current_layout = snap_view.getLayout();
                                RelativeLayout.LayoutParams shadow_params = new RelativeLayout.LayoutParams(current_drag_view.getWidth(),current_drag_view.getHeight());
                                shadow_params.setMargins(vlp.leftMargin,vlp.topMargin,0,0);
                                drop_shadow_view.setLayoutParams(shadow_params);

                                drop_shadow_view.setBackgroundColor(colorContrast(bg_color.getColor(),0.8f));
                                current_layout.addView(drop_shadow_view);

                                break;
                            case DragEvent.ACTION_DRAG_EXITED:

                                drop_shadow_view.setBackgroundColor(background_color);
                                break;
                            case DragEvent.ACTION_DRAG_LOCATION:
                                break;
                            case DragEvent.ACTION_DROP:
                                //check if view is outside of bound
                                if(vlp.leftMargin + current_drag_view.getWidth() > layout_width
                                        ||
                                   vlp.topMargin + current_drag_view.getHeight() > layout_height){
                                    return false;
                                }
                                //check if button group id is equal to layout's group id.
                                if(!group_id.equals(btn.getGroupId())){
                                    return false;
                                }
                                if(hasOverlapView(vlp,btn)){
                                    return false;
                                }

                                btn.getLastLayout().removeView(current_drag_view );
                                drop_shadow_view.setBackgroundColor(background_color);
                                view.setBackgroundColor(background_color);
                                generateButton(btn.getCellWidthRatio(),btn.getCellHeightRatio(),snap_view.getPositionX(),snap_view.getPositionY());
                                btn.setLastLayout(layout);
                                break;

                            case DragEvent.ACTION_DRAG_ENDED:
                                btn.getBackground().setAlpha(255);
                                drop_shadow_view.setBackgroundColor(background_color);


                                break;

                        }
                        return true;
                    }
                });
                layout.addView(snap_view);
                Log.d(TAG, "generateSnapGrid: ");
            }
    }

    /**
     * computes the cell length base on the number of cells available in a layout_size
     * @param cell_count - the number of cells to fit in layout_size
     * @param layout_size - the length of a layout e.g height or width
     * @return the cell_size
     */
    private double getCellSize(int cell_count, double layout_size){
        return layout_size / cell_count;
    }

    /**
        Listens to layout size change.
        This method also avoid receiving 0 width & height on initial load of the layout.
        @param layout - safely takes the layout size.
        @param callback - called when size changes.
     */
    private void updateLayoutSize(final RelativeLayout layout, final IDNDPager callback){

        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                } else {
                    layout.getViewTreeObserver()
                            .removeGlobalOnLayoutListener(this);
                }

                double width  = layout.getMeasuredWidth();
                double height = layout.getMeasuredHeight();

                if(width != 0 && height != 0){

                    callback.onSizeChange(width, height);
                }
            }
        });
    }

    /**
     * Creates a DNDButton to be viewed in the layout.
     * @param width_ratio - the ratio is base on the layout_width
     * @param height_ratio - the ratio is base on the layout_height
     * @param x - coordinates in the layout this is base on the snap gridview (the col_num).
     * @param y - coordinates in the layout this is base on the snap gridview (the row_num).
     */
    public void generateButton(final int width_ratio,final int height_ratio,int x, int y){
        final DNDButton btn = new DNDButton(context);

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent me) {
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(btn);

                ClipData.Item item = null;

                item = new ClipData.Item( "" + v.getId());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData data = new ClipData(
                        btn.toString(),
                        mimeTypes,
                        item
                );
                current_drag_view = v;


                if (me.getAction() == MotionEvent.ACTION_MOVE  ){
                    v.startDrag(data,shadowBuilder,null,0);
                }

                return true;
            }
        });

        btn.setBackgroundColor(Color.parseColor("#000fff"));
        btn.setGroupId(group_id);
        btn.setLastLayout(layout);
        btn.setCellWidthRatio(width_ratio);
        btn.setCellHeightRatio(height_ratio);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(width_ratio * cell_width),(int)(height_ratio * cell_height));
        params.setMargins((int)(x * cell_width),(int)(y * cell_height),0,0);
        btn.setLayoutParams(params);

        layout.addView(btn);
    }

    /**
     * @param color - set the background color of the layout.
     *                Take Note: this is only applied before render()
     */
    public void setBackgroundColor(int color){
        background_color = color;
    }


    /**
     *
     * @param color - the color to be adjust
     * @param factor - ranges from 0.8 to 1.0f to set the contrast
     * @return the contrasted value.
     */
    public int colorContrast(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

    /**
     * determines if the element overlaps to other elements
     * @param params - the button to be check.
     * @return true if it has overlap.
     */
    public boolean hasOverlapView(ViewGroup.MarginLayoutParams params, DNDButton btn){

       for(DNDButton b : getButtons()){
            if(
                    b.getParams().leftMargin < params.leftMargin + cell_width * btn.getCellWidthRatio()
                    &&
                    getButtonFullWidth(b) > params.leftMargin
                    &&
                    b.getParams().topMargin < params.topMargin + cell_height * btn.getCellHeightRatio()
                    &&
                    getButtonFullHeight(b) > params.topMargin
            ){
                return true;
            }
       }
       return false;
    }


    /**
     * Takes the full width of a button base on the cell_width.
     * @param btn
     * @return returns the full size
     */
    private double getButtonFullWidth(DNDButton btn){
        return btn.getParams().leftMargin + cell_width * btn.getCellWidthRatio();
    }

    /**
     * Takes the full height of a button base on the cell_height.
     * @param btn
     * @return returns the full size
     */
    private double getButtonFullHeight(DNDButton btn){
        return btn.getParams().topMargin + cell_height * btn.getCellWidthRatio();
    }

    /**
     * Takes all buttons inside a layout.
     * @return list of buttons
     */
    public List<DNDButton> getButtons(){
        List<DNDButton> btn = new ArrayList<>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if(child instanceof DNDButton){
                btn.add((DNDButton) child);
            }
        }
        Log.d(TAG, "getButtons: " + btn.size());
        return btn;
    }

}
