package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list.ColorCollection;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.DbAdapter;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.Preferencemanager;


public class UIManager {

    private static UIManager htuiManager;

    Context mContext;

    private int dayWidth;
    private int remWidth;
    public static int screenHeight;
    private int screenWidth;
    public static int verticalStartLinePos;
    public static int verticalEndLinePos;
    private int density;
    private int gap;
    private int yGap;
    private int noPeriods;
    private int viewHeight;
    private int viewState;


    private int viewColorPostion;
    private int viewBackground;
    private int f5334I;
    private int viewTextColor;



    private int topAxisHeight;
    ClassMain classMain;
    DbAdapter dbAdapter;
    public HTLesson lesson;


    private int f5349l;


    private int indHeight;
    private int minHeight;
    private int maxHeigth;

   public int daysTotal;

    Preferencemanager preferencemanager;
    TimetableActivity timetableActivity;



    static {
        htuiManager=new UIManager();
    }


    public static  UIManager staticObject(){
        return htuiManager;

    }

    public void m5249a(Context context){


        mContext=context;
        dbAdapter=DbAdapter.creation(mContext);

        preferencemanager=new Preferencemanager(context);
        daysTotal  = Integer.parseInt(preferencemanager.getWeak());
        noPeriods  = Integer.parseInt(preferencemanager.getPeriod());
        density = (int) context.getResources().getDisplayMetrics().density;
        if (((double) density) < 1.5d) {
            yGap=2;
            gap = 1;
        } else {
            gap = 2;
            yGap = 4;
        }

        screenHeight=context.getResources().getDisplayMetrics().heightPixels;
        this.screenWidth=context.getResources().getDisplayMetrics().widthPixels;
        verticalEndLinePos=screenWidth-gap;
        verticalStartLinePos=screenHeight-yGap;
        dayWidth = ((screenWidth - pixelToDp(38)) -pixelToDp(1)) / daysTotal;
        remWidth=(screenWidth-(daysTotal*dayWidth))-gap;
        this.topAxisHeight=pixelToDp(38);


        indHeight = calcHeight(viewHeight);
        Log.i("init",String.valueOf(viewHeight));

        minHeight = pixelToDp(48);
        maxHeigth = pixelToDp(96);

    }



    public int indvidualDayWidth(){
        return this.dayWidth;
    }

    public int remainingWidth(){
        return remWidth;
    }

    public int gap(){
        return gap;
    }
    public int yGap(){
        return yGap;
    }

    public int topAxis(){
        return topAxisHeight;
    }

    public int pixelToDp(int i) {
        if (this.mContext == null) {
            return 0;
        }
        float density = this.mContext.getResources().getDisplayMetrics().density;
        return density != 1.0f ? (int) (((double) (density * ((float) i))) + 0.5d) : i;
    }

    public int pixelToDp(int i,Context context){

        float density = context.getResources().getDisplayMetrics().density;
        return density == 1.0f ? i : (int) (((double) (density * ((float) i))) + 0.5d);
    }


    //scroll view part



    public UIManager(){

        viewState=0;
        viewHeight=0;
        f5349l=0;
    }

    //Class Main

    public int  indHeight(){
        Log.i("returnvalue",String.valueOf(indHeight));
        return indHeight;

    }


    public int maxScreenWidth(){

        return screenWidth;
    }

    public int calcHeight(int i) {
        if (i <= 0) {
            return 0;
        }
        int height = (i - gap) / noPeriods();
        if (height < minHeight) {
            return minHeight;
        }
        if (height > maxHeigth) {
            return maxHeigth;
        }
        return height;
    }

   public int noPeriods(){

       return noPeriods;
   }

   public void setHeight(int indHeight) {

       if (viewHeight < 50) {


           viewHeight=indHeight;
           this.indHeight=calcHeight(indHeight);

           if (timetableActivity!=null){

               timetableActivity.setClassMain();
           }


       }
   }

    public void setClassMain(ClassMain classMain){
        this.classMain=classMain;
    }

    public void setMainActivity(TimetableActivity timetableActivity){

        if (!(this.timetableActivity == null || this.timetableActivity == timetableActivity)) {
            this.timetableActivity.finish();
            setViewState();
        }

        this.timetableActivity=timetableActivity;
    }

    public HTLesson htLesson(){
        HTLesson htLesson=new HTLesson();
        htLesson.subName=BuildConfig.VERSION_NAME;
        htLesson.colorHex=BuildConfig.VERSION_NAME;
        htLesson.fontColorHex=BuildConfig.VERSION_NAME;
        htLesson.color=0;
        htLesson.fontColor=0;
        return htLesson;
    }

    public long setPosition(int day,int period,int lessonId){

        return dbAdapter.storePosition(day,period,lessonId);
    }

    public long storeSubject(HTLesson htLesson){
        return dbAdapter.storeSubject(htLesson.subName, htLesson.profName, htLesson.colorHex, htLesson.fontColorHex);
    }

    public long updateSubject(HTLesson htLesson){
        return dbAdapter.updateSubject(htLesson.subName, htLesson.profName, htLesson.colorHex, htLesson.fontColorHex,htLesson.lessonId);

    }
    public void setViewState(){
        viewState=0;
    }

    public void setViewState1(){
        viewState=1;
    }
    public void setViewState2(){
        viewState=2;
    }

    public int getVewState(){
        return viewState;
    }

    public void makeViewSelectionFalse(){
        classMain.setViewSelection();
    }

    public HTLesson[] getSubInfoFromDb(){
        dbAdapter.makeWritable();
        Cursor cursor=dbAdapter.getSubData("SELECT id, subName ,profName , color, fontColor FROM subject",null);
        HTLesson[] hTLessonArr = null;
        int count = cursor.getCount();
        if (count!=0){
            HTLesson[] hTLessonArr2 = new HTLesson[count];
            int i = 0;
            while (cursor.moveToNext()) {
                HTLesson hTLesson = new HTLesson();
                hTLesson.lessonId=cursor.getInt(0);
                hTLesson.subName=cursor.getString(1);
                hTLesson.profName=cursor.getString(2);
                hTLesson.colorHex=cursor.getString(3);
                hTLesson.fontColorHex=cursor.getString(4);

                hTLesson.color= ColorCollection.parseColorHex(hTLesson.colorHex);
                hTLesson.fontColor=ColorCollection.parseFontColorHex(hTLesson.fontColorHex);

                Cursor cursor2=dbAdapter.getSubData("SELECT day, period FROM position WHERE subId=?", new String[]{String.valueOf(hTLesson.lessonId)});

                if (cursor2.getCount()!=0){
                    while (cursor2.moveToNext()){
                        hTLesson.addPostions(cursor2.getInt(0),cursor2.getInt(1));
                    }
                }
                cursor2.close();
                hTLessonArr2[i]=hTLesson;
                i++;

            }
            hTLessonArr=hTLessonArr2;

            }
        cursor.close();
        dbAdapter.closeDb();
        return hTLessonArr;
    }

    public void showColorList(){

        timetableActivity.showColorList();
    }



    public void setPositionCall(int position){
        setColorPosition(position);
        timetableActivity.setColorBtnProperty(position);

    }

    public void setColorPosition(int position){
        viewColorPostion=position;
        viewBackground= ColorCollection.colorDailog(position);
        f5334I=ColorCollection.colorAlpha(position);
        viewTextColor=ColorCollection.colorAlpha(position);
    }



    public void setColorPositionForEditSub(int colorPosition, int color, int fontColor) {
        viewColorPostion = colorPosition;
        if (colorPosition >= 0) {
            viewBackground = ColorCollection.colorDailog(colorPosition);
            this.f5334I = ColorCollection.colorDialogFont(colorPosition);
            viewTextColor = ColorCollection.colorDialogFont(colorPosition);
            return;
        }
        this.viewBackground = color;
        this.f5334I = ColorCollection.defaultFontColor(fontColor);
        this.viewTextColor = ColorCollection.defaultFontColor(fontColor);
    }

    public int setViewBackColor(){
        return viewBackground;
    }
    public int setViewTextColor(){
        return viewTextColor;
    }
    public int getViewColorPosition(){
        return viewColorPostion;
    }

    public void enableSubClick(HTLesson htLesson){
        timetableActivity.enableSubOnclick(htLesson);
        classMain.setViewSelection(htLesson);
    }

    public void setHTLesson(HTLesson lesson){
        this.lesson=lesson;
        timetableActivity.editSubject(lesson);
    }

    public void deleteSubIfo(int id){
        dbAdapter.deleteSubInfo(id);
    }

    public void deleteFullSubData(HTLesson htLesson){
        dbAdapter.deleteFullSubData(htLesson.lessonId);
        timetableActivity.setClassMainAfterDelete(htLesson);

    }

    public void deleteAllSudjects(){
        dbAdapter.makeWritable();
        Cursor cursor=dbAdapter.getSubData("SELECT id FROM subject", null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                dbAdapter.deleteFullSubData(id);
                timetableActivity.setClassMainAfterDelete(id);
            }
        }
        dbAdapter.closeDb();
    }


}
