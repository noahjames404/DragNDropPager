package com.example.dndp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dndp.DND.IDNDPager;

public class FCollectionAdapter extends FragmentStatePagerAdapter {

    IDNDPager.AutoSwipe auto_swipe;
    public FCollectionAdapter(@NonNull FragmentManager fm, IDNDPager.AutoSwipe auto_swipe) {
        super(fm);
        this.auto_swipe = auto_swipe;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        FPage fragment = new FPage(auto_swipe);
        Bundle bundle = new Bundle();
        position++;
        bundle.putString("message","hello from pages : "+ position);
        fragment.setArguments(bundle);


        return fragment;
    }

    @Override
    public int getCount() {
        return 100;
    }
}
