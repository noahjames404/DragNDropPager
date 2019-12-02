package com.example.dndp.DND;

import android.view.View;

/**
 * contains all callback interface used all over the library.
 */
public interface IDNDPager {
    void onSizeChange(double width, double height);

    interface Coordinates {
        void setPosition(int x, int y);

        void setPosition(Coordinates coordinates);

        int getPositionX();

        int getPositionY();
    }

    interface Item extends Coordinates {
        int getCellWidthRatio();

        void setCellWidthRatio(int cell_width);

        int getCellHeightRatio();

        void setCellHeightRatio(int cell_height);

        void setLastPager(DNDPager layout);

        DNDPager getLastPager();

        String getGroupId();

        void setGroupId(String group_id);

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
        void onCustomize(DNDPager pager, View view);
    }

    interface AutoSwipe {
        void onSwipeLeft();
        void onSwipeRight();
    }

    interface SettingsPreference {
        boolean isEditable();
    }




}
