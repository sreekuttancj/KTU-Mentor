package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class CgpaFragment extends Fragment {


    RelativeLayout relativeLayoutMain;

    EditText editTextS1;
    EditText editTextS2;
    EditText editTextS3;
    EditText editTextS4;
    EditText editTextS5;
    EditText editTextS6;
    EditText editTextS7;
    EditText editTextS8;

    Button buttonClear;
    Button buttonCgpa;

    TextView textViewResult;

    static  int c1=24;
    static  int c2=23;
    static  int c3=24;
    static  int c4=23;
    static  int c5=23;
    static  int c6=23;
    static  int c7=22;
    static  int c8=18;

    double s1=0;
    double s2=0;
    double s3=0;
    double s4=0;
    double s5=0;
    double s6=0;
    double s7=0;
    double s8=0;

    double cgpa;

    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());

        View rowView;
        rowView=inflater.inflate(R.layout.fragment_cgpa, container, false);


        editTextS1= (EditText) rowView.findViewById(R.id.editTextS1);
        editTextS2= (EditText) rowView.findViewById(R.id.editTextS2);
        editTextS3= (EditText) rowView.findViewById(R.id.editTextS3);
        editTextS4= (EditText) rowView.findViewById(R.id.editTextS4);
        editTextS5= (EditText) rowView.findViewById(R.id.editTextS5);
        editTextS6= (EditText) rowView.findViewById(R.id.editTextS6);
        editTextS7= (EditText) rowView.findViewById(R.id.editTextS7);
        editTextS8= (EditText) rowView.findViewById(R.id.editTextS8);

        buttonCgpa= (Button) rowView.findViewById(R.id.buttonCgpa);
        buttonClear= (Button) rowView.findViewById(R.id.buttonClear);

        relativeLayoutMain= (RelativeLayout) rowView.findViewById(R.id.relayoutMain);

        textViewResult= (TextView) rowView.findViewById(R.id.textViewResult);

        relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

        buttonCgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"cgpa");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                if (editTextS1.getText().toString().equals("")&&editTextS2.getText().toString().equals("")&&editTextS3.getText().toString().equals("")&&editTextS4.getText().toString().equals("")&&editTextS5.getText().toString().equals("")&&editTextS6.getText().toString().equals("")&&editTextS7.getText().toString().equals("")&&editTextS8.getText().toString().equals("")){

                    textViewResult.setText("0.0");
                }
                else {

                    try {

                        if (editTextS1.getText().toString().equals("")) {
                            s1 = 0;
                            c1 = 0;
                        } else {
                            s1 = Double.parseDouble(editTextS1.getText().toString());
                        }

                        if (editTextS2.getText().toString().equals("")) {
                            s2 = 0;
                            c2 = 0;
                        } else {
                            s2 = Double.parseDouble(editTextS2.getText().toString());
                        }

                        if (editTextS3.getText().toString().equals("")) {
                            s3 = 0;
                            c3 = 0;
                        } else {
                            s3 = Double.parseDouble(editTextS3.getText().toString());
                        }

                        if (editTextS4.getText().toString().equals("")) {
                            s4 = 0;
                            c4 = 0;
                        } else {
                            s4 = Double.parseDouble(editTextS4.getText().toString());
                        }

                        if (editTextS5.getText().toString().equals("")) {
                            s5 = 0;
                            c5 = 0;
                        } else {
                            s5 = Double.parseDouble(editTextS5.getText().toString());
                        }

                        if (editTextS6.getText().toString().equals("")) {
                            s6 = 0;
                            c6 = 0;
                        } else {
                            s6 = Double.parseDouble(editTextS6.getText().toString());
                        }

                        if (editTextS7.getText().toString().equals("")) {
                            s7 = 0;
                            c7 = 0;
                        } else {
                            s7 = Double.parseDouble(editTextS7.getText().toString());
                        }

                        if (editTextS8.getText().toString().equals("")) {
                            s8 = 0;
                            c8 = 0;
                        } else {
                            s8 = Double.parseDouble(editTextS8.getText().toString());
                        }


                        cgpa = ((s1 * c1) + (s2 * c2) + (s3 * c3) + (s4 * c4) + (s5 * c5) + (s6 * c6) + (s7 * c7) + (s8 * c8)) / (c1 + c2 + c3 + c4 + c5 + c6 + c7 + c8);
                        String result = String.format("%.2f", cgpa);

                        textViewResult.setText(result);

                        hideSoftKeyboard();


                        c1 = 24;
                        c2 = 23;
                        c3 = 24;
                        c4 = 23;
                        c5 = 23;
                        c6 = 23;
                        c7 = 22;
                        c8 = 18;
                    } catch (Exception e) {
//                    finish();
                    }
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextS1.setText("");
                editTextS2.setText("");
                editTextS3.setText("");
                editTextS4.setText("");
                editTextS5.setText("");
                editTextS6.setText("");
                editTextS7.setText("");
                editTextS8.setText("");

                textViewResult.setText("");

            }
        });

        return rowView;


    }

    public void hideSoftKeyboard() {
//        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//        activity.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

    }




}
