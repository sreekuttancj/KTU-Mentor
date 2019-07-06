package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;

public class SamasyaActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button buttonInstall;
    private boolean isInternetPresent=false;
    ConnectionDetector connectionDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samasya);



        mToolbar= (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("Malayalam Dictionary");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonInstall= (Button) findViewById(R.id.button_install);
        connectionDetector=new ConnectionDetector(getApplicationContext());
        buttonInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent=connectionDetector.isConnectingToInternet();
                if (isInternetPresent){
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
                    Toast.makeText(getApplicationContext(),"No network connection",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
}
