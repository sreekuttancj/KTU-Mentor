package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class AuthenticationActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextMobile;
    Spinner spinnerCourse;
    Spinner spinnerBranch;
    Spinner spinnerSemester;

    Button buttonSave;

    String name;
    String mobileNumber;
    String email;
    String[] course={
            "Course",
            "B.Tech"
    };
    String[] branch={
            "Branch",
            "AE",
            "AO",
            "AU",
            "BM",
            "CE",
            "CH",
            "CS",
            "EC",
            "EE",
            "IC",
            "IE",
            "IT",
            "ME",
            "MR"

    };
    String[] semester={
            "Semester",
            "S1&S2",
            "S3",
            "S4"

    };
    Toolbar mToolbar;

    String courseField;
    String semesterField;
    String branchField;

    PrefManager prefManager;
    Boolean isInternetPresent = false;
    LinearLayout linearLayoutMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("KTU Mentor");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefManager=new PrefManager(getApplicationContext());
        editTextName= (EditText) findViewById(R.id.editText_name);
        editTextEmail= (EditText) findViewById(R.id.editText_email);
        editTextMobile= (EditText) findViewById(R.id.editText_number);
        spinnerCourse= (Spinner) findViewById(R.id.spinner_course);
        spinnerBranch= (Spinner) findViewById(R.id.spinner_branch);
        spinnerSemester= (Spinner) findViewById(R.id.spinner_semester);
        linearLayoutMain= (LinearLayout) findViewById(R.id.li_main);

        if (prefManager.getLogin().equals("success")){

            editTextName.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            editTextMobile.setVisibility(View.GONE);

        }


        try {
            Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
            Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    email = account.name;
                    editTextEmail.setText(email);
                }
            }
        }
        catch (Exception e){

        }

        buttonSave= (Button) findViewById(R.id.button_done);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                courseField=spinnerCourse.getSelectedItem().toString();
                semesterField=spinnerSemester.getSelectedItem().toString();
                branchField=spinnerBranch.getSelectedItem().toString();

                if (!prefManager.getLogin().equals("success")) {


                    if (courseField.equals("Course") || branchField.equals("Branch") || semesterField.equals("Semester")) {
                        Snackbar.make(v, "Please fill out all fields", Snackbar.LENGTH_SHORT).show();
                    } else if (editTextName.length() == 0) {
                        editTextName.requestFocus();
                        editTextName.setError("Name can not be empty");
                        return;
                    } else if (editTextEmail.length() == 0) {
                        editTextEmail.requestFocus();
                        editTextEmail.setError("Email id can not be empty");
                        return;

                    } else if (editTextMobile.length() == 0) {
                        editTextMobile.requestFocus();
                        editTextMobile.setError("Mobile Number can not be empty");
                        return;

                    } else if (editTextMobile.length() < 10 || editTextMobile.length() > 13) {
                        Toast.makeText(getApplicationContext(), String.valueOf(editTextMobile.length()), Toast.LENGTH_SHORT).show();
                        editTextMobile.requestFocus();
                        editTextMobile.setError("Mobile Number must contain at least 10 digits");
                        return;

                    } else {

                        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
                        isInternetPresent = connectionDetector.isConnectingToInternet();

                        if (isInternetPresent) {

                        prefManager.setCourse(courseField);
                        prefManager.setBranch(branchField);
                        prefManager.setSemester(semesterField);


                        name = editTextName.getText().toString().trim();
                        email = editTextEmail.getText().toString().trim();
                        mobileNumber = editTextMobile.getText().toString().trim();



                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            final DatabaseReference myRef = database.getReference("Personal Information").child(mobileNumber);

                            myRef.child("name").setValue(name);
                            myRef.child("mob").setValue(mobileNumber);
                            myRef.child("email").setValue(email);
                            myRef.child("course").setValue(spinnerCourse.getSelectedItem().toString());
                            myRef.child("branch").setValue(spinnerBranch.getSelectedItem().toString());
                            myRef.child("semester").setValue(spinnerSemester.getSelectedItem().toString());

                            prefManager.setLoginBit("success");

                            Intent intentMain = new Intent(AuthenticationActivity.this, CategorySelectionActivity.class);
                            startActivity(intentMain);
                            finish();


                        } else {
                            Snackbar.make(linearLayoutMain, "No internet connection", Snackbar.LENGTH_SHORT).show();
                        }


                    }


                }
                else {
                    if (courseField.equals("Course") || branchField.equals("Branch") || semesterField.equals("Semester")) {
                        Snackbar.make(v, "Please fill out all fields", Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        prefManager.setCourse(courseField);
                        prefManager.setBranch(branchField);
                        prefManager.setSemester(semesterField);
                        prefManager.setLoginBit("success");
                        Intent intentMain = new Intent(AuthenticationActivity.this, CategorySelectionActivity.class);
                        startActivity(intentMain);
                        finish();

                    }
                }

            }
        });
        final List<String> courseList = new ArrayList<>(Arrays.asList(course));
        final List<String> branchList= new ArrayList<>(Arrays.asList(branch));
        final List<String> semesterList = new ArrayList<>(Arrays.asList(semester));

        final ArrayAdapter<String> courseAdapter=setSelection(courseList);
        courseAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerCourse.setAdapter(courseAdapter);

        final ArrayAdapter<String> branchAdapter=setSelection(branchList);
        branchAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerBranch.setAdapter(branchAdapter);

        final ArrayAdapter<String> semesterAdapter=setSelection(semesterList);
        semesterAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerSemester.setAdapter(semesterAdapter);





    }

    private ArrayAdapter<String> setSelection(List<String> sub){

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getApplicationContext(), R.layout.spinner_item,sub){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        return spinnerArrayAdapter;
    }



}
