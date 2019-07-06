package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.service;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class DownloadService extends Service {


    private static final String TAG = "Storage#DownloadService";

    /** Actions **/
    public static final String ACTION_DOWNLOAD = "action_download";
    public static final String ACTION_COMPLETED = "action_completed";
    public static final String ACTION_ERROR = "action_error";
    public static final String ACTION_PROGRESS = "action_progress";

    /** Extras **/
    public static final String EXTRA_DOWNLOAD_PATH = "extra_download_path";
    public static final String EXTRA_BYTES_DOWNLOADED = "extra_bytes_downloaded";

    private ProgressDialog pDialog;

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;


    private StorageReference mStorage;
    private int mNumTasks = 0;

    private String filename;
    public static final String filepath = "Mentor Question Papers";
    File myInternalFile;
    File myExternalFile;


    @Override
    public void onCreate() {
        super.onCreate();

        mStorage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ktu-mentor.appspot.com/question_paper/course/btech/s1_s2/");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand:" + intent + ":" + startId);

        if (ACTION_DOWNLOAD.equals(intent.getAction())) {

            final String downloadPath = intent.getStringExtra(EXTRA_DOWNLOAD_PATH);
            Log.d(TAG, ACTION_DOWNLOAD + ":" + downloadPath);

            filename = intent.getStringExtra("filename");
            if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
                ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
                File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
                myInternalFile = new File(directory, filename);
            } else {
                myExternalFile = new File(getExternalFilesDir(filepath), filename);
            }
            taskStarted();

            if (myExternalFile.exists()) {

                Intent broadcast = new Intent(ACTION_COMPLETED);
                broadcast.putExtra(EXTRA_DOWNLOAD_PATH, filepath);
                LocalBroadcastManager.getInstance(getApplicationContext())
                        .sendBroadcast(broadcast);
                taskCompleted();
            } else {

                mStorage.child(downloadPath).getFile(myExternalFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {



                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                        Log.d(TAG, "download:SUCCESS");
                        Intent broadcast = new Intent(ACTION_COMPLETED);
                        broadcast.putExtra(EXTRA_DOWNLOAD_PATH, downloadPath);
                        broadcast.putExtra(EXTRA_BYTES_DOWNLOADED, taskSnapshot.getTotalByteCount());
                        LocalBroadcastManager.getInstance(getApplicationContext())
                                .sendBroadcast(broadcast);

                        // Mark task completed
                        taskCompleted();
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {




//                        Double c= Double.valueOf((taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()));
                        Double a= (double) taskSnapshot.getBytesTransferred();
                        Double b= (double) taskSnapshot.getTotalByteCount();
                        int c= (int) ((a/b)*100);

                        Intent broadcast=new Intent(ACTION_PROGRESS);
                        broadcast.putExtra("percentage",String.valueOf(c));
                        LocalBroadcastManager.getInstance(getApplicationContext())
                                .sendBroadcast(broadcast);


                    }
                }).
                        addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.w(TAG, "download:FAILURE", exception);

                                // Send failure broadcast
                                Intent broadcast = new Intent(ACTION_ERROR);
                                broadcast.putExtra(EXTRA_DOWNLOAD_PATH, downloadPath);
                                LocalBroadcastManager.getInstance(getApplicationContext())
                                        .sendBroadcast(broadcast);

                                // Mark task completed
                                taskCompleted();
                            }
                        });



            }
        }
        return START_REDELIVER_INTENT;

        }



    private void taskStarted() {
        changeNumberOfTasks(1);
    }

    private void taskCompleted() {
        changeNumberOfTasks(-1);
    }
    private synchronized void changeNumberOfTasks(int delta) {
        Log.d(TAG, "changeNumberOfTasks:" + mNumTasks + ":" + delta);
        mNumTasks += delta;

        // If there are no tasks left, stop the service
        if (mNumTasks <= 0) {
            Log.d(TAG, "stopping");
            stopSelf();
        }
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_COMPLETED);
        filter.addAction(ACTION_ERROR);
        filter.addAction(ACTION_PROGRESS);
        return filter;
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

}
