package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.SendFilmInfo;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

public class SendShortFilmInfoActivity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputMobileNumber,inputDuration,inputYoutubeLink,inputCollegeName;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutMobileNumber,inputLayoutduration,inputLayoutyoutubeLink,inputLayoutcollegeName;
    private Button buttonSend;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ConnectionDetector connectionDetector;
    Boolean isInternetPresent = false;
    AlertDialog.Builder alertDialogBuilder;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_shortfilm_info);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("Short Film Info");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Short Film Info");
        connectionDetector =new ConnectionDetector(getApplicationContext());

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobileNumber = (TextInputLayout) findViewById(R.id.input_layout_mob);
        inputLayoutduration = (TextInputLayout) findViewById(R.id.input_layout_duration);
        inputLayoutyoutubeLink = (TextInputLayout) findViewById(R.id.input_layout_youtube_link);
        inputLayoutcollegeName = (TextInputLayout) findViewById(R.id.input_layout_college_name);

        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputMobileNumber = (EditText) findViewById(R.id.input_mob);
        inputDuration = (EditText) findViewById(R.id.input_duration);
        inputYoutubeLink = (EditText) findViewById(R.id.input_youtube_link);
        inputCollegeName = (EditText) findViewById(R.id.input_college_name);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputDuration.addTextChangedListener(new MyTextWatcher(inputDuration));
        inputMobileNumber.addTextChangedListener(new MyTextWatcher(inputMobileNumber));
        inputYoutubeLink.addTextChangedListener(new MyTextWatcher(inputYoutubeLink));

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please Email your Short Film cover image to ktumentor@gmail.com");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(), "We will notify you once we upload your Short Film !", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

        buttonSend = (Button) findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });


    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (!validateDuration()) {
            return;
        }
        if (!validateYoutube()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validateMobile()) {
            return;
        }

        isInternetPresent=connectionDetector.isConnectingToInternet();

        if (isInternetPresent) {
            saveToDb();

        }
        else {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();

        }
    }

    private void saveToDb(){

        SendFilmInfo sendFilmInfo=new SendFilmInfo();
        sendFilmInfo.setName(inputName.getText().toString());
        sendFilmInfo.setDuration(inputDuration.getText().toString());
        sendFilmInfo.setYoutube(inputYoutubeLink.getText().toString());
        sendFilmInfo.setCollegeName(inputCollegeName.getText().toString());
        sendFilmInfo.setEmail(inputEmail.getText().toString());
        sendFilmInfo.setMobile(inputMobileNumber.getText().toString());

        myRef.child(sendFilmInfo.getName()).setValue(sendFilmInfo);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {
        if (inputMobileNumber.getText().toString().trim().isEmpty()) {
            inputLayoutMobileNumber.setError(getString(R.string.err_msg_mobile));
            requestFocus(inputMobileNumber);
            return false;
        } else {
            inputLayoutMobileNumber.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDuration() {
        if (inputDuration.getText().toString().trim().isEmpty()) {
            inputLayoutduration.setError(getString(R.string.err_msg_duration));
            requestFocus(inputDuration);
            return false;
        } else {
            inputLayoutduration.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateYoutube() {
        if (inputYoutubeLink.getText().toString().trim().isEmpty()) {
            inputLayoutyoutubeLink.setError(getString(R.string.err_msg_youtube));
            requestFocus(inputYoutubeLink);
            return false;
        } else {
            inputLayoutyoutubeLink.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_mob:
                    validateMobile();
                    break;
                case R.id.input_duration:
                    validateDuration();
                    break;
                case R.id.input_youtube_link:
                    validateYoutube();
                    break;
            }
        }
    }



}
