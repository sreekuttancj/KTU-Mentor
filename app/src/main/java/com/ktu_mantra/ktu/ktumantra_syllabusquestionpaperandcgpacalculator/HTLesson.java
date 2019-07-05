package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import java.util.ArrayList;

public class HTLesson {

    public int lessonId;
    public String subName;
    public String profName;
    public int color;
    public int fontColor;
    public String colorHex;
    public String fontColorHex;

    int day;
    int period;
    ArrayList lessons;


    public HTLesson(){

        lessons=new ArrayList();
    }
    public HTLesson(int day, int period){
        this.day=day;
        this.period=period;
    }

    public void clearList(){

        lessons.clear();
    }

    public void addPostions(int day,int period){
        lessons.add(new HTLesson(day, period));
    }

    public boolean checkPosition(int day,int period){
        if (lessons==null){
            return false;
        }
        for (int i = 0; i < lessons.size(); i++) {
            HTLesson hTLesson = (HTLesson) lessons.get(i);
            if (hTLesson.day == day && hTLesson.period == period) {
                return true;
            }
        }
        return false;
    }


}
