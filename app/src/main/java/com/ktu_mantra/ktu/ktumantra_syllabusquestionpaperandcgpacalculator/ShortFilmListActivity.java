package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter.MoviesAdapter;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.NotificationItem;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ShortFilmData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class ShortFilmListActivity extends BaseActivity{

    RecyclerView recyclerViewMoviewList;
    MoviesAdapter mAdapter;
    Toolbar mToolbar;
    ConnectionDetector connectionDetector;
    boolean isInternetpresent=false;
    FirebaseDatabase databaseShortFilm;
    DatabaseReference referenceShortFilm;
    List<ShortFilmData> shortFilmList=new ArrayList<>();
    MaterialIntroView materialIntroView;
    ImageButton imageButtonSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_film);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Entertainment");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewMoviewList = (RecyclerView) findViewById(R.id.recycler_view_short_film);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ShortFilmListActivity.this);
        recyclerViewMoviewList.setLayoutManager(mLayoutManager);

        connectionDetector = new ConnectionDetector(getApplicationContext());
        databaseShortFilm = FirebaseDatabase.getInstance();
        referenceShortFilm = databaseShortFilm.getReference("ShortFilms");


//        prepareMovieShortFilmData();

        showProgressDialog();

        isInternetpresent = connectionDetector.isConnectingToInternet();
        if (isInternetpresent) {

            referenceShortFilm.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    shortFilmList.clear();
                    for (DataSnapshot shortFilmObject : dataSnapshot.getChildren()) {

                        ShortFilmData shortFilmData = shortFilmObject.getValue(ShortFilmData.class);
                        shortFilmList.add(shortFilmData);

                    }
                    Collections.sort(shortFilmList, new Comparator<ShortFilmData>() {
                        @Override
                        public int compare(ShortFilmData lhs, ShortFilmData rhs) {
                            return lhs.getPos().compareTo(rhs.getPos());
                        }
                    });
                    mAdapter = new MoviesAdapter(shortFilmList, ShortFilmListActivity.this);
                    recyclerViewMoviewList.setAdapter(mAdapter);

                    hideProgressDialog();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_LONG).show();
        }


        imageButtonSend = (ImageButton) findViewById(R.id.imageButton_send);
        materialIntroView = new MaterialIntroView.Builder(ShortFilmListActivity.this)
                .enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(300)
                .setTargetPadding(25)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText("Press this button to send your/friends Short Films to us")
                .setTarget(imageButtonSend)
                .setUsageId("intro_send_film") //THIS SHOULD BE UNIQUE ID
                .show();

        imageButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentInfo = new Intent(ShortFilmListActivity.this, SendShortFilmInfoActivity.class);
                startActivity(intentInfo);



            }
        });
    }



}
