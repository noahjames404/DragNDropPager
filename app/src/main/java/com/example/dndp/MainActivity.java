package com.example.dndp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.dndp.DND.DNDButton;
import com.example.dndp.DND.DNDItem;
import com.example.dndp.DND.DNDPager;
import com.example.dndp.DND.IDNDPager;
import com.example.dndp.Fragment.FCollectionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MAINACTIVITY";
    private ViewPager view_pager;
    private FCollectionAdapter adapter;
    final List<DNDItem> item_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String group_id = "power";

        view_pager = findViewById(R.id.view_pager);


        IDNDPager.AutoSwipe auto_swipe = new IDNDPager.AutoSwipe() {
            @Override
            public void onSwipeLeft() {
                Log.d(TAG, "onSwipeLeft: ");
                view_pager.setCurrentItem(view_pager.getCurrentItem() - 1, true);
            }

            @Override
            public void onSwipeRight() {
                Log.d(TAG, "onSwipeRight: ");
                view_pager.setCurrentItem(view_pager.getCurrentItem() + 1, true);

            }
        };


        item_list.add(new DNDItem("hello",2,2,1,1,null,Color.parseColor("#000fff"),1));
        item_list.add(new DNDItem("hello",2,2,2,3,null,Color.parseColor("#000fff"),-1));
        item_list.add(new DNDItem("hello",2,2,2,3,null,Color.parseColor("#000fff"),-1));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));
        item_list.add(new DNDItem("hello",null,Color.parseColor("#000fff")));



        adapter = new FCollectionAdapter(getSupportFragmentManager(),item_list,auto_swipe);
        view_pager.setAdapter(adapter);

        view_pager.setOffscreenPageLimit(1000);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                adapter.setEditable(true);
            }
        },10000);

    }

    private FragmentTransaction getFragmentTransaction(){
        return getSupportFragmentManager().beginTransaction();
    }



    
}
