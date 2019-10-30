package com.example.dndp;

import android.widget.Button;
import android.widget.LinearLayout;

public class DNDPagerItem {
    /**
     * The button tobe used in the layout.
     */
    private Button btn;
    /**
     * the position of the button in the layout.
     */
    private int position;

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
