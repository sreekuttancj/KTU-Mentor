package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list.ColorButton;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list.ColorCollection;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list.ColorListDialog;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.Preferencemanager;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.lesson.HTDialog;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.lesson.HTLessonDialog;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.lesson.MoreMenuDialod;

import java.io.File;
import java.io.FileOutputStream;

public class TimetableActivity extends AppCompatActivity implements View.OnClickListener{


    public static Activity mainActivity;
    final static String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";

    DateView dateView;

    TopAxisView htTopAxisView;
    ColorButton colorButton;
    private InputMethodManager inputMethodManager;



    TextView textViewSubNameTitle;
    TextView textViewProfNameTitle;
    TextView textViewSubName;
    TextView textViewProfName;
    TextView textViewTopMenuTitle;
    ImageButton imageButtonCloseSubInfo;


    EditText editTextSubName;
    EditText editTextProfName;

    ImageButton imageButtonMoreMenu;
    ImageButton imageButtonAddSubject;
    ImageButton imageButtonEditSubject;
    ImageButton imageButtonCancel;
    ImageButton imageButtonSave;
    boolean isInternetpresent=false;

    PrefManager prefManager;
    ConnectionDetector connectionDetector;


    Preferencemanager preferencemanager;

    LinearLayout linearLayoutTopbarTitle;
    RelativeLayout relativeLayoutSubjectInformation;
    LinearLayout linearLayoutMain;

    VerticalScrollView verticalScrollView;
    ClassMain classMain;
    UIManager htuiManager;
    HTLesson htLesson;
    public View rootView;
    final  int REQUEST_CODE_ASK_PERMISSIONS = 200;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        mainActivity = this;
        linearLayoutMain = (LinearLayout) findViewById(R.id.activity_main);
        htuiManager = UIManager.staticObject();
        htuiManager.setMainActivity(this);
        classMain = (ClassMain) findViewById(R.id.class_main);
        htuiManager.setClassMain(classMain);
        inputMethodManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));

        verticalScrollView = (VerticalScrollView) findViewById(R.id.main_scrollview);
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        setClassMain();

        preferencemanager = new Preferencemanager(getApplicationContext());


        htTopAxisView = ((TopAxisView) findViewById(R.id.top_axis_view_week_1));
        dateView = (DateView) findViewById(R.id.date_absenece_view);
        colorButton = (ColorButton) findViewById(R.id.button_color);
        htTopAxisView.setValues(this);


        textViewSubNameTitle = (TextView) findViewById(R.id.subject_title);
        textViewProfNameTitle = (TextView) findViewById(R.id.professor_title);
        textViewSubName = (TextView) findViewById(R.id.subject_name);
        textViewProfName = (TextView) findViewById(R.id.professor_name);
        textViewTopMenuTitle = (TextView) findViewById(R.id.top_menu_title);
        imageButtonCloseSubInfo = (ImageButton) findViewById(R.id.button_close_subject_information_view);

        editTextSubName = (EditText) findViewById(R.id.button_subject_name);
        editTextProfName = (EditText) findViewById(R.id.button_professor_name);

        imageButtonMoreMenu = (ImageButton) findViewById(R.id.button_more_menu);
        imageButtonAddSubject = (ImageButton) findViewById(R.id.button_add_subject);
        imageButtonEditSubject = (ImageButton) findViewById(R.id.button_edit_subject);
        imageButtonCancel = (ImageButton) findViewById(R.id.button_top_menu_cancel);
        imageButtonSave = (ImageButton) findViewById(R.id.button_top_menu_save);

        linearLayoutTopbarTitle = (LinearLayout) findViewById(R.id.view_top_bar_title_mode);
        relativeLayoutSubjectInformation = (RelativeLayout) findViewById(R.id.subject_information_view);


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                mAdView.setVisibility(View.GONE);
            }
        });


        prefManager = new PrefManager(getApplicationContext());
        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetpresent = connectionDetector.isConnectingToInternet();
        if (isInternetpresent) {

            if (prefManager.getCount() < 1) {
                mAdView.setVisibility(View.INVISIBLE);
            } else {
                mAdView.setVisibility(View.VISIBLE);

            }
        } else {
            mAdView.setVisibility(View.INVISIBLE);

        }


        imageButtonAddSubject.setOnClickListener(this);
        imageButtonEditSubject.setOnClickListener(this);
        imageButtonCancel.setOnClickListener(this);
        imageButtonSave.setOnClickListener(this);
        imageButtonCloseSubInfo.setOnClickListener(this);
        imageButtonMoreMenu.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.button_top_menu_cancel:

                btnCancelSub();
                break;

            case R.id.button_top_menu_save:

                btnSaveSub();
                break;


            case R.id.button_add_subject:

                btnAddSub();
                break;

            case R.id.button_edit_subject:
                editSubInitialize();
                new HTLessonDialog(this).show();
                break;
            case R.id.button_close_subject_information_view:
                subInfoClose();
                break;

            case R.id.button_more_menu:

              MoreMenuDialod moreMenuDialod=  new MoreMenuDialod(this);
                moreMenuDialod.setMainActivity(this);
                moreMenuDialod.show();
                editSubInitialize();
                break;

        }
    }

    public void btnAddSub(){

        htuiManager.setViewState1();
        htuiManager.makeViewSelectionFalse();
        classMain.subStored();
        addSubInitialize();
        editTextSubName.setText("");
        editTextProfName.setText("");
        colorButton.setSubjectColor(ColorCollection.colorDailog(0));
        colorButton.setTextColor(ColorCollection.colorAlpha(0));
        htuiManager.setColorPosition(0);
        relativeLayoutSubjectInformation.setVisibility(View.VISIBLE);

    }


    public void btnSaveSub(){
        int viewColorPosition;
        String subName=editTextSubName.getText().toString();
        if ((subName==null)||(subName.length()==0)){

            HTDialog htDialog=new HTDialog(this);
            htDialog.setMessage(getString(R.string.subject_name_empty_message));
            htDialog.setButtonText(getString(R.string.ok_button));
            htDialog.setDeleteBtnProperty1(0);
            htDialog.show();
            return;
        }

        String profName = editTextProfName.getText().toString();
        if (profName == null) {
            profName = "";
        }
        changeVisibility();
        if (htuiManager.getVewState()==1) {
            viewColorPosition=htuiManager.getViewColorPosition();

            htLesson = htuiManager.htLesson();
            htLesson.subName = subName;
            htLesson.profName = profName;
            htLesson.colorHex=ColorCollection.colorDailogHex(viewColorPosition);
            htLesson.fontColorHex=ColorCollection.colorDialogFontHex(viewColorPosition);
            htLesson.color=ColorCollection.parseColorHex(htLesson.colorHex);
            htLesson.fontColor=ColorCollection.parseFontColorHex(htLesson.fontColorHex);
            int id= (int) htuiManager.storeSubject(htLesson);
            htLesson.lessonId=id;
            classMain.setLesson(htLesson);
            classMain.setLesson(true,htLesson);

            inputMethodManager.hideSoftInputFromWindow(editTextSubName.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromWindow(editTextProfName.getWindowToken(), 0);
            htuiManager.setViewState();
        }

        if(htuiManager.getVewState()==2){

            viewColorPosition=htuiManager.getViewColorPosition();
            htLesson = htuiManager.lesson;
            htLesson.subName=subName;
            htLesson.profName=profName;
//            htLesson.color=htuiManager.setViewBackColor();
//            htLesson.fontColor=htuiManager.setViewTextColor();
            htLesson.colorHex=ColorCollection.colorDailogHex(viewColorPosition);
            htLesson.fontColorHex=ColorCollection.colorDialogFontHex(viewColorPosition);
            htLesson.color=ColorCollection.parseColorHex(htLesson.colorHex);
            htLesson.fontColor=ColorCollection.parseFontColorHex(htLesson.fontColorHex);
            htLesson.clearList();
            htuiManager.updateSubject(htLesson);
            htuiManager.deleteSubIfo(htLesson.lessonId);
            classMain.setLesson(htLesson);
            classMain.setStoredLesson(true,htLesson);
            inputMethodManager.hideSoftInputFromWindow(editTextSubName.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromWindow(editTextProfName.getWindowToken(), 0);
            htuiManager.setViewState();

        }

    }

    public void btnCancelSub(){
        changeVisibility();
        if (htuiManager.getVewState()==1){
            classMain.setLesson(false, null);
        }

        if (htuiManager.getVewState()==2){
            classMain.setStoredLesson(false,null);
        }
        htuiManager.setViewState();
        inputMethodManager.hideSoftInputFromWindow(editTextSubName.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(editTextProfName.getWindowToken(), 0);


    }


    public void subInfoClose(){
        textViewSubName.setVisibility(View.GONE);
        textViewProfName.setVisibility(View.GONE);
        editTextSubName.setVisibility(View.VISIBLE);
        editTextProfName.setVisibility(View.VISIBLE);
        colorButton.setVisibility(View.VISIBLE);
        imageButtonCloseSubInfo.setVisibility(View.GONE);
        relativeLayoutSubjectInformation.setVisibility(View.GONE);
        editTextProfName.clearFocus();
        editTextSubName.clearFocus();
        classMain.setViewSelection();
    }
    public void addSubInitialize(){

        textViewSubName.setVisibility(View.GONE);
        textViewProfName.setVisibility(View.GONE);

        editTextSubName.setVisibility(View.VISIBLE);
        editTextProfName.setVisibility(View.VISIBLE);
        linearLayoutTopbarTitle.setVisibility(View.VISIBLE);
        colorButton.setVisibility(View.VISIBLE);
        editTextSubName.clearFocus();
        editTextProfName.clearFocus();
        relativeLayoutSubjectInformation.setBackgroundColor(getResources().getColor(R.color.subject_information_background_edit_mode));

    }

    public void editSubInitialize(){
        relativeLayoutSubjectInformation.setVisibility(View.GONE);
        htuiManager.makeViewSelectionFalse();
    }

    public void  changeVisibility(){
        textViewSubName.setVisibility(View.GONE);
        textViewProfName.setVisibility(View.GONE);
        editTextSubName.setVisibility(View.GONE);
        editTextProfName.setVisibility(View.GONE);
        colorButton.setVisibility(View.GONE);
        relativeLayoutSubjectInformation.setVisibility(View.GONE);
        linearLayoutTopbarTitle.setVisibility(View.GONE);

    }

    public void showColorList(){

        new ColorListDialog(TimetableActivity.this,htuiManager.getViewColorPosition()).show();
        inputMethodManager.hideSoftInputFromWindow(editTextSubName.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(editTextSubName.getWindowToken(), 0);
        editTextSubName.clearFocus();
        editTextProfName.clearFocus();
    }

    public void setColorBtnProperty(int position){
        colorButton.setSubjectColor(ColorCollection.colorDailog(position));
        colorButton.setTextColor(ColorCollection.colorAlpha(position));
        classMain.setViewProperty();
    }

    public void setClassMain(){
        classMain.init(getApplication());
        classMain.setLessonsFromDb();

    }

    public void enableSubOnclick(HTLesson htLesson){
        subOnClickVisibility();
        textViewSubName.setText(htLesson.subName);
        textViewProfName.setText(htLesson.profName);
        relativeLayoutSubjectInformation.setVisibility(View.VISIBLE);
        editTextSubName.setVisibility(View.GONE);
        editTextProfName.setVisibility(View.GONE);
        colorButton.setVisibility(View.GONE);
    }

    public void subOnClickVisibility() {
        textViewSubNameTitle.setVisibility(View.VISIBLE);
        textViewProfNameTitle.setVisibility(View.VISIBLE);
        textViewSubName.setVisibility(View.VISIBLE);
        textViewProfName.setVisibility(View.VISIBLE);
        imageButtonCloseSubInfo.setVisibility(View.VISIBLE);
        editTextSubName.setVisibility(View.GONE);
        editTextProfName.setVisibility(View.GONE);
        relativeLayoutSubjectInformation.setBackgroundColor(getResources().getColor(R.color.subject_information_background_normal));

    }

    public void screenShotShare(View view){

        Bitmap screenshot=getScreenShot(view);

        int hasLocationPermission = ContextCompat.checkSelfPermission(TimetableActivity.this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(TimetableActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


                showMessageOKCancel("PLease grant permission for storing Screenshots",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(TimetableActivity.this,
                                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }


                        });
            }

            else {
                Toast.makeText(getApplicationContext(), "Need to revoke Storage permission to store Screenshot", Toast.LENGTH_SHORT).show();

            }

        }
        else {

            store(screenshot, "KTU Mentor.png");
        }
    }

    public void editSubject(HTLesson htLesson){
        htuiManager.setViewState2();
        htuiManager.makeViewSelectionFalse();
        classMain.setLessonForEditSub(htLesson);
        addSubInitialize();
        editTextSubName.setText(htLesson.subName);
        editTextProfName.setText(htLesson.profName);
        colorButton.setSubjectColor(htLesson.color);
        colorButton.setTextColor(htLesson.fontColor);
        textViewTopMenuTitle.setText(getResources().getString(R.string.edit_course));
        relativeLayoutSubjectInformation.setBackgroundColor(getResources().getColor(R.color.subject_information_background_edit_mode));
        relativeLayoutSubjectInformation.setVisibility(View.VISIBLE);

        int colorPosition=ColorCollection.getSubColorPosition(htLesson.color);
        htuiManager.setColorPositionForEditSub(colorPosition, htLesson.color, htLesson.fontColor);
    }

    public void setClassMainAfterDelete(HTLesson htLesson){
        classMain.setLessonAfterDelete(htLesson);
    }
    public void setClassMainAfterDelete(int id){
        classMain.setLessonAfterDelete(id);
    }

    public static Bitmap getScreenShot(View rootView) {

        View view = rootView.getRootView();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }


    public  void store(Bitmap bm, String fileName) {
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        shareImage(file);

    }

    private  void shareImage(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(TimetableActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }



    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


}
