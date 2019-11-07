package com.example.dndp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dndp.DND.DNDButton;
import com.example.dndp.DND.DNDPager;

public class FCustomizePanel extends DialogFragment {

    private DNDButton btn;
    private DNDPager pager;
    private EditText et_color, et_image, et_height, et_width;
    private Button btn_confirm,btn_browse;
    /**
     * used for validating the bounds of a layout.
     */
    public static FCustomizePanel getInstance(DNDPager pager, DNDButton btn){
        FCustomizePanel fragment = new FCustomizePanel();
        fragment.btn = btn;
        fragment.pager = pager;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customize_item,container);
        et_color = view.findViewById(R.id.et_color);
        et_image = view.findViewById(R.id.et_image);
        et_height = view.findViewById(R.id.et_height);
        et_width = view.findViewById(R.id.et_width);

        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_browse = view.findViewById(R.id.btn_browse);





        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        final int default_cell_height = btn.getCellHeightRatio();
        final int default_cell_width = btn.getCellWidthRatio();
        et_color.setText("#"+Integer.toHexString(btn.getColor()));
        et_height.setText(String.valueOf(default_cell_height));
        et_width.setText(String.valueOf(default_cell_width));


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cell_height = Integer.parseInt(et_height.getText().toString());
                int cell_width = Integer.parseInt(et_width.getText().toString());
                DNDButton temp_btn = new DNDButton(getContext());

                ViewGroup.MarginLayoutParams params =  pager.setGridPosition(cell_width,cell_height,btn.getPositionX(),btn.getPositionY());
                btn.setCellHeightRatio(cell_height);
                btn.setCellWidthRatio(cell_width);
                DNDPager.MESSAGE message = pager.checkItem(params,btn);
                switch (message){
                    case SAVED:
                        btn.setBackgroundColor(Color.parseColor(et_color.getText().toString()));


                        btn.setLayoutParams(params);
                        Toast.makeText(getContext(),"saved",Toast.LENGTH_SHORT).show();
                        break;
                    case OVERLAPPED:
                        Toast.makeText(getContext(),"Button overlaps another button",Toast.LENGTH_SHORT).show();
                        btn.setCellHeightRatio(default_cell_height);
                        btn.setCellWidthRatio(default_cell_width);
                        break;
                    case OUT_OF_BOUNDS:
                        Toast.makeText(getContext(),"Button is out of bounds",Toast.LENGTH_SHORT).show();
                        btn.setCellHeightRatio(default_cell_height);
                        btn.setCellWidthRatio(default_cell_width);
                        break;
                }

                dismiss();
            }
        });

    }


}
