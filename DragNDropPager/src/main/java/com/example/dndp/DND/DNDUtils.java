package com.example.dndp.DND;

import android.graphics.Bitmap;
import android.os.Environment;

import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class DNDUtils {

    public static final String[][] color_palette = new String[][]{
            {"GREEN","#2ecc71"},
            {"BLUE","#3498db"},
            {"VIOLET","#9b59b6"},
            {"BLACK","#34495e"},
            {"YELLOW","#f1c40f"},
            {"ORANGE","#e67e22"},
            {"GREEN","#2ecc71"},
            {"RED","#e74c3c"},
            {"GREY","#95a5a6"}
    };


    /**
     * updates the is_added properties to false
     * @param item
     */
    public static void resetItems(List<DNDItem> item){
        for(DNDItem i : item){
            i.is_added = false;
        }
    }

    /**
     * generates an autoswipe callback for View Pagers
     * @param view_pager
     * @return
     */
    public static IDNDPager.AutoSwipe defaultAutoSwipe(final ViewPager view_pager){
        return new IDNDPager.AutoSwipe() {
            @Override
            public void onSwipeLeft() {
                view_pager.setCurrentItem(view_pager.getCurrentItem() - 1, true);
            }

            @Override
            public void onSwipeRight() {
                view_pager.setCurrentItem(view_pager.getCurrentItem() + 1, true);

            }
        };
    }

    /**
     * quick sort, short code
     * @param list_item
     */
    public static void sortItems(List<DNDItem> list_item){
        Collections.sort(list_item, new DNDItemComparator());
    }

    /**
     * sort items by page number
     */
    public static class DNDItemComparator implements Comparator<DNDItem> {

        @Override
        public int compare(DNDItem t1, DNDItem t2) {
            return t2.page_num - t1.page_num;
        }
    }



}
