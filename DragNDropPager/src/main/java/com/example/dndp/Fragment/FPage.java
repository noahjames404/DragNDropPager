package com.example.dndp.Fragment;


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
import com.example.dragndroppager.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FPage extends Fragment {

    public static final String TAG = "FPAGE";
    protected List<DNDItem> item_list = new ArrayList<>();
    protected int swipe_buffer = 500;
    protected static long recent_swipe = new Date().getTime();
    protected RelativeLayout rl_grid;
    protected IDNDPager.AutoSwipe auto_swipe;
    protected View left_bound, right_bound;
    protected DNDPager pager;
    protected int page_num = -1;
    protected IDNDPager.ActionEvent post_action;
    protected IDNDPager.SettingsPreference settingsPreference;
    protected IDNDPager.ItemView event = null;
    protected String group_id = "";
    protected int row_num = -1, col_num = -1;
    protected IDNDPager.OnChangeLocationListener on_change_location;
    protected IDNDPager.OnButtonPreInit button_pre_init;

    public FPage(int page_num, IDNDPager.AutoSwipe auto_swipe,int row_num, int col_num, String group_id, IDNDPager.SettingsPreference settingsPreference) {
        this.auto_swipe = auto_swipe;
        this.page_num = page_num;
        this.settingsPreference = settingsPreference;
        this.group_id = group_id;
        this.row_num = row_num;
        this.col_num = col_num;
    }

    /**
     * empty constructor is required when switching orientation
     */
    public FPage(){

    }

    /**
     * modify the button before rendering to layout
     * @param button_pre_init - called on generateButton() method
     */
    public void setOnButtonPreInit(IDNDPager.OnButtonPreInit button_pre_init){
        this.button_pre_init = button_pre_init;
    }

    /**
     * get the current pager of the Fragment
     * @return
     */
    public DNDPager getPager(){
        return pager;
    }

    /**
     * define the general customize fragment to all buttons inside the layout.
     * @param event
     */
    public void setCustomizeFragment(IDNDPager.ItemView event){
        this.event = event;
    }

    /**
     * set the group of DNDItems to be applied on DNDPager
     * @param item_list
     */
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



        pager = new DNDPager(rl_grid,row_num,col_num,group_id,getContext());
        pager.setPageNum(page_num);
        pager.setIsEditable(settingsPreference);
        pager.setOnChangeLocationListener(on_change_location);
        pager.setOnButtonPreInit(button_pre_init);


        pager.setOnCustomize(new IDNDPager.ItemView() {
            @Override
            public void onCustomize(DNDPager pager, View view) {
                Log.d(TAG, "onCustomize: is nulll");
                if(event == null){

                    DNDButton btn = (DNDButton) view;
                    FCustomizePanel
                            .getInstance(btn)
                            .show(getActivity().getSupportFragmentManager(),"customized");
                }else {
                    event.onCustomize(pager,view);
                }
            }
        });

        pager.render(new IDNDPager.ActionEvent() {
            @Override
            public void onExecute() {

                pager.addButtonToLayout(item_list);
                if(post_action != null){
                    post_action.onExecute();
                }
            }
        });
    }

    /**
     * callback when draggable view's location is change or moved to another layout
     * @param event
     */
    public void setOnChangeLocationListener(IDNDPager.OnChangeLocationListener event){
        on_change_location = event;
    }

    /**
     * execute after all views are rendered in DNDPager
     * @param post_action
     */
    public void setPostAction(IDNDPager.ActionEvent post_action){
        this.post_action = post_action;
    }
}
