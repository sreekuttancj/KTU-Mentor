package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


public class VerticalScrollView extends ScrollView {

    ScrollView scrollView=null;



    public VerticalScrollView(Context context) {
        super(context);

    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);



    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);


            UIManager.staticObject().setHeight(b - t);



    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (motionEvent.getAction() == MotionEvent.ACTION_UP && scrollView != null) {
            scrollView.scrollTo(0, computeVerticalScrollOffset());
        }
        return true;
    }

}
