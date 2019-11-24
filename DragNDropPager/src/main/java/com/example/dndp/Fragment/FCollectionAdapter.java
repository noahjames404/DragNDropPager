package com.example.dndp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dndp.DND.DNDItem;
import com.example.dndp.DND.IDNDPager;

import java.util.List;

public class FCollectionAdapter extends FragmentPagerAdapter {

    public static String TAG ="FCollectionAdapter";
    IDNDPager.AutoSwipe auto_swipe;
    List<DNDItem> item_list;
    int page_count = 1;
    View.OnClickListener btn_listener;
    boolean is_editable = false;
    IDNDPager.ItemView event;
    String group_id = "";
    int row_num, col_num;


    /**
     *
     * @param fm - use support fragment manager
     * @param row_num - number of rows of each page
     * @param col_num - number of columns of each page
     * @param item_list - DNDItems list to be applied in the view_pager
     * @param auto_swipe - callback method, use DNDUtils.defaultAutoSwipe().
     */
    public FCollectionAdapter(@NonNull FragmentManager fm, int row_num, int col_num, List<DNDItem> item_list, IDNDPager.AutoSwipe auto_swipe) {
        super(fm);
        this.auto_swipe = auto_swipe;
        this.item_list = item_list;
        this.row_num = row_num;
        this.col_num = col_num;
    }

    /**
     *
     * @param fm - use support fragment manager
     * @param row_num - number of rows of each page
     * @param col_num - number of columns of each page
     * @param item_list - DNDItems list to be applied in the view_pager
     * @param group_id - group id of each page
     * @param auto_swipe - callback method, use DNDUtils.defaultAutoSwipe().
     */
    public FCollectionAdapter(@NonNull FragmentManager fm, int row_num, int col_num,  List<DNDItem> item_list, String group_id, IDNDPager.AutoSwipe auto_swipe) {
        super(fm);
        this.auto_swipe = auto_swipe;
        this.item_list = item_list;
        this.group_id = group_id;
        this.row_num = row_num;
        this.col_num = col_num;
    }

    /**
     * assign a listener from each page.
     * @param btn_listener
     */
    public void setOnClickBtnListener(View.OnClickListener btn_listener){
        this.btn_listener = btn_listener;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        final FPage fragment = new FPage(position, auto_swipe,row_num,col_num,group_id, new IDNDPager.SettingsPreference() {
            @Override
            public boolean isEditable() {
                return is_editable;
            }
        });
        Bundle bundle = new Bundle();
        position++;
        bundle.putString("message","hello from pages : "+ position);
        fragment.setArguments(bundle);
        fragment.setItemList(item_list);
        fragment.setCustomizeFragment(event);
        fragment.setPostAction(new IDNDPager.ActionEvent() {
            @Override
            public void onExecute() {
                hasRemainingItems();
                fragment.getPager().setOnClickBtnListener(btn_listener);
            }
        });

        return fragment;
    }

    public void setEditable(final boolean is_editable){
        this.is_editable = is_editable;
    }


    public void setCustomizeFragment(IDNDPager.ItemView event){
        this.event = event;
    }

    @Override
    public int getCount() {
        return page_count;
    }

    public void setCount(int page_count){
        this.page_count= page_count;
        notifyDataSetChanged();
    }

    private void hasRemainingItems(){
        for(DNDItem item : item_list){
            if(!item.is_added){
                setCount(getCount()+1);
                return;
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return System.currentTimeMillis();
    }

}
