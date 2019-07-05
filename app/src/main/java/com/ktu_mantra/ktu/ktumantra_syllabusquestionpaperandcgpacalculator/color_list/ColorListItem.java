package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.UIManager;

public class ColorListItem extends FrameLayout implements View.OnClickListener{

    private ColorInterface colorInterface;
    private View viewBackground;
    private View viewPoint;
    private View viewborder;
    private Button buttonColor;

    private int gap;
    private int colorPosition;
    private int color;
    private int fontColor;


    public ColorListItem(Context context) {
        super(context);
        colorListItem(context);
    }

    public void colorListItem(Context context){
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.color_list_item, this, true);
        viewBackground = findViewById(R.id.background_view);
        viewPoint = findViewById(R.id.point_view);
        viewborder = findViewById(R.id.border_view);
        buttonColor = (Button) findViewById(R.id.button_color);
        gap= UIManager.staticObject().pixelToDp(2);
        buttonColor.setOnClickListener(this);
    }

    public void setColor(int position){

        colorPosition=position;
        color=ColorCollection.colorDailog(colorPosition);
        fontColor=ColorCollection.colorAlpha(colorPosition);
        GradientDrawable gradientDrawable = (GradientDrawable)viewBackground.getBackground();
        gradientDrawable.setColor(color);
        gradientDrawable.setStroke(gap, color);
        ((GradientDrawable) viewPoint.getBackground()).setColor(fontColor);
        gradientDrawable = (GradientDrawable) viewborder.getBackground();
        gradientDrawable.setColor(ColorCollection.defaultColor);
        gradientDrawable.setStroke(gap, ColorCollection.defaultStrokeColor);
    }

    public int getColor() {
        return color;
    }

    public void setSelected(boolean isSelected) {
        if (isSelected) {
            viewPoint.setVisibility(VISIBLE);
        } else {
            viewPoint.setVisibility(INVISIBLE);
        }
    }

    public void setOnColorItemClickListener(ColorInterface colorInterface){
        this.colorInterface=colorInterface;

    }

    @Override
    public void onClick(View v) {
        if (colorInterface != null) {

            colorInterface.setColorPosition(colorPosition);
        }

    }



}
