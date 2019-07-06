package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter.QuestionPaperAdapter;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.DatabaseHandler;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.QuestionsItem;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.service.DownloadService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class QuestionPaperActivity extends BaseActivity {

    RecyclerView listViewQuestionPaper;
    QuestionPaperAdapter questionPaperAdapter;
    Toolbar mToolbar;

    private AdView mAdView;
    final  int REQUEST_CODE_ASK_PERMISSIONS = 200;
    private boolean isInternetPresent=false;

    FirebaseDatabase database;
    DatabaseReference myRef;

    PrefManager prefManager;
    QuestionsItem questionsItem;
    DatabaseHandler databaseHandler;
    ConnectionDetector connectionDetector;
    RelativeLayout relativeLayoutMain;
    List<QuestionsItem> questionsItemList;

    private BroadcastReceiver mDownloadReceiver;
    private ProgressDialog mProgressDialog;
    private static final String TAG = "Storage#MainActivity";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";
    private Uri mDownloadUrl = null;

    private ProgressDialog pDialog;

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;


    private StorageReference mStorageRef;

    boolean isInternetpresent;

    static final int SHARE_REQUEST = 1;  // The request code


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_paper);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Question Bank");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                mAdView.setVisibility(View.GONE);
            }
        });


        relativeLayoutMain= findViewById(R.id.re_main);
        prefManager=new PrefManager(getApplicationContext());
        databaseHandler=new DatabaseHandler(getApplicationContext());
        connectionDetector =new ConnectionDetector(getApplicationContext());

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


        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        if (savedInstanceState != null) {
            mDownloadUrl = savedInstanceState.getParcelable(KEY_DOWNLOAD_URL);
        }
        listViewQuestionPaper = findViewById(R.id.listViewQuestionSelection);
        listViewQuestionPaper.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        questionsItemList =new ArrayList<>();

        if (prefManager.getSemester().equals("S1&S2")){

            if (databaseHandler.checkQuestions()<1) {

                isInternetPresent=connectionDetector.isConnectingToInternet();
                if (isInternetPresent) {

                    showProgressDialog();
                    myRef = database.getReference().child("question_paper").child("btech").child("s1_s2");

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Log.e("db_data",postSnapshot.toString());

                                questionsItem = postSnapshot.getValue(QuestionsItem.class);


                                databaseHandler.addQuestionPaper(String.valueOf(questionsItem.getPos()), questionsItem.getName(), questionsItem.getYear());
                                questionsItemList = databaseHandler.getQuestionContent();

                                questionPaperAdapter = new QuestionPaperAdapter(getApplicationContext(), questionsItemList);
                                listViewQuestionPaper.setAdapter(questionPaperAdapter);


                                hideProgressDialog();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Snackbar.make(relativeLayoutMain,"No network connection",Snackbar.LENGTH_LONG).show();

                }
            }
            else {


                questionsItemList =databaseHandler.getQuestionContent();
                questionPaperAdapter=new QuestionPaperAdapter(getApplicationContext(), questionsItemList);
                listViewQuestionPaper.setAdapter(questionPaperAdapter);



            }
        }
        else {
            Snackbar.make(relativeLayoutMain,"Not Available",Snackbar.LENGTH_LONG).show();
        }

        listViewQuestionPaper.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), listViewQuestionPaper, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                int hasLocationPermission = ContextCompat.checkSelfPermission(QuestionPaperActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(QuestionPaperActivity.this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {


                        showMessageOKCancel("PLease grant permission for reading question paper",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(QuestionPaperActivity.this,
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }


                                });
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Need to revoke Storage permission to open question bank", Toast.LENGTH_SHORT).show();

                    }

                }
                 else {

                    String questionPaperName = databaseHandler.getQnameAppend(position);


                    File myExternalFile = new File(getExternalFilesDir(DownloadService.filepath), questionPaperName);

                    if (myExternalFile.exists()) {
                        Intent intentResult = new Intent(Intent.ACTION_VIEW);

                        Uri downloadURI = FileProvider.getUriForFile(QuestionPaperActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider",myExternalFile );
                        intentResult.setDataAndType(downloadURI, "application/pdf");
                        intentResult.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intentResult.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intentResult);

                    } else {
                        beginDownload(questionPaperName);

                    }


                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        // Download receiver
        mDownloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "downloadReceiver:onReceive:" + intent);
//                hideProgressDialog();

                if (DownloadService.ACTION_COMPLETED.equals(intent.getAction())) {
                    String path = intent.getStringExtra(DownloadService.EXTRA_DOWNLOAD_PATH);

                   Intent intentResult = new Intent(Intent.ACTION_VIEW);

                   File myExternalFile = new File(getExternalFilesDir(DownloadService.filepath), path);

                    questionPaperAdapter.notifyDataSetChanged();
                    Uri downloadURI = FileProvider.getUriForFile(QuestionPaperActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",myExternalFile );
                    intentResult.setDataAndType(downloadURI, "application/pdf");
                    intentResult.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intentResult.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intentResult);

                    dismissDialog(progress_bar_type);

                }

                if (DownloadService.ACTION_PROGRESS.equals(intent.getAction())){
                    int percentage=Integer.parseInt(intent.getStringExtra("percentage"));
                    pDialog.setProgress(percentage);

                }

                if (DownloadService.ACTION_ERROR.equals(intent.getAction())) {
                    String path = intent.getStringExtra(DownloadService.EXTRA_DOWNLOAD_PATH);

                    // Alert failure
                    showMessageDialog("Error", String.format(Locale.getDefault(),
                            "Failed to download from %s", path));
                }
            }
        };

    }

    private void beginDownload(String qpaperName) {

                String path = qpaperName ;

        isInternetPresent=connectionDetector.isConnectingToInternet();
        if (isInternetPresent) {
            // Kick off download service
            Intent intent = new Intent(this, DownloadService.class);
            intent.setAction(DownloadService.ACTION_DOWNLOAD);
            intent.putExtra("filename", qpaperName);
            intent.putExtra(DownloadService.EXTRA_DOWNLOAD_PATH, path);
            startService(intent);

            // Show loading spinner
            showDialog(progress_bar_type);
        }
        else {
            Snackbar.make(relativeLayoutMain,"No network connection",Snackbar.LENGTH_SHORT).show();
        }
    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private QuestionPaperActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final QuestionPaperActivity.ClickListener clickListener) {
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
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(false);
                pDialog.setOnKeyListener(new Dialog.OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface arg0, int keyCode,
                                         KeyEvent event) {
                        // TODO Auto-generated method stub
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                            pDialog.dismiss();
                        }
                        return true;
                    }
                });

                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public boolean onOptionsItemSelected (MenuItem item) {


        switch (item.getItemId()) {

            case R.id.share_app:
                Uri imageUri = null;
                try {
                    imageUri = Uri.parse(MediaStore.Images.Media.insertImage(QuestionPaperActivity.this.getContentResolver(),
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

    @Override
    public void onStart() {
        super.onStart();

        // Register download receiver
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mDownloadReceiver, DownloadService.getIntentFilter());
    }

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_DOWNLOAD_URL, mDownloadUrl);
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
    public void onStop() {
        super.onStop();

        // Unregister download receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDownloadReceiver);
    }


    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(QuestionPaperActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
