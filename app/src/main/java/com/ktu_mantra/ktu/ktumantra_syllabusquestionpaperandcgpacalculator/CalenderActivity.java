package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter.CalenderAdapter;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.DatabaseHandler;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.CalenderItem;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class CalenderActivity extends AppCompatActivity{

    Toolbar mToolbar;

    DatabaseHandler databaseHandler;
    CalenderAdapter calenderAdapter;
    ListView listViewCalender;
    List<CalenderItem> calenderItemList;
    ArrayList<String> arrayListHeader =new ArrayList<>();

    private AdView mAdView;
    PrefManager prefManager;
    boolean isInternetpresent=false;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Academic Calendar");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        prefManager=new PrefManager(getApplicationContext());

        connectionDetector=new ConnectionDetector(getApplicationContext());


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


        isInternetpresent=connectionDetector.isConnectingToInternet();
        if (isInternetpresent){

            if (prefManager.getCount()<1){
                mAdView.setVisibility(View.GONE);
            }
            else {
                mAdView.setVisibility(View.VISIBLE);

            }
        }
        else {
            mAdView.setVisibility(View.GONE);

        }

        databaseHandler=new DatabaseHandler(getApplicationContext());

        calenderItemList=databaseHandler.getAllCalenderInfo();



        calenderAdapter =new CalenderAdapter(this);
        listViewCalender = (ListView) findViewById(R.id.listView_calender);


        for (int i = 0; i < calenderItemList.size(); i++) {
            CalenderItem calenderItem=calenderItemList.get(i);
            if (!arrayListHeader.contains(calenderItem.getMonth())){

                  calenderAdapter.addSectionHeaderItem(calenderItem);
                  arrayListHeader.add(calenderItem.getMonth());

            }
            calenderAdapter.addItem(calenderItem);

        }
        listViewCalender.setAdapter(calenderAdapter);
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

