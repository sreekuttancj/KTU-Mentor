package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.Preferencemanager;

import java.lang.reflect.Array;


public class ClassMain extends FrameLayout {

    public static int MAXNUM;
    //Display the week
    public static int WEEKNUM;

    Context mContext;
    private float gap;
    private float yGap;
    private float halfGap;
    private float remWidth;
    private float dayWidth;
    private boolean f5072F;

    private ClassView[][] lessons;
    private ClassView classView;

    private View frameView;


    private int maximumHeight;
    private int indvidualViewheight;


    private Paint verticalLine;
    private Paint periodHorLine;
    private Paint periodText;

    private Paint verticalLineLight;


    Preferencemanager preferencemanager;
    private UIManager htuiManager;


    int periodTextColor = Color.argb(255, 34, 34, 34);

    public ClassMain(Context context) {
        super(context);


    }

    public ClassMain(Context context, AttributeSet attrs) {
        super(context, attrs);

        preferencemanager=new Preferencemanager(context);

        MAXNUM=Integer.parseInt(preferencemanager.getPeriod());
        WEEKNUM=Integer.parseInt(preferencemanager.getWeak());

        htuiManager=UIManager.staticObject();
        remWidth=htuiManager.remainingWidth();
        dayWidth=htuiManager.indvidualDayWidth();

        f5072F = false;

        yGap=htuiManager.yGap();
        gap = yGap * 0.5f;
        halfGap=gap * 0.5f;



        verticalLine = new Paint();
        verticalLine.setARGB(255, 80, 80, 80);
        verticalLine.setStrokeWidth(yGap);

        verticalLineLight=new Paint();
        verticalLineLight.setARGB(255, 80, 80, 80);
        verticalLineLight.setStrokeWidth(gap);

        periodHorLine = new Paint();
        periodHorLine.setARGB(255, 80, 80, 80);
        periodHorLine.setStrokeWidth(gap);


        periodText = new Paint(1);
        periodText.setColor(periodTextColor);
        periodText.setTextAlign(Paint.Align.CENTER);
        periodText.setTextSize(getResources().getDimension(R.dimen.period_text));
        periodText.setTypeface(Typeface.create(Typeface.DEFAULT, 1));

        LayoutParams layoutParams = new LayoutParams(10, 10);
        frameView=new View(context);
        frameView.setLayoutParams(layoutParams);
        addView(frameView);



    }

    public void init(Context context){

        mContext=context;
        f5072F = true;
        indvidualViewheight=htuiManager.indHeight();
        maximumHeight= (int) ((indvidualViewheight*MAXNUM)+gap);

        setViewHeight(maximumHeight);




    }

    public void onDraw(Canvas canvas) {

        int check=1;

        if (f5072F) {

            canvas.drawLine(gap, 0.0f, gap, maximumHeight, verticalLine);
            canvas.drawLine(remWidth - gap, 0.0f, remWidth - gap, maximumHeight, verticalLine);
            canvas.drawLine(UIManager.verticalEndLinePos, 0.0f, UIManager.verticalEndLinePos, maximumHeight, verticalLine);
            canvas.drawLine(0.0f, maximumHeight-gap, getWidth(), maximumHeight-gap, verticalLine);


            for (int j = 0; j < MAXNUM; j++) {

                float temp = ((float) (indvidualViewheight * j)) - halfGap;

                canvas.drawLine(yGap, temp, ((float) remWidth) - yGap, temp, periodHorLine);


            }

            float yPos = ((indvidualViewheight - gap) * 0.5f) + ((periodText.descent() - periodText.ascent()) * 0.5f) - periodText.descent();

            for (int k = 1; k <= MAXNUM; k++) {

                float temp = (indvidualViewheight * (k - 1)) + yPos;
                canvas.drawText(String.valueOf(k), (remWidth * 0.5f), temp, periodText);

            }

            while (check<WEEKNUM){

                float temp1=(remWidth+(dayWidth * check))-halfGap;
                canvas.drawLine(temp1,0.0f,temp1,maximumHeight,verticalLineLight);
                check++;
            }

        }

    }

    public void setViewHeight(int i) {
        if (getHeight() != i) {
            setLayoutParams(new LayoutParams(htuiManager.maxScreenWidth(), i));
            frameView.setLayoutParams(new LayoutParams(htuiManager.maxScreenWidth(), i));
        }
    }



    public void setClassmain(){

        int dayWidth=htuiManager.indvidualDayWidth();
        int gap=htuiManager.gap();
        int indHeigth=htuiManager.indHeight();
        int remWidth=htuiManager.remainingWidth();
        final int firstDay=2;

        if (lessons!=null){


            for (int i=0;i<lessons.length;i++){
                for(View view:lessons[i]){


                    ((ViewGroup) view.getParent()).removeView(view);


                }
            }
        }

        lessons = null;
        lessons = (ClassView[][]) Array.newInstance(ClassView.class, new int[]{WEEKNUM, MAXNUM});
        float f = 1.0f;


        for (int i=0;i<WEEKNUM;i++){

            int firstPeriod=0;
            for (int j=0;j<MAXNUM;j++){

                classView=new ClassView(mContext);

                LayoutParams layoutParams = new LayoutParams(dayWidth - gap, indHeigth);
                layoutParams.setMargins((dayWidth * i) + remWidth, indHeigth * j, 0, 0);
                layoutParams.gravity = Gravity.TOP;
                classView.setLayoutParams(layoutParams);
                classView.setPeriod(firstPeriod);
                classView.setDay(firstDay + i);
                classView.setNumBlocks(1);
                classView.setLR((dayWidth * WEEKNUM) + remWidth, indHeigth * MAXNUM);
                addView(classView);
                lessons[i][j] = classView;
                firstPeriod += f;


            }
        }
    }


    public void subStored(){
        for(int i=0;i<lessons.length;i++){
            for (ClassView classView:lessons[i]){
                if (classView.getSubject()!=null){
                    classView.subStored();
                }
            }
        }
    }



    public void setLesson(HTLesson htLesson){
        int id;
        int i,j;
        ClassView classView;
        int day;
        int period;
        if (htuiManager.getVewState()==1) {
            id = htLesson.lessonId;

            for ( i = 0; i < lessons.length; i++) {
                for (j = 0; j < lessons[i].length; j++) {
                    if (lessons[i][j].getState()==1) {

                        classView = lessons[i][j];
                        day = classView.day;
                        period = classView.period;
                        htLesson.addPostions(day,period);
                        htuiManager.setPosition(day, period, id);
                        classView.setSubject(htLesson);
                    }
                }
            }
        }
        else if (htuiManager.getVewState()==2) {

            id = htLesson.lessonId;
            for (i = 0; i < lessons.length; i++) {
                for (ClassView classView2 : lessons[i]) {

                    if (classView2.getState() == 0) {
                        classView2.setSubject(null);
                    } else if (classView2.getState() == 1) {
                        day = classView2.getDay();
                        period = classView2.getPeriod();
                        htLesson.addPostions(day, period);
                        htuiManager.setPosition(day, period, id);
                        classView2.setSubject(htLesson);

                    }

                }
            }
        }

    }

    public void setLesson(boolean check,HTLesson htLesson){

        int i;
        ClassView classView;
        if (!check || htLesson == null) {
            for (int i2 = 0; i2 < lessons.length; i2++) {
                for (ClassView classView2 : lessons[i2]) {
                    classView2.setSubView(false);
                }
            }
            return;
        }

        for (i = 0; i < lessons.length; i++) {
            classView = null;
            for (ClassView classView3 : lessons[i]) {
                if (classView3.getState() == 1 && htLesson.checkPosition( classView3.getDay(), classView3.getPeriod())) {
                    if (classView == null) {
                        classView3.setNumBlocks(1);
                        classView = classView3;
                    }else if (htLesson.checkPosition(classView3.getDay(),classView3.getPeriod()-1)){
                        classView.setNumBlocks(classView.getNumBlocks() + 1);
                        classView3.setNumBlocks(0);

                    }
                    else {
                        classView3.setNumBlocks(1);
                        classView = classView3;
                    }
                }
                classView3.setSubView(true);
            }
        }


    }


    public void setStoredLesson(boolean check, HTLesson hTLesson) {
        int i;
        ClassView classView2=null;
        if (!check || hTLesson == null) {
            for (int i2 = 0; i2 < lessons.length; i2++) {
                for (ClassView classView : lessons[i2]) {

                    classView.setSubViewStored(false);
                }
            }
            return;
        }
      
        for (i = 0; i < lessons.length; i++) {

            for (ClassView ClassView : lessons[i]) {
                if (ClassView.getState() == 0) {
                    ClassView.setNumBlocks(1);
                }
                if (ClassView.getState() == 1 && hTLesson.checkPosition(ClassView.getDay(), ClassView.getPeriod())) {
                    if (classView2 == null) {
                        ClassView.setNumBlocks(1);
                        classView2 = ClassView;
                    }
                       else if (hTLesson.checkPosition(ClassView.getDay(), ClassView.getPeriod()-1)) {
                            classView2.setNumBlocks(classView2.getNumBlocks() + 1);
                            ClassView.setNumBlocks(0);
                        }
                    else {
                        ClassView.setNumBlocks(1);
                        classView2 = ClassView;
                    }

                }
                ClassView.setSubViewStored(true);
            }
        }
    }

    public void setLessonsFromDb() {
        setClassmain();
        HTLesson[] htLessonArr = htuiManager.getSubInfoFromDb();
        if (htLessonArr != null) {
            Log.i("htarraylength",String.valueOf(htLessonArr.length));

            int period = 1;
            for (HTLesson hTLesson : htLessonArr) {
                for (int i = 0; i < lessons.length; i++) {
                    ClassView classView = null;
                    int i2 = 0;
                    while (i2 < lessons[i].length) {
                        ClassView classView2 = lessons[i][i2];
                        if (hTLesson.checkPosition(classView2.getDay(), classView2.getPeriod())) {

                            Log.i("positionlesson","position:"+"["+String.valueOf(i)+"]["+String.valueOf(i2)+"]");

                            classView2.setSubject(hTLesson);
                            if (classView!=null&&hTLesson.checkPosition(classView2.getDay(), classView2.getPeriod()-period)){
                                Log.i("getblock",String.valueOf(classView.getNumBlocks()));
                                classView.setNumBlocks(classView.getNumBlocks()+1);
                                classView2.setNumBlocks(0);
                                classView2 = classView;
                                Log.i("checkagain","checkagain");
                            }



                        } else {
                            Log.i("positionlesson","position false:"+"["+String.valueOf(i)+"]["+String.valueOf(i2)+"]");

                            classView2 = classView;
                        }
                        i2++;
                        classView = classView2;
                    }

                }
            }
        }
    }

    public void setViewSelection(){
        for (int i = 0; i < lessons.length; i++) {
            for (ClassView viewIsSelected  : lessons[i]) {
                viewIsSelected.setViewIsSelected(false);
            }
        }

    }



    public void setViewSelection(HTLesson hTLesson) {
        for (int i = 0; i < lessons.length; i++) {
            for (ClassView classView : lessons[i]) {
                if (classView.checkSubject(hTLesson)) {
                    classView.setViewIsSelected(true);
                } else {
                    classView.setViewIsSelected(false);
                }
            }
        }
    }

    public void setViewProperty(){

        for (int i = 0; i < lessons.length; i++) {
            for (ClassView classView : lessons[i]) {
                classView.setViewProperty();
            }
        }

    }

    public void setLessonForEditSub(HTLesson htLesson){
        for (int i = 0; i < lessons.length; i++) {
            for (ClassView classView : lessons[i]) {
                if (classView.getSubject() != null) {
                    if (classView.getSubject().lessonId == htLesson.lessonId) {
                        classView.setViewForEditSub();
                    } else {
                        classView.subStored();              }
                }
            }
        }

    }

    public void setLessonAfterDelete(HTLesson htLesson){
        for (int i = 0; i < lessons.length; i++) {
            for (ClassView classView : lessons[i]) {
                if (classView.getSubject() != null && classView.getSubject().lessonId == htLesson.lessonId) {
                    classView.refreshViewAfterDelete();
                }
            }
        }

    }

    public void setLessonAfterDelete(int id){
        for (int i = 0; i < lessons.length; i++) {
            for (ClassView classView : lessons[i]) {
                if (classView.getSubject() != null && classView.getSubject().lessonId == id) {
                    classView.refreshViewAfterDelete();
                }
            }
        }

    }

}
