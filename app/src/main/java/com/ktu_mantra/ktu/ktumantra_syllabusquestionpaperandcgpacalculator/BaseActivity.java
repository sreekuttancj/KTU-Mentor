package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Patterns;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.NotificationItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;


public class BaseActivity extends AppCompatActivity {

    FirebaseDatabase databaseNotification;
    DatabaseReference referenceNotification;
    public static List<NotificationItem> notificationList=new ArrayList<>();


    private ProgressDialog mProgressDialog;


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showMessageDialog(String title, String message) {
        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .create();
        ad.show();
    }




    public void getNotification(final String from){

        notificationList.clear();
        databaseNotification = FirebaseDatabase.getInstance();
        referenceNotification = databaseNotification.getReference("Notification");


        referenceNotification.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                notificationList.clear();
                for (DataSnapshot notificationObject:dataSnapshot.getChildren()) {

                    NotificationItem notification=notificationObject.getValue(NotificationItem.class);
                    notificationList.add(notification);

                }
                Collections.sort(notificationList, new Comparator<NotificationItem>() {
                    @Override
                    public int compare(NotificationItem lhs, NotificationItem rhs) {
                        return lhs.getPos().compareTo(rhs.getPos());
                    }
                });

                hideProgressDialog();

                if (from.equals("NotificationActivity")){
                    NotificationActivity.notificationAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

}
