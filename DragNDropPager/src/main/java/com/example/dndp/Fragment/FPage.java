package com.example.dndp.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.example.dndp.DND.DNDButton;
import com.example.dndp.DND.DNDItem;
import com.example.dndp.DND.DNDPager;
import com.example.dndp.DND.IDNDPager;
import com.example.dndp.FCustomizePanel;
import com.example.dndp.R;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FPage extends Fragment {


    List<DNDItem> item_list;
    int swipe_buffer = 500;
    static long recent_swipe = new Date().getTime();
    RelativeLayout rl_grid;
    IDNDPager.AutoSwipe auto_swipe;
    View left_bound, right_bound;
    DNDPager pager;
    private int page_num = -1;
    private IDNDPager.ActionEvent post_action;
    private IDNDPager.SettingsPreference settingsPreference;
    private IDNDPager.ItemView event = null;
    private String group_id = "";
    public FPage(int page_num, IDNDPager.AutoSwipe auto_swipe, String group_id, IDNDPager.SettingsPreference settingsPreference) {
        this.auto_swipe = auto_swipe;
        this.page_num = page_num;
        this.settingsPreference = settingsPreference;
        this.group_id = group_id;
    }

    public DNDPager getPager(){
        return pager;
    }

    public void setCustomizeFragment(IDNDPager.ItemView event){
        this.event = event;
    }



    public void setItemList(List<DNDItem> item_list) {
        this.item_list = item_list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_page, container, false);
        rl_grid = view.findViewById(R.id.rl_grid);
        left_bound = view.findViewById(R.id.left_bound);
        right_bound = view.findViewById(R.id.right_bound);

        init();

        return view;
    }


    private void init(){



        left_bound.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                if(DragEvent.ACTION_DRAG_ENTERED == dragEvent.getAction() && recent_swipe + swipe_buffer < new Date().getTime()){
                    recent_swipe =  new Date().getTime();
                    auto_swipe.onSwipeLeft();
                }

                return true;
            }
        });

        right_bound.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                if(DragEvent.ACTION_DRAG_ENTERED == dragEvent.getAction() && recent_swipe + swipe_buffer < new Date().getTime()){
                    recent_swipe =  new Date().getTime();
                    auto_swipe.onSwipeRight();
                }

                return true;
            }
        });



        pager = new DNDPager(rl_grid,6,6,group_id,getContext());
        pager.setPageNum(page_num);
        pager.setIsEditable(settingsPreference);
        if(event == null){
            event = new IDNDPager.ItemView() {
                @Override
                public View onCustomize(DNDPager pager, View view) {
                    DNDButton btn = (DNDButton) view;
                    FCustomizePanel
                            .getInstance(btn)
                            .show(getActivity().getSupportFragmentManager(),"customized");
                    return null;
                }
            };
        }
        pager.setOnCustomize(event);
        pager.updateLayoutSize(rl_grid, new IDNDPager() {
            @Override
            public void onSizeChange(double width, double height) {
                for(DNDItem item : item_list){
                    pager.addButtonToLayout(item, page_num);
                    if(post_action != null){
                        post_action.onExecute();
                    }
                }
            }
        });
    }
    public void addButtonToLayout(DNDItem item, int page_num){
        pager.addButtonToLayout(item,page_num);
    }

    public void setPostAction(IDNDPager.ActionEvent post_action){
        this.post_action = post_action;
    }
}
