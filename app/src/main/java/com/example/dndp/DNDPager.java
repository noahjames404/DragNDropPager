package com.example.dndp;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DNDPager {


    Context context;
    /**
     * The number of rows & columns from each page to support.
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
        @param layout items are populated in this layout.
     */
    public void DNDPager(RelativeLayout layout, final int row_num, final int col_num,Context context){

        this.layout = layout;
        this.context = context;
        updateLayoutSize(layout, new IDNDPager() {
            @Override
            public void onSizeChange(double width, double height) {
                layout_height = height;
                layout_width = width;
                cell_height = getCellSize(row_num,height);
                cell_width = getCellSize(col_num,width);

                generateSnapGrid();
            }
        });
    }

    /**
        populate the layout with snap views,
        this are hidden from the user's vision.
     */
    public void generateSnapGrid(){
        for(int y =0; y < row_num; y++)
            for (int x = 0; x < col_num; x++) {
                View snap_view = new View(context);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        (int)cell_width,
                        (int)cell_height
                );
                int x_margin =(int)(x * cell_width);
                int y_margin =(int)(y * cell_height);
                params.setMargins(x_margin, y_margin,0,0);
                snap_view.setBackgroundColor(Color.parseColor("#000fff"));
            }
    }

    /**
     * computes the cell length base on the number of cells available in a layout_size
     * @param cell_count - the number of cells to fit in layout_size
     * @param layout_size - the length of a layout e.g height or width
     * @return the cell_size
     */
    public double getCellSize(int cell_count, double layout_size){
        return layout_size / cell_count;
    }

    /**
        Listens to layout size change.
        This method also avoid receiving 0 width & height on initial load of the layout.
     */
    public void updateLayoutSize(final RelativeLayout layout, final IDNDPager callback){

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
}
