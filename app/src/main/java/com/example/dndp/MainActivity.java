package com.example.dndp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout rl,rl_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl = findViewById(R.id.rl);
        rl_1 = findViewById(R.id.rl_1);
        String group_id = "power";

        DNDPager pager = new DNDPager(rl,2,4,group_id,getApplicationContext());
        pager.setBackgroundColor(Color.parseColor("#fff000"));
        pager.render();

        DNDPager pager1 = new DNDPager(rl_1,6,6,group_id +"dsa",getApplicationContext());
        pager1.render();

    }
}
