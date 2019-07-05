package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper;


public class ApplicationController extends android.app.Application{

    @Override
    public void onCreate() {


        super.onCreate();
        CustomFont.setDefaultFont(this, "MONOSPACE", "fonts/JosefinSans-Regular.ttf");

    }


}
