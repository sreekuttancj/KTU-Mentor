package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;

public class ModuleActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar mToolbar;
    CardView cardViewM1;
    CardView cardViewM2;
    CardView cardViewM3;
    CardView cardViewM4;
    CardView cardViewM5;
    CardView cardViewM6;
    CardView cardViewTextBook;
    Button buttonInstall;

    String subject;
    String position;
    String modulePosition;
//    private AdView mAdView;
    boolean isInternetpresent=false;

    PrefManager prefManager;
    ConnectionDetector connectionDetector;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        prefManager=new PrefManager(getApplicationContext());
        connectionDetector=new ConnectionDetector(getApplicationContext());
        relativeLayout= (RelativeLayout) findViewById(R.id.adView);

        Intent intentFromSyllabus=getIntent();
        subject=intentFromSyllabus.getStringExtra("subject");
        position=intentFromSyllabus.getStringExtra("position");

        mToolbar.setTitle(subject);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        isInternetpresent=connectionDetector.isConnectingToInternet();
        if (isInternetpresent){

            if (prefManager.getCount()<1){
                relativeLayout.setVisibility(View.GONE);
            }
            else {
                relativeLayout.setVisibility(View.VISIBLE);

            }
        }
        else {
            relativeLayout.setVisibility(View.GONE);

        }

//
//        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
//
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new AdListener() {
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                super.onAdFailedToLoad(errorCode);
//
//                mAdView.setVisibility(View.GONE);
//            }
//        });
//

        buttonInstall= (Button) findViewById(R.id.button_install);
        buttonInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetpresent=connectionDetector.isConnectingToInternet();
                if (isInternetpresent) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    intent.setData(Uri.parse("https://goo.gl/eLHdcp"));
                    if (!MyStartActivity(intent))
                        //Market (Google play) app seems not installed, let's try to open a webbrowser
                        intent.setData(Uri.parse("https://goo.gl/eLHdcp"));
                    if (!MyStartActivity(intent)) {
                        //Well if this also fails, we have run out of options, inform the user.
                        Toast.makeText(getApplicationContext(), "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Snackbar.make(relativeLayout,"No internet connection",Snackbar.LENGTH_SHORT).show();

                }
            }
        });

        cardViewM1= (CardView) findViewById(R.id.cardView_module1);
        cardViewM2= (CardView) findViewById(R.id.cardView_module2);
        cardViewM3= (CardView) findViewById(R.id.cardView_module3);
        cardViewM4= (CardView) findViewById(R.id.cardView_module4);
        cardViewM5= (CardView) findViewById(R.id.cardView_module5);
        cardViewM6= (CardView) findViewById(R.id.cardView_module6);
        cardViewTextBook= (CardView) findViewById(R.id.cardView_textbook);

        cardViewM1.setOnClickListener(this);
        cardViewM2.setOnClickListener(this);
        cardViewM3.setOnClickListener(this);
        cardViewM4.setOnClickListener(this);
        cardViewM5.setOnClickListener(this);
        cardViewM6.setOnClickListener(this);
        cardViewTextBook.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.cardView_module1:
                modulePosition="0";
                break;
            case R.id.cardView_module2:
                modulePosition="1";

                break;
            case R.id.cardView_module3:
                modulePosition="2";

                break;
            case R.id.cardView_module4:
                modulePosition="3";

                break;
            case R.id.cardView_module5:
                modulePosition="4";

                break;
            case R.id.cardView_module6:
                modulePosition="5";

                break;
            case R.id.cardView_textbook:
                modulePosition="6";
                break;


        }

        Intent intentSyllabusInner=new Intent(ModuleActivity.this,SyllabusInnerActivity.class);
        intentSyllabusInner.putExtra("subject",subject);
        intentSyllabusInner.putExtra("position",position);
        intentSyllabusInner.putExtra("module_position",modulePosition);
        startActivity(intentSyllabusInner);

    }
    public  boolean MyStartActivity(Intent aIntent) {
        try
        {

            startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }
}
