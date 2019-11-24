package com.example.dndp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dndp.DND.DNDButton;
import com.example.dndp.DND.DNDPager;
import com.example.dndp.DND.DNDUtils;
import com.example.dragndroppager.R;

/**
 * Default fragment used to customize views inside the DNDPager.
 */
public class FCustomizePanel extends DialogFragment {

    private DNDButton btn;
    private DNDPager pager;
    private EditText et_image, et_height, et_width,et_text;
    private Button btn_confirm,btn_browse;
    private Spinner spinner;

    public static FCustomizePanel getInstance(DNDButton btn){
        FCustomizePanel fragment = new FCustomizePanel();
        fragment.btn = btn;
        fragment.pager = btn.getLastPager();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customize_item,container);
        et_image = view.findViewById(R.id.et_image);
        et_height = view.findViewById(R.id.et_height);
        et_width = view.findViewById(R.id.et_width);
        et_text = view.findViewById(R.id.et_text);

        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_browse = view.findViewById(R.id.btn_browse);

        generateColorPalettes(view);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        final int default_cell_height = btn.getCellHeightRatio();
        final int default_cell_width = btn.getCellWidthRatio();

        et_height.setText(String.valueOf(default_cell_height));
        et_width.setText(String.valueOf(default_cell_width));
        et_text.setText(btn.getText());
        spinner.setSelection(getColorIndex(btn.getColor()));

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * initialize the default fields
                 */
                int cell_height = Integer.parseInt(et_height.getText().toString());
                int cell_width = Integer.parseInt(et_width.getText().toString());
                /**
                 * update button grid position & size
                 */
                ViewGroup.MarginLayoutParams params =  pager.setGridPosition(cell_width,cell_height,btn.getPositionX(),btn.getPositionY());
                btn.setCellHeightRatio(cell_height);
                btn.setCellWidthRatio(cell_width);
                /**
                 * validate changes
                 */
                DNDPager.MESSAGE message = pager.checkItem(params,btn);
                switch (message){
                    case SAVED:
                        /**
                         * changes are valid
                         */
                        btn.setBackgroundColor(Color.parseColor(DNDUtils.color_palette[spinner.getSelectedItemPosition()][1]));
                        btn.setLayoutParams(params);
                        Toast.makeText(getContext(),"saved",Toast.LENGTH_SHORT).show();
                        break;
                    case OVERLAPPED:
                        /**
                         * Error: button overlaps another button, return previous changes to default.
                         */
                        Toast.makeText(getContext(),"Button overlaps another button",Toast.LENGTH_SHORT).show();
                        btn.setCellHeightRatio(default_cell_height);
                        btn.setCellWidthRatio(default_cell_width);
                        break;
                    case OUT_OF_BOUNDS:
                        /**
                         * Error: Button is out of the parent layout, return previous changes to default.
                         */
                        Toast.makeText(getContext(),"Button is out of bounds",Toast.LENGTH_SHORT).show();
                        btn.setCellHeightRatio(default_cell_height);
                        btn.setCellWidthRatio(default_cell_width);
                        break;
                }

                dismiss();
            }
        });

    }

    /**
     * generates default color palettes
     * @param view
     */
    public void generateColorPalettes(View view){
        //get the spinner from the xml.
        spinner = view.findViewById(R.id.spinner_color_palette);
        //create a list of items for the spinner.

        String[] items = new String[DNDUtils.color_palette.length];

        for(int i = 0; i < DNDUtils.color_palette.length; i++){
            items[i] = DNDUtils.color_palette[i][0];
        }


        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);
    }

    /**
     * converts hex value to color palettes base index
     * @param color - hex to convert
     * @return color palette index. returns -1 only if the hex does not exist in the existing palette
     */
    private int getColorIndex(int color){
        String hex_color = "#" + Integer.toHexString(color).substring(2);
        for(int i = 0; i < DNDUtils.color_palette.length; i++){
            if(DNDUtils.color_palette[i][1].equals(hex_color)){
                return i;
            }
        }
        return -1;
    }


}
