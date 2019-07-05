package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list.ColorCollection;

import java.util.ArrayList;
import java.util.List;


public class ClassView extends View {

    private UIManager uiManager;
    private HTLesson htLesson;

    private static final int backColor;
    private static final int previousSubBackColor;

    private List f5235D;
    private List f5236E;
    private boolean isViewSelected;
    private  int state;

    private int indWidth;
    private int indHeight;
    private float indTextHeight;

    float gap;
    float halfGap;

    int period;
    int day;
    int noOfBlock;
    String subName;
    int l;
    int r;
    boolean changeColor;

    private Paint line;
    private TextPaint textPaint;
    private List subNameList;

    private Context mContext;

    private int textColor;

    static {
        backColor = Color.argb(0, 0, 0, 0);
        previousSubBackColor=Color.argb(255, 110, 110, 110);
    }

    public ClassView(Context context) {
        super(context);

        uiManager = UIManager.staticObject();
        state = 0;

        isViewSelected = false;
        changeColor = false;
        noOfBlock=0;
        this.f5235D = new ArrayList();
        this.f5236E = new ArrayList();


        indHeight=uiManager.indHeight();
        indWidth= (int) (uiManager.indvidualDayWidth()-gap);

        m5168a(context);


    }

    public ClassView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void m5168a(Context context){

        mContext=context;
//        setBackgroundColor(getResources().getColor(R.color.bar_button_blue));

        gap=uiManager.gap();
        halfGap=gap *0.5f;

        line = new Paint(1);
        line.setStrokeWidth(gap);
        line.setARGB(255, 80, 80, 80);

        textPaint=new TextPaint();
        textPaint.setColor(Color.parseColor("#ffffff"));
        textPaint.setFlags(1);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sub_name_font_size));
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));

        indTextHeight= textPaint.descent()-textPaint.ascent();
        subNameList=new ArrayList();
    }


    public void setLR(int l,int r){

        this.l=l;
        this.r=r;
    }

    public void setDay(int day) {
        this.day = day;
    }


    public void setPeriod(int period) {
        this.period = period;
    }

    public void setSubject(HTLesson hTLesson) {

        this.htLesson=hTLesson;
        if (hTLesson == null) {
            setBackgroundColor(backColor);
        } else if (noOfBlock> 0) {
            Log.i("setsubback","name:"+String.valueOf(htLesson.subName)+"color"+String.valueOf(htLesson.color)+"block:"+String.valueOf(noOfBlock));
            setBackgroundColor(hTLesson.color);
        }


    }


    public void setNumBlocks(int noOfBlock){

        this.noOfBlock=noOfBlock;

        if (noOfBlock>0){
            indHeight=uiManager.indHeight()*noOfBlock;
            layout(l,r,l+indWidth,r+indHeight);
            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            layoutParams.height = indHeight;
            setLayoutParams(layoutParams);
            setVisibility(VISIBLE);
            if (htLesson != null) {
                setBackgroundColor(htLesson.color);
            }
        } else {
            setBackgroundColor(Color.argb(0, 0, 0, 0));
            setVisibility(INVISIBLE);
        }

    }



    public int getDay() {
        return this.day;
    }

    public int getPeriod() {
        return this.period;
    }

    public HTLesson getSubject() {
        return htLesson;
    }


    public int getNumBlocks() {
        return noOfBlock;
    }

    public int getState(){
        return state;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int i=0;
        float textPosX=indWidth/2;
        int viewState=uiManager.getVewState();
        if (viewState==0){
            if (noOfBlock>0){

                if (changeColor) {

                    canvas.drawColor(ColorCollection.onClickAlpha);
                }
                else if (isViewSelected){
                    canvas.drawColor(ColorCollection.onClickDefault);

                }

                canvas.drawLine(0.0f, indHeight-halfGap, indWidth, indHeight-halfGap, line);
                if (state!=2&&htLesson!=null){
                    subNameList.clear();
                    subName=htLesson.subName.trim();
                    textPaint.setColor(htLesson.fontColor);
                    canvas.drawText(subName, textPosX, (indHeight / 2), textPaint);



                }


            }
        }
        else if (viewState==1||viewState==2){
//            subName=htLesson.subName.trim();

            textPaint.setColor(textColor);
            canvas.drawLine(0.0f,indHeight-halfGap, indWidth,  indHeight-halfGap, line);


        }

    }

    public void subStored(){

        state=2;
        setBackgroundColor(previousSubBackColor);
    }



    public boolean checkSubject(HTLesson htLesson){
        if (this.htLesson!=null&&this.htLesson.lessonId==htLesson.lessonId){
            return true;
        }
        return false;
    }

    public void setViewIsSelected(boolean viewIsSelected){
        this.isViewSelected=viewIsSelected;
        invalidate();
    }

    public void setSubView(boolean check) {
        state = 0;
        if (noOfBlock > 0) {
            indHeight = uiManager.indHeight() * noOfBlock;
            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            layoutParams.height = indHeight;
            setLayoutParams(layoutParams);
            setVisibility(VISIBLE);
            if (htLesson == null) {
                setBackgroundColor(Color.argb(0, 0, 0, 0));
                return;
            } else {
                setBackgroundColor(htLesson.color);
                return;
            }
        }
        setBackgroundColor(Color.argb(0, 0, 0, 0));
        setVisibility(INVISIBLE);
    }

    public void setSubViewStored(boolean check) {
        state = 0;
        if (noOfBlock> 0) {
           indHeight= uiManager.indHeight() * noOfBlock;
            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            layoutParams.height = indHeight;
            setLayoutParams(layoutParams);
            setVisibility(VISIBLE);
            if (htLesson == null) {
                setBackgroundColor(Color.argb(0, 0, 0, 0));
                return;
            } else {
                setBackgroundColor(htLesson.color);
                return;
            }
        }
        setBackgroundColor(Color.argb(0, 0, 0, 0));
        setVisibility(INVISIBLE);
    }


    public void setViewProperty(){
        if (state==1){
            setBackgroundColor(uiManager.setViewBackColor());
            textColor=uiManager.setViewTextColor();
            invalidate();
        }
    }

    public void setViewForEditSub() {
        indHeight = uiManager.indHeight();
        state = 1;
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        layoutParams.height = indHeight;
        setLayoutParams(layoutParams);
        setVisibility(VISIBLE);
        if (htLesson != null) {
            setBackgroundColor(htLesson.color);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);


        switch (uiManager.getVewState()) {

            case 0:

                if (htLesson != null) {
                    if (action != MotionEvent.ACTION_DOWN) {
                        if (action != MotionEvent.ACTION_UP) {
                            if (action == MotionEvent.ACTION_CANCEL) {
                                changeColor = false;
                                invalidate();
                                break;
                            }
                        }
                        else {
                            changeColor = false;
                            uiManager.enableSubClick(htLesson);
                            invalidate();
                        }


                        break;
                    }
                    changeColor = true;
                    invalidate();
                    break;
                }
                break;

            case 1:
            if (state != 2 &&action == MotionEvent.ACTION_UP) {
                if (state != 0) {
                    if (state == 1) {
                        state = 0;
                        setBackgroundColor(Color.argb(0, 0, 0, 0));

                        break;
                    }
                }
                state = 1;
                setBackgroundColor(uiManager.setViewBackColor());
                break;
            }
            break;

            case 2:

                if (this.state != 2 && action== MotionEvent.ACTION_UP) {
                    if (state != 0) {
                        if (state == 1) {
                            state = 0;
                            setBackgroundColor(Color.argb(0, 0, 0, 0));
                            textColor=0;
                            break;
                        }
                    }
                    state = 1;
                    setBackgroundColor(uiManager.setViewBackColor());
                    textColor=uiManager.setViewTextColor();
                    break;
                }
                break;
        }
        return true;
    }




    public void refreshViewAfterDelete(){
        htLesson=null;
        setNumBlocks(1);
        setBackgroundColor(Color.argb(0, 0, 0, 0));
        textColor=0;
        invalidate();

    }



}
