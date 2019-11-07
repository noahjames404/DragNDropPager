package com.example.dndp.DND;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public interface IDNDPager {
    void onSizeChange(double width, double height);

    interface Coordinates {
        void setPosition(int x,int y);

        void setPosition(Coordinates coordinates);

        int getPositionX();

        int getPositionY();
    }

    interface ItemEvent {
        void onDrag();

        void onDrop();
    }

    interface Item extends Coordinates {
        int getCellWidthRatio();

        void setCellWidthRatio(int cell_width);

        int getCellHeightRatio();

        void setCellHeightRatio(int cell_height);

        void setLastLayout(RelativeLayout layout);

        RelativeLayout getLastLayout();

        String getGroupId();

        void setGroupId(String group_id);

    }

    interface CustomItem {
        void setItem(Item i);

        Item getItem();
    }

    interface ActionEvent {
        void onExecute();
    }

    interface ItemView {
        /**
         *
         * @param pager - use only to validate
         * @param view - to be customize
         * @return
         */
        View onCustomize(DNDPager pager,View view);

        View onExecute();

        void onResponse(DNDPager.MESSAGE message);
    }


}
