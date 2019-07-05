package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.Locale;

public class TopAxisView extends View{

    private UIManager htuiManager;


    public static final int dayColor = Color.argb(255, 250, 250, 0);
    public static final int dayBackGround = Color.argb(255, 85, 85, 85);
//    public static final int dayBackGround = Color.parseColor("#107896");

    private String[] weekname = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};



    private Paint today;
    private Paint days;
    private Paint verticalLine;
    private Paint remLines;


    private int screenWidth;
    private float dayWidth;
    private float remWidth;
    private float gap;
    private float yGap;
    private float lineGap;
    private int cDay;

    private int startDay;

    private float topAxisHeight;
    private float yPos;

    private boolean check;

    public TopAxisView(Context context, AttributeSet attrs) {
        super(context, attrs);

        htuiManager=UIManager.staticObject();
        htuiManager.m5249a(context);

        setBackgroundColor(dayBackGround);
        today = new Paint(1);
        today = new Paint(1);
        today.setColor(dayColor);
        today.setTextAlign(Paint.Align.CENTER);
        today.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.day_font_size));

        days = new Paint(1);
        days.setColor(-1);
        days.setTextAlign(Paint.Align.CENTER);
        days.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.day_font_size));

        verticalLine = new Paint();
        verticalLine.setARGB(255, 60, 60, 60);

        this.remLines = new Paint();
        this.remLines.setARGB(255, 60, 60, 60);


    }


    public void setValues(Context context){

        screenWidth=context.getResources().getDisplayMetrics().widthPixels;
        gap=htuiManager.gap();
        yGap=htuiManager.yGap();
        lineGap=gap*0.5f;
        topAxisHeight=htuiManager.topAxis();

        dayWidth=htuiManager.indvidualDayWidth();
        remWidth=htuiManager.remainingWidth();

        verticalLine.setStrokeWidth(gap);
        remLines.setStrokeWidth(yGap);

        startDay=0;
        yPos = ((topAxisHeight * 0.5f) + days.descent()) + ((float) htuiManager.pixelToDp(2));
        currentDay();
        check = true;

    }

    public void currentDay(){

        cDay=Calendar.getInstance(Locale.getDefault()).get(Calendar.DAY_OF_WEEK) - 1;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (check) {

            //top
            canvas.drawLine(0.0f,gap,(float)screenWidth,gap,remLines);
            //bottom
            canvas.drawLine(0.0f,topAxisHeight-gap,(float)screenWidth,topAxisHeight-gap,remLines);
            //left
            canvas.drawLine(gap,yGap,gap,topAxisHeight-yGap,remLines);
            //2left
            canvas.drawLine(remWidth-gap,yGap,remWidth-gap,topAxisHeight-yGap,remLines);
            //right
            canvas.drawLine((float) screenWidth-gap,yGap,(float) screenWidth-gap,topAxisHeight-yGap,remLines);
        }


        //draw vertical line btw days

        for (int i=0;i<htuiManager.daysTotal;i++){

            float startX=remWidth+((float)i*dayWidth)-lineGap;
            canvas.drawLine(startX,yGap,startX,topAxisHeight-yGap,verticalLine);

        }

        float extra = ((dayWidth - gap) * 0.5f) + remWidth;

        //draw week_day
        for (int i=0;i<htuiManager.daysTotal;i++){

                float xPos = (dayWidth * ((float) i)) + extra;
                String day=weekname[i];

                if ((cDay-1)==i){
                    canvas.drawText(day, xPos, yPos, today);

                }
            else {
                    canvas.drawText(day, xPos, yPos, days);

                }
            }



    }





}
