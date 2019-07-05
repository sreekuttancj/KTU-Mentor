package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.UIManager;

public class ColorListDialog extends Dialog implements ColorInterface{

    ImageButton imageButtonClose;
    int state;
    public ColorListDialog(Context context,int state) {
        super(context);

        this.state=state;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.color_list_dialog);

        imageButtonClose= (ImageButton) findViewById((R.id.button_close));

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.color_button_base);
        Context context = getContext();

        int viewSize = UIManager.staticObject().pixelToDp(44, context);
        int margin = UIManager.staticObject().pixelToDp(4, context);

        for (int i = 0; i < 32; i++) {
            int i2 = i / 6;
            int i3 = i - (i2 * 6);
            ColorListItem colorListItem = new ColorListItem(context);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(viewSize, viewSize);
            layoutParams.setMargins(i3 * (viewSize + margin), i2 * (viewSize + margin), 0, 0);
            layoutParams.gravity = 48;
            colorListItem.setLayoutParams(layoutParams);
            colorListItem.setColor(i);
            colorListItem.setOnColorItemClickListener(this);
            if (i==state){
                colorListItem.setSelected(true);
            }
            else {
                colorListItem.setSelected(false);

            }
            frameLayout.addView(colorListItem);

        }




        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void setColorPosition(int position) {
        UIManager.staticObject().setPositionCall(position);
        dismiss();
    }
}
