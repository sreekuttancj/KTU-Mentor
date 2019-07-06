package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter.ViewPagerSyllabusAdapter;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;


public class SyllabusInnerActivity extends AppCompatActivity {

    TextView textViewModuleNo;

    ImageButton imageButtonLeft;
    ImageButton imageButtonRight;

    Toolbar mToolbar;
    ViewPager viewPagerSyllabus;
    ViewPagerSyllabusAdapter viewPagerSyllabusAdapter;
    Button buttonInstall;
    boolean isInternetpresent=false;

    PrefManager prefManager;
    ConnectionDetector connectionDetector;
    RelativeLayout relativeLayout;

//    private AdView mAdView;


    int position;
    int modulePOsition;

    String[] moduleNO={
            "Module I",
            "Module II",
            "Module III",
            "Module IV",
            "Module V",
            "Module VI",
            "Text Book & References"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_inner);

        Intent myIntent=getIntent();
        final String subject=myIntent.getStringExtra("subject");
        position=Integer.parseInt(myIntent.getStringExtra("position"));
        modulePOsition=Integer.parseInt(myIntent.getStringExtra("module_position"));

        mToolbar= (Toolbar) findViewById(R.id.toolbar);

        textViewModuleNo= (TextView) findViewById(R.id.textViewModuleNo);
        imageButtonLeft= (ImageButton) findViewById(R.id.imageLeft);
        imageButtonRight= (ImageButton) findViewById(R.id.imageRight);
        prefManager=new PrefManager(getApplicationContext());
        connectionDetector=new ConnectionDetector(getApplicationContext());
        relativeLayout= (RelativeLayout) findViewById(R.id.adView);
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

        viewPagerSyllabus= (ViewPager) findViewById(R.id.viewPagerSyllabus);
        viewPagerSyllabusAdapter=new ViewPagerSyllabusAdapter(SyllabusInnerActivity.this,position);
        viewPagerSyllabus.setAdapter(viewPagerSyllabusAdapter);
        viewPagerSyllabus.setCurrentItem(modulePOsition);

        viewPagerSyllabus.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                if (subject.equals("Engineering Graphics"))
                {
                    textViewModuleNo.setText(getApplicationContext().getResources().getStringArray(R.array.graphics_weight)[position]);

                }
                else if(subject.equals("Basics of Electronics Engineering"))
                {
                    textViewModuleNo.setText(getApplicationContext().getResources().getStringArray(R.array.basics_electronics_weight)[position]);

                }
                else if (subject.equals("Life Skills")){
                    textViewModuleNo.setText(moduleNO[position]);

                }
                else {
                    textViewModuleNo.setText(getApplicationContext().getResources().getStringArray(R.array.common_weight)[position]);

                }

                imageButtonLeftRight(position);


            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerSyllabus.setCurrentItem(getItem(-1), true);
            }
        });

        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerSyllabus.setCurrentItem(getItem(+1), true);
            }
        });



    }

    private int getItem(int i) {
        return viewPagerSyllabus.getCurrentItem() + i;
    }
    public void imageButtonLeftRight(int currentPage) {
        int lastPage = viewPagerSyllabus.getAdapter().getCount() - 1;

        if (currentPage != 0) {
            imageButtonLeft.setVisibility(View.VISIBLE);

        } else {
            imageButtonLeft.setVisibility(View.INVISIBLE);
        }

        if (currentPage == lastPage) {
            imageButtonRight.setVisibility(View.INVISIBLE);
        } else {
            imageButtonRight.setVisibility(View.VISIBLE);

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

//    @Override
//    public void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//        }
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mAdView != null) {
//            mAdView.resume();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
//        super.onDestroy();
//    }

}
