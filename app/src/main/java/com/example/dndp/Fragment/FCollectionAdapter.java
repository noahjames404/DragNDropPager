package com.example.dndp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dndp.DND.DNDButton;
import com.example.dndp.DND.DNDItem;
import com.example.dndp.DND.IDNDPager;

import java.util.List;

public class FCollectionAdapter extends FragmentStatePagerAdapter {

    IDNDPager.AutoSwipe auto_swipe;
    List<DNDItem> item_list;

    public FCollectionAdapter(@NonNull FragmentManager fm,List<DNDItem> item_list, IDNDPager.AutoSwipe auto_swipe) {
        super(fm);
        this.auto_swipe = auto_swipe;
        this.item_list = item_list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        FPage fragment = new FPage(position,auto_swipe);
        Bundle bundle = new Bundle();
        position++;
        bundle.putString("message","hello from pages : "+ position);
        fragment.setArguments(bundle);
        fragment.setItemList(item_list);


        return fragment;
    }

    @Override
    public int getCount() {
        return item_list.size();
    }


}
