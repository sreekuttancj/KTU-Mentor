package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;


public class AdblockActivity extends AppCompatActivity {

    Toolbar mToolbar;
    PrefManager prefManager;
//    public static final String MyPREFERENCES = "MyPrefs" ;
//    public static final String COUNT = "countKey";

    int checkCount;
    Boolean isInternetPresent = false;
    ConnectionDetector connectionDetector;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    TextView textViewCount;
    Button buttonShare;
    static final int SHARE_REQUEST = 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adblock);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        textViewCount= (TextView) findViewById(R.id.textView_count);
        buttonShare= (Button) findViewById(R.id.button_share);
//        pref = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
//        editor = pref.edit();
        prefManager=new PrefManager(getApplicationContext());
        connectionDetector=new ConnectionDetector(getApplicationContext());

        mToolbar.setTitle("AdBlock");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkCount=prefManager.getCount();
        if (checkCount>0){
            textViewCount.setText(String.valueOf(checkCount));
        }
        else {
            buttonShare.setText("Ads Blocked");
            buttonShare.setEnabled(false);
            textViewCount.setVisibility(View.GONE);
        }

        final RelativeLayout relativeLayoutMain= (RelativeLayout) findViewById(R.id.relayout_main);

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = connectionDetector.isConnectingToInternet();

                if (prefManager.getCount() > 0) {

                    if (isInternetPresent) {

                        Uri imageUri = null;
                        try {
                            imageUri = Uri.parse(MediaStore.Images.Media.insertImage(AdblockActivity.this.getContentResolver(),
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

                        checkCount--;

                        prefManager.setCount(checkCount);


                    } else {
                        Snackbar.make(relativeLayoutMain, "No Internet Connection", Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    buttonShare.setText("Ads Blocked");
                    textViewCount.setVisibility(View.GONE);
                    buttonShare.setEnabled(false);
                }
            }

        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==SHARE_REQUEST){


                        if (checkCount>=0) {
                            textViewCount.setText(String.valueOf(prefManager.getCount()));
                        }

        }
    }


}
