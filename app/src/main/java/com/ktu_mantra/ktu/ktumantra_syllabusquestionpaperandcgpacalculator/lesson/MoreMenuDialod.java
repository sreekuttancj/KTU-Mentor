package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.lesson;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.SettingsActivity;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.TimetableActivity;


public class MoreMenuDialod extends Dialog implements View.OnClickListener,HtDialogInterface{

    TimetableActivity timetableActivity;
    public MoreMenuDialod(Context context) {
        super(context);
    }

        protected void onCreate(Bundle paramBundle)
        {
            super.onCreate(paramBundle);
            requestWindowFeature(1);
            getWindow().setBackgroundDrawable(new ColorDrawable(0));
            setCanceledOnTouchOutside(false);
            setContentView(R.layout.dialog_more_menu);

            ((Button)findViewById(R.id.button_share)).setOnClickListener(this);
            ((Button)findViewById(R.id.button_settings)).setOnClickListener(this);
            ((ImageButton)findViewById(R.id.button_close)).setOnClickListener(this);


        }



    public void setMainActivity(TimetableActivity timetableActivity){
        this.timetableActivity=timetableActivity;
    }

@Override
    public void onClick(View v) {

       switch (v.getId()){
           case R.id.button_share:

               timetableActivity.screenShotShare(timetableActivity.rootView);
               break;
           case R.id.button_settings:
               Intent intentSettings=new Intent(getContext(), SettingsActivity.class);
               getContext().startActivity(intentSettings);
               break;
           case R.id.button_close:
               dismiss();
               break;
       }
    }

    @Override
    public void setClickAction(HTDialog htDialog, int paramInt) {

    }


}
