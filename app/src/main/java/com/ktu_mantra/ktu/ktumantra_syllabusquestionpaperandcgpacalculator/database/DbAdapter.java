package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DbAdapter {

    private static DbAdapter dbAdapter =new DbAdapter();
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHandler databaseHandler;
    private Context context;

    public static DbAdapter creation(Context context){
        dbAdapter.createDb(context);
        dbAdapter.context=context;
        return dbAdapter;
    }

    private void createDb(Context context)
    {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
    }

    public DbAdapter makeWritable(){
        sqLiteDatabase=databaseHandler.getWritableDatabase();
        return this;
    }
   public long storeSubject(String subName,String profName,String color,String fontColor){
       makeWritable();
       ContentValues contentValues=new ContentValues();
       contentValues.put("subName",subName);
       contentValues.put("profName",profName);
       contentValues.put("color",color);
       contentValues.put("fontColor",fontColor);
       long id= sqLiteDatabase.insert(DatabaseHandler.TABLE_SUBJECT,null,contentValues);
       closeDb();
       return id;
   }

    public int updateSubject(String subName,String profName,String color,String fontColor,int id){
        ContentValues contentValues=new ContentValues();
        contentValues.put("subName",subName);
        contentValues.put("profName",profName);
        contentValues.put("color", color);
        contentValues.put("fontColor",fontColor);

        makeWritable();
        SQLiteDatabase localSQLiteDatabase = sqLiteDatabase;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = String.valueOf(id);
        int i = localSQLiteDatabase.update("subject", contentValues, "id=?", arrayOfString);
        closeDb();
        return i;

    }

    public long storePosition(int day,int period,int subId){
        makeWritable();
        ContentValues contentValues=new ContentValues();
        contentValues.put("day",day);
        contentValues.put("period",period);
        contentValues.put("subId",subId);
        long id=sqLiteDatabase.insert(DatabaseHandler.TABLE_POSITION, null, contentValues);
        closeDb();
        return id;
    }

    public Cursor getSubData(String query,String[] paramArrayOfString){
        return sqLiteDatabase.rawQuery(query,paramArrayOfString);
    }

    public int deleteSubInfo(int id){

        makeWritable();
        SQLiteDatabase localSQLiteDatabase = sqLiteDatabase;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = String.valueOf(id);
        int lessonId = localSQLiteDatabase.delete("position", "subId=?", arrayOfString);
        closeDb();
        return lessonId;

    }

    public int deleteFullSubData(int id){

            makeWritable();
            SQLiteDatabase localSQLiteDatabase1 = sqLiteDatabase;
            String[] arrayOfString1 = new String[1];
            arrayOfString1[0] = String.valueOf(id);
            localSQLiteDatabase1.delete("position", "subId=?", arrayOfString1);

            SQLiteDatabase localSQLiteDatabase2 = sqLiteDatabase;
            String[] arrayOfString2 = new String[1];
            arrayOfString2[0] = String.valueOf(id);
            int i = localSQLiteDatabase2.delete("subject", "id=?", arrayOfString2);
            closeDb();
            return i;
        }


    public Cursor setCursor(String query, String[] values)
    {
        return sqLiteDatabase.rawQuery(query, values);
    }


    public void closeDb(){
        sqLiteDatabase.close();
    }
}
