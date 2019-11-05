package com.example.dndp;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

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
     * The margin in width & height of a button, use for checking the sizes in buttons.
     * a percentage is ignored when an element overlaps another element.
     */
    private double margin_percentage = 0.05;


    /**
     * background color of the layout, since the layout's background color would be override by the grid snap view.
     */
    private int background_color = Color.WHITE;

    private DNDButton button_to_swap;

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
                generateButton(1,1,0,0).setBackgroundColor(Color.BLACK);
                generateButton(1,1,1,0);
                generateButton(1,1,2,0);
                generateButton(1,1,3,0);

                generateButton(1,1,0,1);
                generateButton(1,1,1,1);
                generateButton(1,1,2,1);
                generateButton(1,1,3,1);
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
                final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
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
                                //check if it overlaps other views (buttons)
                                if(hasOverlapView(vlp,btn)){
                                    //this section checks if it is able to switch places with another button.
                                    //check if both buttons has the same ratio stored
                                    if(hasTheSameRatio(snap_view.getStoredButton(),btn)){
                                        //check if both buttons are in the same layout
                                        if(btn.getLastLayout() == layout){
                                           //switch
                                            DNDSnapView previous_snap = btn.getStoreSnapView();
                                            previous_snap.getStoredButton().setText("wowww");
//                                            generateButton(btn.getCellWidthRatio(),btn.getCellHeightRatio(),
//                                                    previous_snap.getPositionX(),previous_snap.getPositionY()).setBackgroundColor( snap_view.getStoredButton().getColor());

                                            RelativeLayout.LayoutParams new_pos = setGridPosition(btn.getCellWidthRatio(),btn.getCellHeightRatio(),
                                                    snap_view.getPositionX(),snap_view.getPositionY());

                                            RelativeLayout.LayoutParams old_pos = setGridPosition(btn.getCellWidthRatio(),btn.getCellHeightRatio(),
                                                    previous_snap.getPositionX(),previous_snap.getPositionY());

                                            btn.setLayoutParams(new_pos);
                                            button_to_swap.setLayoutParams(old_pos);
                                            button_to_swap.setText("power");

                                            btn.setText("wowwwwwwwww");
//                                            previous_snap.getStoredButton().setLayoutParams(new_pos);
//                                            layout.removeView(snap_view.getStoredButton());
                                            Log.d(TAG, "onDrag: is stored button null " + (snap_view.getStoredButton() == null));
                                            Log.d(TAG, "onDrag: switching coordinates " + previous_snap.getPositionX() + "-"+ previous_snap.getPositionY());
                                            Log.d(TAG, "onDrag: switching coordinates " + snap_view.getStoredButton().getStoreSnapView().getPositionX() + "-"+ snap_view.getStoredButton().getStoreSnapView().getPositionY());

                                        }else {
                                            return false;
                                        }
                                    }
                                    else {
                                        return false;
                                    }
                                }else {
                                    btn.getLastLayout().removeView(current_drag_view );
                                    drop_shadow_view.setBackgroundColor(background_color);
                                    view.setBackgroundColor(background_color);
                                    generateButton(btn.getCellWidthRatio(),btn.getCellHeightRatio(),snap_view.getPositionX(),snap_view.getPositionY())
                                            .setBackgroundColor(btn.getColor());

                                    btn.setLastLayout(layout);
                                    btn.getBackground().setAlpha(255);
                                    snap_view.setStoredButton(btn);
                                    btn.setStoreSnapView(snap_view);
                                }








                                break;

                            case DragEvent.ACTION_DRAG_ENDED:
                                btn.getBackground().setAlpha(255);
                                layout.removeView(drop_shadow_view);
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
    private int getCellSize(int cell_count, double layout_size){
        return (int)layout_size / cell_count;
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
    public DNDButton generateButton(final int width_ratio,final int height_ratio,final int x,final int y, DNDButton custom_btn){
        final DNDButton btn = custom_btn != null ? custom_btn : new DNDButton(context);

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

                //search pair
                if(btn.getStoreSnapView() == null){
                    List<DNDSnapView> snap_list =  getSnapViews();
                    for(DNDSnapView snap : snap_list ){
                        if(snap.getPositionX() == x && snap.getPositionY() == y){
                            btn.setStoreSnapView(snap);
                        }
                    }
                }

                if (me.getAction() == MotionEvent.ACTION_MOVE  ){
                    v.startDrag(data,shadowBuilder,null,0);
                }

                return true;
            }
        });

        btn.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()){
                    case DragEvent.ACTION_DRAG_ENTERED :
                        Log.d(TAG, "onDrag: entered");
                        button_to_swap = btn;
                        break;
                }
                return true;
            }
        });



        btn.setBackgroundColor(Color.GRAY);
        btn.setGroupId(group_id);
        btn.setLastLayout(layout);

        btn.setBorder(5,background_color);




        btn.setCellWidthRatio(width_ratio);
        btn.setCellHeightRatio(height_ratio);

        btn.setLayoutParams(setGridPosition(width_ratio,height_ratio,x,y));

        layout.addView(btn);
        return btn;
    }

    public DNDButton generateButton(final int width_ratio,final int height_ratio,int x, int y){
        return generateButton(width_ratio,height_ratio,x,y,null);
    }


    public RelativeLayout.LayoutParams setGridPosition(double width_ratio,double height_ratio,int x, int y){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(width_ratio * cell_width),(int)(height_ratio * cell_height));
        params.setMargins((int)(x * cell_width),(int)(y * cell_height),0,0);
        return params;
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

        Log.d(TAG, "hasOverlapView: shrink size" + shrinkSize(params.leftMargin + cell_width * btn.getCellWidthRatio(),margin_percentage));
        Log.d(TAG, "hasOverlapView: actual size " + params.leftMargin + cell_width * btn.getCellWidthRatio());
       for(DNDButton b : getButtons()){
            if(     b != btn &&
                    expandSize(b.getParams().leftMargin,margin_percentage)< params.leftMargin + cell_width * btn.getCellWidthRatio()
                    &&
                    shrinkSize(getButtonFullWidth(b),margin_percentage)> params.leftMargin
                    &&
                    expandSize(b.getParams().topMargin,margin_percentage)< params.topMargin + cell_height * btn.getCellHeightRatio()
                    &&
                    shrinkSize(getButtonFullHeight(b),margin_percentage)> params.topMargin

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

    /**
     * Takes all snapviews inside a layout.
     * @return list of snap_views
     */
    public List<DNDSnapView> getSnapViews(){
        List<DNDSnapView> btn = new ArrayList<>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if(child instanceof DNDSnapView){
                btn.add((DNDSnapView) child);
            }
        }
        Log.d(TAG, "getSnapViews: " + btn.size());
        return btn;
    }

    /**
     * shrink the size base on percentage
     * @param size - target value
     * @param percentage - works effectively with range 0.0 to 1.0f;
     * @return shrink size
     */
    public double shrinkSize(double size, double percentage){
        Log.d(TAG, "shrinkSize: " + size + " " + percentage);
        return size - size * percentage;
    }

    /**
     * expand the size base on percentage
     * @param size - target value
     * @param percentage - works effectively with range 0.0 to 1.0f;
     * @return
     */
    public double expandSize(double size, double percentage){
        Log.d(TAG, "shrinkSize: " + size + " " + percentage);
        return size + size * percentage;
    }

    public boolean hasTheSameRatio(DNDButton btn1, DNDButton btn2){
        if(btn1.getCellHeightRatio() == btn2.getCellHeightRatio()
        && btn1.getCellWidthRatio() == btn2.getCellWidthRatio()){
            return true;
        }
        return false;
    }

}
