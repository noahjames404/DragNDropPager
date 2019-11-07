package com.example.dndp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.dndp.DND.DNDButton;
import com.example.dndp.DND.DNDPager;
import com.example.dndp.DND.IDNDPager;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rl,rl_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl = findViewById(R.id.rl);
        rl_1 = findViewById(R.id.rl_1);
        String group_id = "power";



        IDNDPager.ItemView event = new IDNDPager.ItemView() {
            @Override
            public View onCustomize(DNDPager pager, View view) {
                DNDButton btn = (DNDButton) view;
                FCustomizePanel
                        .getInstance(pager,btn)
                        .show(getFragmentTransaction(),"ewqewq");

                Log.d("power", "onCustomize: " + pager.getInvalidColor());
                return null;
            }

            @Override
            public View onExecute() {
                return null;
            }

            @Override
            public void onResponse(DNDPager.MESSAGE message) {

            }

        };



        DNDPager pager = new DNDPager(rl,6,6,group_id,getApplicationContext());
        pager.setBackgroundColor(Color.parseColor("#fff000"));
        pager.render();
        pager.setEditable(true);
        pager.setOnCustomize(event);

        DNDPager pager1 = new DNDPager(rl_1,6,6,group_id,getApplicationContext());
        pager1.render();
        pager1.setEditable(true);
        pager1.setOnCustomize(event);
        pager1.setInvalidColor(Color.parseColor("#000fff"));
    }

    private FragmentTransaction getFragmentTransaction(){
        return getSupportFragmentManager().beginTransaction();
    }
}
