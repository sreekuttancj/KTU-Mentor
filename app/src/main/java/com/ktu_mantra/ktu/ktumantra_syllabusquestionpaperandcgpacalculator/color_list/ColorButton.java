package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.UIManager;

public class ColorButton extends FrameLayout {

    TextView textViewColor;
//    ColorButton colorButton;
    public ColorButton(Context context) {
        super(context);
        colorButton(context);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        colorButton(context);
    }

    public void colorButton(Context context){

        ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.color_button, this, true);
        textViewColor= (TextView) findViewById(R.id.text_view_color);
//        colorButton= (ColorButton)
            findViewById(R.id.button_change_color).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

              UIManager.staticObject().showColorList();
            }
        });
    }

    public void setSubjectColor(int paramInt)
    {
        ((GradientDrawable)textViewColor.getBackground()).setColor(paramInt);
    }

    public void setTextColor(int paramInt)
    {
        textViewColor.setTextColor(paramInt);
    }

}
