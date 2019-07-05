package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter.NotificationAdapter;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;

public class NotificationActivity extends BaseActivity {

    Toolbar mToolbar;

    RecyclerView recyclerViewNotification;
    public static NotificationAdapter notificationAdapter;

    String from="NotificationActivity";

    private AdView mAdView;
    boolean isInternetpresent=false;

    PrefManager prefManager;
    ConnectionDetector connectionDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notification");

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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


        prefManager=new PrefManager(getApplicationContext());
        connectionDetector=new ConnectionDetector(getApplicationContext());
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


        recyclerViewNotification = (RecyclerView) findViewById(R.id.recycler_notification);
        notificationAdapter = new NotificationAdapter(notificationList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewNotification.setLayoutManager(mLayoutManager);
        recyclerViewNotification.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNotification.setAdapter(notificationAdapter);


        showProgressDialog();
        getNotification(from);



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
