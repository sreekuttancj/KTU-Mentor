package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.BaseActivity;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.DatabaseHandler;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;

import java.util.ArrayList;
import java.util.List;


public class SyllabusActivity extends BaseActivity {

    Toolbar mToolbar;
    RecyclerView recyclerViewSyllabus;
    SyllabusAdapter syllabusAdapter;

    DatabaseHandler databaseHandler;
    PrefManager prefManager;
    ConnectionDetector connectionDetector;

    FirebaseDatabase database;
    DatabaseReference myRef;
    SyllabusItem syllabusItem;
    List<SyllabusItem> syllabusItemList;

    String[] subject = new String[30];

    String course;
    String branch;
    String semester;

    int dbCheck;
    private boolean isInternetPresent = false;
    static final int SHARE_REQUEST = 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Syllabus");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final RelativeLayout relativeLayout = findViewById(R.id.re_main);

        databaseHandler = new DatabaseHandler(getApplicationContext());
        prefManager = new PrefManager(getApplicationContext());
        connectionDetector = new ConnectionDetector(getApplicationContext());

        database = FirebaseDatabase.getInstance();


        course = "btech";
        semester = prefManager.getSemester().toLowerCase();

        if (prefManager.getBranch().equals("CE") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "ce";
        } else if (prefManager.getBranch().equals("CS") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "cs";

        } else if (prefManager.getBranch().equals("CH") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "ch";

        } else if (prefManager.getBranch().equals("EC") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "ec";

        } else if (prefManager.getBranch().equals("EE") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "ee";

        } else if (prefManager.getBranch().equals("IC") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "ic";

        } else if (prefManager.getBranch().equals("IE") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "ie";

        } else if (prefManager.getBranch().equals("IT") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "it";

        } else if (prefManager.getBranch().equals("CS") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "cs";

        } else if (prefManager.getBranch().equals("ME") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "me";

        } else if (prefManager.getBranch().equals("AE") && !prefManager.getSemester().equals("S1&S2")) {
            if (prefManager.getSemester().equals("S4")) {
                branch = "ae";
            } else {
                branch = "ec";
            }
        } else if (prefManager.getBranch().equals("AO") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "ao";

        } else if (prefManager.getBranch().equals("AU") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "au";

        } else if (prefManager.getBranch().equals("BM") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "bm";

        } else if (prefManager.getBranch().equals("MR") && !prefManager.getSemester().equals("S1&S2")) {
            branch = "mr";

        }
        else {
            semester = "s1_s2";
        }

        Log.i("finale_check",semester);

        if (semester.equals("s1_s2")) {
            myRef = database.getReference().child("syllabus").child("course").child(course).child(semester);

        } else {
            myRef = database.getReference().child("syllabus").child("course").child(course).child(branch).child(semester);
        }

        syllabusItemList = new ArrayList<>();
        dbCheck = databaseHandler.checkDb(prefManager.getCourse(), prefManager.getBranch(), prefManager.getSemester());
        if (dbCheck == 0) {

            showProgressDialog();

            isInternetPresent = connectionDetector.isConnectingToInternet();
            if (isInternetPresent) {

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Log.i("check_value","enter");

                            syllabusItem = postSnapshot.getValue(SyllabusItem.class);
                            Log.i("check_value",syllabusItem.getName());
                            databaseHandler.addSyllabusContent(syllabusItem.getPos(), prefManager.getCourse(), prefManager.getBranch(), prefManager.getSemester(), syllabusItem.getName(), syllabusItem.getM1(), syllabusItem.getM2(), syllabusItem.getM3(), syllabusItem.getM4(), syllabusItem.getM5(), syllabusItem.getM6(), syllabusItem.getT_r());
                            hideProgressDialog();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("check_error",databaseError.toString());
                        Snackbar.make(relativeLayout, "Failed to load please try again", Snackbar.LENGTH_LONG).show();
                    }

                });
            } else {
                Snackbar.make(relativeLayout, "No network connection", Snackbar.LENGTH_LONG).show();
            }

        }


        //TODO create subject array for s4 and change condition loop here

        if (!prefManager.getSemester().equals("S1&S2")) {
            if (prefManager.getBranch().equals("CE")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.ce_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.ce_s4_subject);
                }
            } else if (prefManager.getBranch().equals("CS")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.cs_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.cs_s4_subject);
                }
            } else if (prefManager.getBranch().equals("CH")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.ch_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.ch_s4_subject);
                }
            } else if (prefManager.getBranch().equals("EC")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.ec_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.ec_s4_subject);
                }
            } else if (prefManager.getBranch().equals("EE")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.ee_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.ee_s4_subject);
                }
            } else if (prefManager.getBranch().equals("IC")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.ic_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.ic_s4_subject);
                }
            } else if (prefManager.getBranch().equals("IE")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.ie_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.ie_s4_subject);
                }
            } else if (prefManager.getBranch().equals("IT")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.it_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.it_s4_subject);
                }
            } else if (prefManager.getBranch().equals("ME")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.me_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.me_s4_subject);
                }
            } else if (prefManager.getBranch().equals("AE")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.ec_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.ae_s4_subject);
                }
            } else if (prefManager.getBranch().equals("AO")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.ao_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.ao_s4_subject);
                }
            } else if (prefManager.getBranch().equals("AU")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.au_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.au_s4_subject);
                }
            } else if (prefManager.getBranch().equals("BM")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.bm_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.bm_s4_subject);
                }
            } else if (prefManager.getBranch().equals("MR")) {
                if (prefManager.getSemester().equals("S3")) {
                    subject = getResources().getStringArray(R.array.mr_s3_subject);
                } else {
                    subject = getResources().getStringArray(R.array.mr_s4_subject);
                }
            }
        }
        else {
            subject = getResources().getStringArray(R.array.s1_s2_subjects);
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recyclerViewSyllabus = findViewById(R.id.recyclerViewSyllabus);
        syllabusAdapter = new SyllabusAdapter(SyllabusActivity.this, subject);
        recyclerViewSyllabus.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewSyllabus.setAdapter(syllabusAdapter);

        recyclerViewSyllabus.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerViewSyllabus, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intentSyllabusInner = new Intent(SyllabusActivity.this, ModuleActivity.class);
                intentSyllabusInner.putExtra("subject", subject[position]);
                intentSyllabusInner.putExtra("position", String.valueOf(position));
                startActivity(intentSyllabusInner);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private SyllabusActivity.ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final SyllabusActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.share_app:
                Uri imageUri = null;
                try {
                    imageUri = Uri.parse(MediaStore.Images.Media.insertImage(SyllabusActivity.this.getContentResolver(),
                            BitmapFactory.decodeResource(getResources(), R.drawable.share_image), null, null));
                } catch (NullPointerException e) {
                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download KTU Mentor Android App developed for KTU University students :https://goo.gl/1Gy9QT");
                sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                sendIntent.setType("image/*");
                sendIntent.setPackage("com.whatsapp");
                startActivityForResult(sendIntent, SHARE_REQUEST);

                return true;


            case R.id.rate_us:


                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("http://goo.gl/abcK2p"));
                if (!MyStartActivity(intent))
                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                    intent.setData(Uri.parse("http://goo.gl/abcK2p"));
                if (!MyStartActivity(intent)) {
                    //Well if this also fails, we have run out of options, inform the user.
                    Toast.makeText(getApplicationContext(), "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
                }

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public boolean MyStartActivity(Intent aIntent) {
        try {

            startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }


}
