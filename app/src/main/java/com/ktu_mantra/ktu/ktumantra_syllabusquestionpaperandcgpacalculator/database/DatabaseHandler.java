package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.CalenderItem;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.QuestionsItem;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.SyllabusItem;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context mContext;

    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "ktu";

    private static String DATABASE_PATH;

    public static final String TABLE_SYLLABUS= "syllabus";
    public static final String TABLE_QUESTIONPAPER= "question_paper";
    public static final String TABLE_SUBJECT= "subject";
    public static final String TABLE_POSITION="position";
    public static final String TABLE_CALENDER="calender";
    public static final String TABLE_CALENDER_FAVOURITE="calender_favourite";



    private static final String KEY_SUB_ID = "id";
    private static final String KEY_COURSE = "course";
    private static final String KEY_BRANCH = "branch";
    private static final String KEY_SEMESTER = "semester";
    private static final String KEY_YEAR = "year";


    private static final String KEY_SUB_NAME = "sub_name";
    private static final String KEY_MODULE_1 = "module_1";
    private static final String KEY_MODULE_2 = "module_2";
    private static final String KEY_MODULE_3 = "module_3";
    private static final String KEY_MODULE_4 = "module_4";
    private static final String KEY_MODULE_5 = "module_5";
    private static final String KEY_MODULE_6 = "module_6";
    private static final String KEY_REFFERENCE = "refference";


    private static final String FAVOURITE_ID = "favourite_id";
    private static final String FAVOURITE_MONTH = "month";
    private static final String FAVOURITE_DAY = "day";
    private static final String FAVOURITE_WEEK = "week";
    private static final String FAVOURITE_DESCRIPTION = "description";
    private static final String FAVOURITE_HOLIDAY = "holiday";



    String CREATE_SYLLABUS_TABLE = "CREATE TABLE " + TABLE_SYLLABUS + "("
            + KEY_SUB_ID + " INTEGER ," + KEY_COURSE + " TEXT,"+ KEY_BRANCH+ " TEXT,"+ KEY_SEMESTER+ " TEXT,"+  KEY_SUB_NAME+ " TEXT,"+ KEY_MODULE_1 + " TEXT,"
            + KEY_MODULE_2 + " TEXT," + KEY_MODULE_3 + " TEXT,"+ KEY_MODULE_4 + " TEXT,"
            + KEY_MODULE_5 +" TEXT,"+ KEY_MODULE_6 +" TEXT,"+ KEY_REFFERENCE + " TEXT" +")";

    String CREATE_QUESTIONPAPER_TABLE = "CREATE TABLE " + TABLE_QUESTIONPAPER + "("
            + KEY_SUB_ID + " INTEGER ," + KEY_SUB_NAME + " TEXT,"+ KEY_YEAR+ " TEXT" +")";


    String CREATE_CALENDER_FAVOURITE = "CREATE TABLE " + TABLE_CALENDER_FAVOURITE + "("
            + FAVOURITE_ID+ " INTEGER PRIMARY KEY," + FAVOURITE_MONTH + " TEXT,"+ FAVOURITE_DAY+ " TEXT," + FAVOURITE_WEEK+ " TEXT," + FAVOURITE_DESCRIPTION+ " TEXT,"
            + FAVOURITE_HOLIDAY+ " TEXT "+")";


    List<QuestionsItem> questionsItems;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=context;
        DATABASE_PATH = context.getFilesDir().getParentFile().getPath()
                + "/databases/";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String subTable="CREATE TABLE "+TABLE_SUBJECT+
                "(id INTEGER PRIMARY KEY, subName TEXT, profName TEXT, color TEXT, fontColor TEXT );";

        String storePosition="CREATE TABLE "+TABLE_POSITION+ "(id INTEGER PRIMARY KEY, day INTEGER, period INTEGER, subId INTEGER);";

        db.execSQL(CREATE_SYLLABUS_TABLE);
        db.execSQL(CREATE_QUESTIONPAPER_TABLE);
        db.execSQL(CREATE_CALENDER_FAVOURITE);
        db.execSQL(subTable);
        db.execSQL(storePosition);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYLLABUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONPAPER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDER_FAVOURITE);
        db.execSQL("DROP TABLE IF EXISTS subject");
        db.execSQL("DROP TABLE IF EXISTS position");

        onCreate(db);
    }

    public void addSyllabusContent(int position,String course,String branch,String semester,String subName,String module1,String module2,String module3,String module4,String module5,String module6,String refference){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_SUB_ID,position);
        values.put(KEY_COURSE, course);
        values.put(KEY_BRANCH, branch);
        values.put(KEY_SEMESTER, semester);

        values.put(KEY_SUB_NAME, subName);
        values.put(KEY_MODULE_1, module1);
        values.put(KEY_MODULE_2, module2);
        values.put(KEY_MODULE_3, module3);
        values.put(KEY_MODULE_4, module4);
        values.put(KEY_MODULE_5, module5);
        values.put(KEY_MODULE_6, module6);
        values.put(KEY_REFFERENCE, refference);

        db.insert(TABLE_SYLLABUS, null, values);

    }

    public void addCalenderFavourite(CalenderItem calenderItem){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FAVOURITE_ID,calenderItem.getId());
        values.put(FAVOURITE_MONTH,calenderItem.getMonth());
        values.put(FAVOURITE_DAY,calenderItem.getDay());
        values.put(FAVOURITE_WEEK,calenderItem.getWeek());
        values.put(FAVOURITE_DESCRIPTION,calenderItem.getContent());
        values.put(FAVOURITE_HOLIDAY,calenderItem.getHoliday());

        db.insert(TABLE_CALENDER_FAVOURITE, null, values);

    }

    public void deleteFavourite(CalenderItem calenderItem){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALENDER_FAVOURITE, FAVOURITE_ID + " = ?",
                new String[] { String.valueOf(calenderItem.getId()) });
        db.close();

    }

    public List<CalenderItem> getCalenderFavourite(){

        List<CalenderItem> calenderItemList=new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CALENDER_FAVOURITE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CalenderItem calenderItem = new CalenderItem();

                calenderItem.setId(cursor.getInt(0));
                calenderItem.setMonth(cursor.getString(1));
                calenderItem.setDay(cursor.getInt(2));
                calenderItem.setWeek(cursor.getString(3));
                calenderItem.setContent(cursor.getString(4));
                calenderItem.setHoliday(cursor.getString(5));

                calenderItemList.add(calenderItem);
            } while (cursor.moveToNext());
        }

        return calenderItemList;


    }

    public List<Integer> getCalenderFavId(){
        List<Integer> calenderIdList=new ArrayList<>();
        String selectQuery = "SELECT  favourite_id FROM " + TABLE_CALENDER_FAVOURITE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                Log.i("favidlist",String.valueOf(cursor.getInt(0)));
                calenderIdList.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        return calenderIdList;



    }

    public void addQuestionPaper(String position,String subject,String year){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUB_ID,position);
        values.put(KEY_SUB_NAME, subject);
        values.put(KEY_YEAR, year);

        db.insert(TABLE_QUESTIONPAPER, null, values);

    }

    public int checkDb(String course,String branch,String semester)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String count = "SELECT count(*) FROM "+TABLE_SYLLABUS+" WHERE "+KEY_COURSE+" = '"+course+"' AND "+KEY_BRANCH+" = '"+branch+"' AND "+KEY_SEMESTER+" = '"+semester+"'" ;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int iCount = mcursor.getInt(0);
        mcursor.close();
        Log.i("count of db",String.valueOf(iCount));

        return iCount;

    }

    public int checkQuestions(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String count="SELECT count(*) FROM "+TABLE_QUESTIONPAPER;
        Cursor cursor=sqLiteDatabase.rawQuery(count,null);
        cursor.moveToFirst();
        int iCount=cursor.getInt(0);
        return iCount;

    }


    public SyllabusItem getSyllabusContent(int position, String course, String branch, String semester){

        SyllabusItem syllabusItem =new SyllabusItem();
        SQLiteDatabase db=this.getReadableDatabase();
        String getAllData= "SELECT "+KEY_MODULE_1+ " , "+KEY_MODULE_2+" , "+KEY_MODULE_3+" , "+KEY_MODULE_4+" , "+KEY_MODULE_5+" , "+KEY_MODULE_6+" , "+KEY_REFFERENCE+  " FROM "+TABLE_SYLLABUS+" WHERE "+KEY_COURSE+" = '"+course+"' AND "+KEY_BRANCH+" = '"+branch+"' AND "+KEY_SEMESTER+" = '"+semester+"' AND "+KEY_SUB_ID+" = '"+position+"'";
        Cursor mCursor=db.rawQuery(getAllData,null);
        mCursor.moveToFirst();

        syllabusItem.setM1(null);
        syllabusItem.setM2(null);
        syllabusItem.setM3(null);
        syllabusItem.setM4(null);
        syllabusItem.setM5(null);
        syllabusItem.setM6(null);
        syllabusItem.setT_r(null);

        if (mCursor.getCount()>0) {

            syllabusItem.setM1(mCursor.getString(0));
            syllabusItem.setM2(mCursor.getString(1));
            syllabusItem.setM3(mCursor.getString(2));
            syllabusItem.setM4(mCursor.getString(3));
            syllabusItem.setM5(mCursor.getString(4));
            syllabusItem.setM6(mCursor.getString(5));
            syllabusItem.setT_r(mCursor.getString(6));
        }

        mCursor.close();
        return syllabusItem;
    }

    public List<QuestionsItem> getQuestionContent(){
        questionsItems =new ArrayList<>();


        SQLiteDatabase db=this.getReadableDatabase();
        for (int i=0;i<checkQuestions();i++){

            String getAllData="SELECT * FROM "+TABLE_QUESTIONPAPER+" WHERE "+KEY_SUB_ID+" = '"+i+"'";
            Cursor mCursor=db.rawQuery(getAllData,null);
            mCursor.moveToFirst();

            QuestionsItem questionsItem =new QuestionsItem();
            if (mCursor.getCount()>0){
                questionsItem.setPos(Long.valueOf(mCursor.getString(0)));
                questionsItem.setName(mCursor.getString(1));
                questionsItem.setYear(mCursor.getString(2));
                questionsItems.add(questionsItem);
            }

        }
        return questionsItems;

    }

    public String getQnameAppend(int position){
        String name="";
        SQLiteDatabase db=this.getReadableDatabase();
        String getAllData="SELECT * FROM "+TABLE_QUESTIONPAPER+" WHERE "+KEY_SUB_ID+" = '"+position+"'";
        Cursor mCursor=db.rawQuery(getAllData,null);
        mCursor.moveToFirst();
        if (mCursor.getCount()>0){
            name=mCursor.getString(1)+" "+mCursor.getString(2)+".pdf";
        }
        return name;

    }

    public void createCalenderDb() throws IOException {
        boolean check = checkCalenderDataBase();

        SQLiteDatabase db_Read = null;

        // Creates empty database default system path
        db_Read = this.getWritableDatabase();
        db_Read.close();
        try {
            if (!check) {
                copyDataBase();
            }

        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }

    private boolean checkCalenderDataBase() {


        SQLiteDatabase checkDB = null;
        try {
            String myPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(DATABASE_NAME+".db");

        // Path to the just created empty db
        String outFileName = DATABASE_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    public List<CalenderItem> getAllCalenderInfo(){

        List<CalenderItem> calenderItemList=new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CALENDER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                CalenderItem calenderItem = new CalenderItem();

                calenderItem.setId(cursor.getInt(0));
                calenderItem.setMonth(cursor.getString(1));
                calenderItem.setDay(cursor.getInt(2));
                calenderItem.setWeek(cursor.getString(3));
                calenderItem.setContent(cursor.getString(4));
                calenderItem.setHoliday(cursor.getString(5));

                calenderItemList.add(calenderItem);
            } while (cursor.moveToNext());
        }

        return calenderItemList;


    }
}
