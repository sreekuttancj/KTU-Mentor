package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper;


import android.content.Context;
import android.content.SharedPreferences;

public class Preferencemanager {


    SharedPreferences preferences;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "TIMETABLE";

    private static final String KEY_WEAK = "weak";
    private static final String KEY_PERIOD="period";

    public Preferencemanager(Context context) {
        this._context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }


    public void setWeak(String weak)
    {
        editor.putString(KEY_WEAK,weak);
        editor.commit();
    }

    public String getWeak()
    {
        return preferences.getString(KEY_WEAK,"5");
    }

    public void setPeriod(String period)
    {
        editor.putString(KEY_PERIOD,period);
        editor.commit();
    }
    public String getPeriod()
    {
        return preferences.getString(KEY_PERIOD,"9");
    }

}
