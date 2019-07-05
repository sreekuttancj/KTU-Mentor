package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class DateView extends LinearLayout {

    Context mContext;

    private TextView week;
    private TextView date;


    public DateView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_date_view, this, true);
        getDate(context);
    }

    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_date_view, this, true);
        getDate(context);
    }
    public void getDate()
    {
        Calendar localCalendar = Calendar.getInstance();
        String str1 = java.text.DateFormat.getDateInstance(1, Locale.getDefault()).format(localCalendar.getTime());
        String str2 = android.text.format.DateFormat.format("EEEE", localCalendar).toString();
        this.week.setText(str1);
        this.date.setText(str2);
    }

    private void getDate(Context paramContext)
    {
        mContext=paramContext;

        this.week = ((TextView)findViewById(R.id.text_view_weekday));
        this.date = ((TextView)findViewById(R.id.text_view_date));

        getDate();
    }

}
