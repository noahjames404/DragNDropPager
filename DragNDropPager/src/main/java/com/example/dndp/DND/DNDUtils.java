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
     * Converts a immutable bitmap to a mutable bitmap. This operation doesn't allocates
     * more memory that there is already allocated.
     *
     * @param imgIn - Source image. It will be released, and should not be used more
     * @return a copy of imgIn, but muttable.
     */
    public Bitmap convertBitmapToMutable(Bitmap imgIn) {
        try {
            //this is the file going to use temporally to save the bytes.
            // This file will not be a image, it will store the raw image data.
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temp.tmp");

            //Open an RandomAccessFile
            //Make sure you have added uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
            //into AndroidManifest.xml file
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

            // get the width and height of the source bitmap.
            int width = imgIn.getWidth();
            int height = imgIn.getHeight();
            Bitmap.Config type = imgIn.getConfig();

            //Copy the byte to the file
            //Assume source bitmap loaded using options.inPreferredConfig = Config.ARGB_8888;
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, imgIn.getRowBytes()*height);
            imgIn.copyPixelsToBuffer(map);
            //recycle the source bitmap, this will be no longer used.
            imgIn.recycle();
            System.gc();// try to force the bytes from the imgIn to be released

            //Create a new bitmap to load the bitmap again. Probably the memory will be available.
            imgIn = Bitmap.createBitmap(width, height, type);
            map.position(0);
            //load it back from temporary
            imgIn.copyPixelsFromBuffer(map);
            //close the temporary file and channel , then delete that also
            channel.close();
            randomAccessFile.close();

            // delete the temp file
            file.delete();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imgIn;
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
