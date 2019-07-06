package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kobakei.ratethisapp.RateThisApp;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.DatabaseHandler;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.NotificationItem;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.Utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.mobiwise.materialintro.animation.MaterialIntroListener;
import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;


public class CategorySelectionActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    Toolbar mToolbar;

    ImageView imageViewCalculator;
    ImageView imageViewSyllabus;
    ImageView imageViewQuestionBank;
    ImageView imageViewTimetable;
    ImageView imageViewNotification;
    ImageView imageViewCalender;
    ImageView imageViewEntertainment;
    ImageView imageViewDictionary;

    TextView textViewCalculator;
    TextView textViewSyllabus;
    TextView textViewQuestionBank;
    TextView textViewTimetable;
    TextView textViewCalender;
    TextView textViewNotification;
    TextView textViewEntertainment;
    TextView textViewDictionary;



    FetchCountTask fetchCountTask;
    String from="CategorySelectionActivity";
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public int mNotificationsCount = 0;

    PrefManager prefManager;
    private boolean isInternetPresent=false;
    ConnectionDetector connectionDetector;
    DatabaseHandler databaseHandler;

    DrawerLayout drawer;

    static final int SHARE_REQUEST = 1;  // The request code
    MaterialIntroView materialIntroView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        RateThisApp.Config config = new RateThisApp.Config(3, 5);
        RateThisApp.init(config);

        mToolbar = findViewById(R.id.toolbar);
        prefManager=new PrefManager(getApplicationContext());
        connectionDetector =new ConnectionDetector(getApplicationContext());
        databaseHandler=new DatabaseHandler(getApplicationContext());

        try {
            // check if database exists in app path, if not copy it from assets
            databaseHandler.createCalenderDb();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        imageViewCalculator= findViewById(R.id.imageViewCalculator);
        imageViewSyllabus= findViewById(R.id.imageViewSyllabus);
        imageViewQuestionBank= findViewById(R.id.imageViewQuestionBank);
        imageViewTimetable= findViewById(R.id.imageViewAboutUs);
        imageViewCalender= findViewById(R.id.imageViewCalender);
        imageViewNotification= findViewById(R.id.imageViewNotification);
        imageViewEntertainment= findViewById(R.id.imageViewEntertainment);
        imageViewDictionary= findViewById(R.id.imageViewDictionary);

        textViewCalculator= findViewById(R.id.textViewCalculator);
        textViewSyllabus= findViewById(R.id.textViewSyllabus);
        textViewQuestionBank= findViewById(R.id.textViewQuestionBank);
        textViewTimetable= findViewById(R.id.textViewAboutUs);
        textViewCalender= findViewById(R.id.textViewCalender);
        textViewNotification= findViewById(R.id.textViewNotification);
        textViewEntertainment= findViewById(R.id.textViewEntertainment);
        textViewDictionary= findViewById(R.id.textViewDictionary);

        imageViewCalculator.setOnClickListener(this);
        imageViewSyllabus.setOnClickListener(this);
        imageViewQuestionBank.setOnClickListener(this);
        imageViewTimetable.setOnClickListener(this);
        imageViewCalender.setOnClickListener(this);
        imageViewNotification.setOnClickListener(this);
        imageViewEntertainment.setOnClickListener(this);
        imageViewDictionary.setOnClickListener(this);

        mToolbar.setTitle("KTU Mentor");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        fetchCountTask=new FetchCountTask();
        fetchCountTask.execute();

        android.view.animation.Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);


        textViewCalculator.startAnimation(animation);
        textViewSyllabus.startAnimation(animation);
        textViewQuestionBank.startAnimation(animation);
        textViewTimetable.startAnimation(animation);
        textViewCalender.startAnimation(animation);
        textViewNotification.startAnimation(animation);
        textViewEntertainment.startAnimation(animation);
        textViewDictionary.startAnimation(animation);



        materialIntroView=new MaterialIntroView.Builder(CategorySelectionActivity.this)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(300)
                .setTargetPadding(25)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Entertainment Zone")
                .setTarget(imageViewEntertainment)
                .setUsageId("intro_entertainment") //THIS SHOULD BE UNIQUE ID
                .setListener(new MaterialIntroListener() {
                    @Override
                    public void onUserClicked(String materialIntroViewId) {



                        materialIntroView=new MaterialIntroView.Builder(CategorySelectionActivity.this)
                                .enableIcon(false)
                                .setFocusGravity(FocusGravity.CENTER)
                                .setFocusType(Focus.MINIMUM)
                                .setDelayMillis(300)
                                .setTargetPadding(30)
                                .enableFadeAnimation(true)
                                .performClick(false)
                                .setInfoText("You can Edit your profile\n View Important dates saved from Academic Calendar")
                                .setTarget(getNavButtonView(mToolbar))
                                .setUsageId("intro_humburger") //THIS SHOULD BE UNIQUE ID
                                .show();


                    }
                })
                .show();




    }

    @Override
    protected void onRestart() {
        super.onRestart();
        imageViewCalculator.getDrawable().setColorFilter(null);
        imageViewSyllabus.getDrawable().setColorFilter(null);
        imageViewQuestionBank.getDrawable().setColorFilter(null);
        imageViewTimetable.getDrawable().setColorFilter(null);
        imageViewNotification.getDrawable().setColorFilter(null);
        imageViewCalender.getDrawable().setColorFilter(null);
        imageViewEntertainment.getDrawable().setColorFilter(null);
        imageViewDictionary.getDrawable().setColorFilter(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_category, menu);
        MenuItem itemNotification=menu.findItem(R.id.notification);
        LayerDrawable icon = (LayerDrawable) itemNotification.getIcon();
        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, mNotificationsCount);

        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected (MenuItem item) {


        switch (item.getItemId()) {


            case R.id.notification:

                isInternetPresent=connectionDetector.isConnectingToInternet();

                if (isInternetPresent) {
                    Intent intentNotification = new Intent(CategorySelectionActivity.this, NotificationActivity.class);
                    startActivity(intentNotification);
                }
                else {
                    Snackbar.make(drawer,"No network connection",Snackbar.LENGTH_SHORT).show();
                }
                return true;



            case R.id.share_app:
                Uri imageUri = null;
                try {
                    imageUri = Uri.parse(MediaStore.Images.Media.insertImage(CategorySelectionActivity.this.getContentResolver(),
                            BitmapFactory.decodeResource(getResources(), R.drawable.share_image), null, null));
                } catch (NullPointerException e) {
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download KTU Mentor Android App developed for KTU University students :https://goo.gl/1Gy9QT");
                sendIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                sendIntent.setType("image/*");
                sendIntent.setPackage("com.whatsapp");
                startActivityForResult(sendIntent,SHARE_REQUEST);
                return true;


            case R.id.rate_us:



                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("https://goo.gl/abcK2p"));
                if (!MyStartActivity(intent))
                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                    intent.setData(Uri.parse("https://goo.gl/abcK2p"));
                if (!MyStartActivity(intent)) {
                    //Well if this also fails, we have run out of options, inform the user.
                    Toast.makeText(getApplicationContext(), "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
                }

                return true;


                    default:
                        return super.onOptionsItemSelected(item);
                }

    }

    public  boolean MyStartActivity(Intent aIntent) {
        try
        {
            aIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            getApplicationContext().startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        RateThisApp.onStart(this);
        RateThisApp.showRateDialogIfNeeded(this);

    }




    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            finish();
            Intent intentEdit=new Intent(CategorySelectionActivity.this,AuthenticationActivity.class);
            startActivity(intentEdit);

        }

        else if (id == R.id.adblock){
            Intent intentAdblock=new Intent(CategorySelectionActivity.this,AdblockActivity.class);
            startActivity(intentAdblock);
        }

        else if (id == R.id.share) {

            Uri imageUri = null;
            try {
                imageUri = Uri.parse(MediaStore.Images.Media.insertImage(CategorySelectionActivity.this.getContentResolver(),
                        BitmapFactory.decodeResource(getResources(), R.drawable.share_image), null, null));
            } catch (NullPointerException e) {
            }
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Download KTU Mentor Android App developed for KTU University students :https://goo.gl/1Gy9QT");
            sendIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
            sendIntent.setType("image/*");
            sendIntent.setPackage("com.whatsapp");
            startActivityForResult(sendIntent,SHARE_REQUEST);

        } else if (id == R.id.rate) {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse("https://goo.gl/abcK2p"));
            if (!MyStartActivity(intent))
                //Market (Google play) app seems not installed, let's try to open a webbrowser
                intent.setData(Uri.parse("https://goo.gl/abcK2p"));
            if (!MyStartActivity(intent)) {
                //Well if this also fails, we have run out of options, inform the user.
                Toast.makeText(getApplicationContext(), "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.about) {
            Intent aboutIntent = new Intent(CategorySelectionActivity.this,AboutUsActivity.class);
            startActivity(aboutIntent);
        }
        else if (id == R.id.important_day){
            Intent intentImportantDays=new Intent(CategorySelectionActivity.this,FavouriteDayActivity.class);
            startActivity(intentImportantDays);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void openGPlus(String profile) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setClassName("com.google.android.apps.plus",
                    "com.google.android.apps.plus.phone.UrlGatewayActivity");
            intent.putExtra("customAppUri", profile);
            startActivity(intent);
        } catch(ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/"+profile+"/posts")));
        }
    }

    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + AboutUsActivity.FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + AboutUsActivity.FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return AboutUsActivity.FACEBOOK_URL; //normal web url
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imageViewAboutUs:

                imageViewTimetable.getDrawable().setColorFilter(Color.parseColor("#80DEEA"), PorterDuff.Mode.MULTIPLY);
                Intent intentTimetable = new Intent(CategorySelectionActivity.this, TimetableActivity.class);
                startActivity(intentTimetable);

                break;
            case R.id.imageViewNotification:

                isInternetPresent=connectionDetector.isConnectingToInternet();
                if (isInternetPresent) {
                    imageViewNotification.getDrawable().setColorFilter(Color.parseColor("#80DEEA"), PorterDuff.Mode.MULTIPLY);
                    Intent intentNotification = new Intent(CategorySelectionActivity.this, NotificationActivity.class);
                    startActivity(intentNotification);
                }
                else {
                    Snackbar.make(v,"No network connection", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.imageViewCalender:

                imageViewCalender.getDrawable().setColorFilter(Color.parseColor("#80DEEA"), PorterDuff.Mode.MULTIPLY);
                Intent intentCalender = new Intent(CategorySelectionActivity.this, CalenderActivity.class);
                startActivity(intentCalender);

                break;
            case R.id.imageViewSyllabus:

                imageViewSyllabus.getDrawable().setColorFilter(Color.parseColor("#80DEEA"), PorterDuff.Mode.MULTIPLY);
                Intent intentSyllabus = new Intent(CategorySelectionActivity.this, SyllabusActivity.class);
                startActivity(intentSyllabus);
                break;
            case R.id.imageViewQuestionBank:


                if( prefManager.getSemester().equals("S1&S2")) {

                    if (databaseHandler.checkQuestions()>1) {

                        imageViewQuestionBank.getDrawable().setColorFilter(Color.parseColor("#80DEEA"), PorterDuff.Mode.MULTIPLY);
                        Intent intentQuestion = new Intent(CategorySelectionActivity.this, QuestionPaperActivity.class);
                        startActivity(intentQuestion);
                    }
                    else {
                        isInternetPresent=connectionDetector.isConnectingToInternet();
                        if (isInternetPresent){
                            imageViewQuestionBank.getDrawable().setColorFilter(Color.parseColor("#80DEEA"), PorterDuff.Mode.MULTIPLY);
                            Intent intentQuestion = new Intent(CategorySelectionActivity.this, QuestionPaperActivity.class);
                            startActivity(intentQuestion);
                        }
                        else {
                            Snackbar.make(drawer,"No network connection",Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    Snackbar.make(drawer,"Previous year question paper only available for S1 and S2",Snackbar.LENGTH_LONG).show();
                }

                break;
            case R.id.imageViewCalculator:

                if (!prefManager.getSemester().equals("S4")) {
                    imageViewCalculator.getDrawable().setColorFilter(Color.parseColor("#80DEEA"), PorterDuff.Mode.MULTIPLY);
                    Intent intentCalculator = new Intent(CategorySelectionActivity.this, CalculatorActivity.class);
                    startActivity(intentCalculator);
                }else {
                    Toast.makeText(getApplicationContext(),"Sorry for the inconvenience,S4 Calculator Will be available on next month onwards",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.imageViewEntertainment:
                isInternetPresent=connectionDetector.isConnectingToInternet();
                if (isInternetPresent) {
                    Intent intentEntertainment = new Intent(CategorySelectionActivity.this, ShortFilmListActivity.class);
                    startActivity(intentEntertainment);
                }
                else {
                    Snackbar.make(drawer,"No network connection",Snackbar.LENGTH_LONG).show();
                }
                break;

            case R.id.imageViewDictionary:
                Intent intentEntertainment = new Intent(CategorySelectionActivity.this, SamasyaActivity.class);
                startActivity(intentEntertainment);
                break;
        }
    }


    private class FetchCountTask extends AsyncTask<Void,Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            getNotification(from);

            while (1>0){

                if (isCancelled())
                {
                    break;
                }


                if (notificationList.size()>0){
                    break;
                }
                else {
                    continue;
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            for (int i=0;i<notificationList.size();i++){
                NotificationItem notification=notificationList.get(i);

                Date date = null;
                try {
                    date = df.parse(notification.getTimestamp());
                    long fromDate=date.getTime();
                    long currentDate=System.currentTimeMillis();
                    long timeDiff=currentDate-fromDate;
                    timeDiff=timeDiff/(24 * 60 * 60 * 1000);

                    if (timeDiff<7)
                    {
                        mNotificationsCount++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

            updateNotificationsBadge(mNotificationsCount);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            notificationList.clear();
            mNotificationsCount=0;

        }
    }

    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;

        invalidateOptionsMenu();
    }


    private ImageButton getNavButtonView(Toolbar toolbar)
    {
        for (int i = 0; i < toolbar.getChildCount(); i++)
            if(toolbar.getChildAt(i) instanceof ImageButton)
                return (ImageButton) toolbar.getChildAt(i);

        return null;
    }

}
