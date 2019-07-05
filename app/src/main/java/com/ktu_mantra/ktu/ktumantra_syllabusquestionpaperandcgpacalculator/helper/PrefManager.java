package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper;


import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {


    SharedPreferences preferences;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "KTUMENTOR";

    private static final String KEY_COURSE = "course";
    private static final String KEY_BRANCH="branch";
    private static final String KEY_SEMESTER = "semester";
    private static final String KEY_LOGIN="login";
    public static final String KEY_COUNT = "countKey";
    public static final String KEY_CACHE = "cache";



    public PrefManager(Context context) {
        this._context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }


    public void setCourse(String course)
    {
        editor.putString(KEY_COURSE,course);
        editor.commit();
    }

    public void setBranch(String branch)
    {
        editor.putString(KEY_BRANCH,branch);
        editor.commit();
    }

    public void setSemester(String semester)
    {
        editor.putString(KEY_SEMESTER,semester);
        editor.commit();
    }

    public void setLoginBit(String login){
        editor.putString(KEY_LOGIN,login);
        editor.commit();

    }
    public void setCount(int count){
        editor.putInt(KEY_COUNT,count);
        editor.commit();
    }
    public void setCache(int cache)
    {
        editor.putInt(KEY_CACHE,cache);
        editor.commit();
    }

    public String getCourse()
    {
        return preferences.getString(KEY_COURSE,"course");
    }
    public String getBranch()
    {
        return preferences.getString(KEY_BRANCH,"branch");
    }

    public String getSemester()
    {
        return preferences.getString(KEY_SEMESTER,"semester");
    }
    public String getLogin()
    {
        return preferences.getString(KEY_LOGIN,"fail");
    }
    public int getCount()
    {
        return preferences.getInt(KEY_COUNT,5);
    }
    public int getCache()
    {
        return preferences.getInt(KEY_CACHE,0);
    }



}
