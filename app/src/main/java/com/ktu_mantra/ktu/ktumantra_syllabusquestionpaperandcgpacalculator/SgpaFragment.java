package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SgpaFragment extends Fragment {

    Spinner spinnerSubject1;
    Spinner spinnerSubject2;
    Spinner spinnerSubject3;
    Spinner spinnerSubject4;
    Spinner spinnerSubject5;
    Spinner spinnerSubject6;
    Spinner spinnerSubject7;
    Spinner spinnerSubject8;
    Spinner spinnerSubject9;

    Spinner spinnerGrade1;
    Spinner spinnerGrade2;
    Spinner spinnerGrade3;
    Spinner spinnerGrade4;
    Spinner spinnerGrade5;
    Spinner spinnerGrade6;
    Spinner spinnerGrade7;
    Spinner spinnerGrade8;
    Spinner spinnerGrade9;

    Button buttonClear;
    Button buttonSgpa;

    Button buttonClearOther;
    Button buttonSgpaOther;

    int credit1;
    int credit2;
    int credit3;
    int credit4;
    int credit5;
    int credit6;
    int credit7;
    int credit8;
    int credit9;

    int gradePoint1;
    int gradePoint2;
    int gradePoint3;
    int gradePoint4;
    int gradePoint5;
    int gradePoint6;
    int gradePoint7;
    int gradePoint8;
    int gradePoint9;

    TextView textViewSemSubject1;
    TextView textViewSemSubject2;
    TextView textViewSemSubject3;
    TextView textViewSemSubject4;
    TextView textViewSemSubject5;
    Spinner  SpinnerSubject6_sem;
    TextView textViewSemLab1;
    TextView textViewSemLab2;


    RelativeLayout relativeLayoutS1S2;
    RelativeLayout relativeLayoutOther;

    private FirebaseAnalytics mFirebaseAnalytics;
    PrefManager prefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        prefManager=new PrefManager(getActivity());

        View rowView;
        rowView = inflater.inflate(R.layout.fragment_sgpa, container, false);

        relativeLayoutS1S2= (RelativeLayout) rowView.findViewById(R.id.re_s1_s2);
        relativeLayoutOther= (RelativeLayout) rowView.findViewById(R.id.re_other);
        final List<String> grade = new ArrayList<>(Arrays.asList(getActivity().getResources().getStringArray(R.array.grade)));


        if (prefManager.getSemester().equals("S1&S2")) {

            relativeLayoutOther.setVisibility(View.GONE);
            relativeLayoutS1S2.setVisibility(View.VISIBLE);

            String[] subject1 = {
                    "Subject 1",
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[0],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[1]
            };

            String[] subject2 = {
                    "Subject 2",
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[2],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[3]
            };
            String[] subject3 = {
                    "Subject 3",
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[4],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[5]
            };
            String[] subject4 = {
                    "Subject 4",
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[6],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[7],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[8],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[9],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[10],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[11],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[12],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[13],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[14],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[15]


            };

            String[] subject5 = {
                    "Subject 5",
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[12]
                    , getActivity().getResources().getStringArray(R.array.subjects_sgpa)[13],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[14],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[15]

            };

            String[] subject6 = {
                    "Subject 6",
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[16],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[17]

            };
            String[] subject7 = {
                    "Lab 1",
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[18],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[19]

            };
            final String[] subject8 = {
                    "Lab 2",
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[20],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[21],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[22],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[23],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[24],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[25]


            };

            final String[] subject9 = {
                    "Lab 3",
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[20],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[21],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[22],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[23],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[24],
                    getActivity().getResources().getStringArray(R.array.subjects_sgpa)[25]


            };


            final List<String> sub1List1 = new ArrayList<>(Arrays.asList(subject1));
            final List<String> sub1List2 = new ArrayList<>(Arrays.asList(subject2));
            final List<String> sub1List3 = new ArrayList<>(Arrays.asList(subject3));
            final List<String> sub1List4 = new ArrayList<>(Arrays.asList(subject4));
            final List<String> sub1List5 = new ArrayList<>(Arrays.asList(subject5));
            final List<String> sub1List6 = new ArrayList<>(Arrays.asList(subject6));
            final List<String> sub1List7 = new ArrayList<>(Arrays.asList(subject7));
            final List<String> sub1List8 = new ArrayList<>(Arrays.asList(subject8));
            final List<String> sub1List9 = new ArrayList<>(Arrays.asList(subject9));




            spinnerSubject1 = (Spinner) rowView.findViewById(R.id.spinner_subject1);
            spinnerSubject2 = (Spinner) rowView.findViewById(R.id.spinner_subject2);
            spinnerSubject3 = (Spinner) rowView.findViewById(R.id.spinner_subject3);
            spinnerSubject4 = (Spinner) rowView.findViewById(R.id.spinner_subject4);
            spinnerSubject5 = (Spinner) rowView.findViewById(R.id.spinner_subject5);
            spinnerSubject6 = (Spinner) rowView.findViewById(R.id.spinner_subject6);
            spinnerSubject7 = (Spinner) rowView.findViewById(R.id.spinner_subject7);
            spinnerSubject8 = (Spinner) rowView.findViewById(R.id.spinner_subject8);
            spinnerSubject9 = (Spinner) rowView.findViewById(R.id.spinner_subject9);


            // Initializing an ArrayAdapter
            final ArrayAdapter<String> sub1 = setSelection(sub1List1);
            sub1.setDropDownViewResource(R.layout.spinner_item);
            spinnerSubject1.setAdapter(sub1);

            ArrayAdapter<String> sub2 = setSelection(sub1List2);
            sub2.setDropDownViewResource(R.layout.spinner_item);
            spinnerSubject2.setAdapter(sub2);

            ArrayAdapter<String> sub3 = setSelection(sub1List3);
            sub3.setDropDownViewResource(R.layout.spinner_item);
            spinnerSubject3.setAdapter(sub3);

            ArrayAdapter<String> sub4 = setSelection(sub1List4);
            sub4.setDropDownViewResource(R.layout.spinner_item);
            spinnerSubject4.setAdapter(sub4);

            ArrayAdapter<String> sub5 = setSelection(sub1List5);
            sub5.setDropDownViewResource(R.layout.spinner_item);
            spinnerSubject5.setAdapter(sub5);

            ArrayAdapter<String> sub6 = setSelection(sub1List6);
            sub6.setDropDownViewResource(R.layout.spinner_item);
            spinnerSubject6.setAdapter(sub6);

            ArrayAdapter<String> sub7 = setSelection(sub1List7);
            sub7.setDropDownViewResource(R.layout.spinner_item);
            spinnerSubject7.setAdapter(sub7);

            ArrayAdapter<String> sub8 = setSelection(sub1List8);
            sub8.setDropDownViewResource(R.layout.spinner_item);
            spinnerSubject8.setAdapter(sub8);

            ArrayAdapter<String> sub9 = setSelection(sub1List9);
            sub9.setDropDownViewResource(R.layout.spinner_item);
            spinnerSubject9.setAdapter(sub9);


            spinnerGrade1=(Spinner) rowView.findViewById(R.id.spinner_grade1);
            spinnerGrade2=(Spinner) rowView.findViewById(R.id.spinner_grade2);
            spinnerGrade3=(Spinner) rowView.findViewById(R.id.spinner_grade3);
            spinnerGrade4=(Spinner) rowView.findViewById(R.id.spinner_grade4);
            spinnerGrade5=(Spinner) rowView.findViewById(R.id.spinner_grade5);
            spinnerGrade6=(Spinner) rowView.findViewById(R.id.spinner_grade6);
            spinnerGrade7=(Spinner) rowView.findViewById(R.id.spinner_grade7);
            spinnerGrade8=(Spinner) rowView.findViewById(R.id.spinner_grade8);
            spinnerGrade9=(Spinner) rowView.findViewById(R.id.spinner_grade9);



            spinnerSubject1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position > 0){
                        // Notify the selected item text
                        credit1=4;

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }


            });

            spinnerSubject2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    if(position > 0){
                        // Notify the selected item text
                        credit2=4;

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

            spinnerSubject3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selectedItemText = (String) parent.getItemAtPosition(position);

                    if(position > 0){
                        // Notify the selected item text

                        if (selectedItemText.equals(getActivity().getResources().getStringArray(R.array.subjects_sgpa)[4])){

                            credit3=4;
                        }
                        if (selectedItemText.equals(getActivity().getResources().getStringArray(R.array.subjects_sgpa)[5])){

                            credit3=3;
                        }


                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

            spinnerSubject4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    if(position > 0){
                        // Notify the selected item text
                        credit4=3;

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

            spinnerSubject5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position > 0){
                        // Notify the selected item text
                        credit5=3;

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }


            });

            spinnerSubject6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position > 0){
                        // Notify the selected item text
                        credit6=3;

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }


            });
            spinnerSubject7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position > 0){
                        // Notify the selected item text
                        credit7=1;

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }


            });
            spinnerSubject8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selectedItemText = (String) parent.getItemAtPosition(position);

                    if(position > 0){
                        // Notify the selected item text
                        credit8=1;

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }


            });
            spinnerSubject9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position > 0){
                        // Notify the selected item text
                        credit9=1;


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });

        }

        else {
            relativeLayoutS1S2.setVisibility(View.GONE);
            relativeLayoutOther.setVisibility(View.VISIBLE);



            textViewSemSubject1= (TextView) rowView.findViewById(R.id.textView_s_subject1);
            textViewSemSubject2= (TextView) rowView.findViewById(R.id.textView_s_subject2);
            textViewSemSubject3= (TextView) rowView.findViewById(R.id.textView_s_subject3);
            textViewSemSubject4= (TextView) rowView.findViewById(R.id.textView_s_subject4);
            textViewSemSubject5= (TextView) rowView.findViewById(R.id.textView_s_subject5);
            textViewSemLab1= (TextView) rowView.findViewById(R.id.textView_s_lab1);
            textViewSemLab2= (TextView) rowView.findViewById(R.id.textView_s_lab2);


            if (prefManager.getBranch().equals("AE")||prefManager.getBranch().equals("EC")){
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.ec_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.ec_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.ec_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.ec_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.ec_s3_subject)[4]);
                if (prefManager.getBranch().equals("EC")) {
                    textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.ec_s3_subject)[7]);
                    textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.ec_s3_subject)[8]);
                }
                else {
                    textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.ae_s3_lab)[0]);
                    textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.ae_s3_lab)[1]);
                }

            }
            else if (prefManager.getBranch().equals("CS")){
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.cs_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.cs_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.cs_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.cs_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.cs_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.cs_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.cs_s3_subject)[8]);


            }

            else if (prefManager.getBranch().equals("CE")){
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.ce_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.ce_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.ce_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.ce_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.ce_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.ce_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.ce_s3_subject)[8]);

            }

            else if (prefManager.getBranch().equals("CH")){
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.ch_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.ch_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.ch_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.ch_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.ch_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.ch_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.ch_s3_subject)[8]);

            }
            else if (prefManager.getBranch().equals("EE")){
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.ee_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.ee_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.ee_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.ee_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.ee_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.ee_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.ee_s3_subject)[8]);

            }

            else if (prefManager.getBranch().equals("IC")){
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.ic_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.ic_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.ic_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.ic_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.ic_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.ic_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.ic_s3_subject)[8]);

            }

            else if (prefManager.getBranch().equals("IE")){
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.ie_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.ie_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.ie_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.ie_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.ie_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.ie_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.ie_s3_subject)[8]);

            }

            else if (prefManager.getBranch().equals("IT")){
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.it_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.it_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.it_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.it_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.it_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.it_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.it_s3_subject)[8]);

            }

            else if (prefManager.getBranch().equals("ME")) {
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.me_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.me_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.me_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.me_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.me_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.me_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.me_s3_subject)[8]);


            }
            else if (prefManager.getBranch().equals("AO")) {
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.ao_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.ao_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.ao_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.ao_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.ao_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.ao_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.ao_s3_subject)[8]);
            }
                else if (prefManager.getBranch().equals("AU")) {
                    textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.au_s3_subject)[0]);
                    textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.au_s3_subject)[1]);
                    textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.au_s3_subject)[2]);
                    textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.au_s3_subject)[3]);
                    textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.au_s3_subject)[4]);
                    textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.au_s3_subject)[7]);
                    textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.au_s3_subject)[8]);


                }

            else if (prefManager.getBranch().equals("BM")) {
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.bm_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.bm_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.bm_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.bm_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.bm_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.bm_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.bm_s3_subject)[8]);


            }
            else if (prefManager.getBranch().equals("MR")) {
                textViewSemSubject1.setText(getActivity().getResources().getStringArray(R.array.mr_s3_subject)[0]);
                textViewSemSubject2.setText(getActivity().getResources().getStringArray(R.array.mr_s3_subject)[1]);
                textViewSemSubject3.setText(getActivity().getResources().getStringArray(R.array.mr_s3_subject)[2]);
                textViewSemSubject4.setText(getActivity().getResources().getStringArray(R.array.mr_s3_subject)[3]);
                textViewSemSubject5.setText(getActivity().getResources().getStringArray(R.array.mr_s3_subject)[4]);
                textViewSemLab1.setText(getActivity().getResources().getStringArray(R.array.mr_s3_subject)[7]);
                textViewSemLab2.setText(getActivity().getResources().getStringArray(R.array.mr_s3_subject)[8]);


            }



            credit1 = Integer.parseInt(getActivity().getResources().getStringArray(R.array.s3_credit)[0]);
                credit2 = Integer.parseInt(getActivity().getResources().getStringArray(R.array.s3_credit)[1]);
                credit3 = Integer.parseInt(getActivity().getResources().getStringArray(R.array.s3_credit)[2]);
                credit4 = Integer.parseInt(getActivity().getResources().getStringArray(R.array.s3_credit)[3]);
                credit5 = Integer.parseInt(getActivity().getResources().getStringArray(R.array.s3_credit)[4]);
                credit6 = Integer.parseInt(getActivity().getResources().getStringArray(R.array.s3_credit)[5]);
                credit7 = Integer.parseInt(getActivity().getResources().getStringArray(R.array.s3_credit)[7]);
                credit8 = Integer.parseInt(getActivity().getResources().getStringArray(R.array.s3_credit)[8]);

                SpinnerSubject6_sem = (Spinner) rowView.findViewById(R.id.spinner_s_subject6);

                String[] subject6 = {
                        "Subject 6",
                        getActivity().getResources().getStringArray(R.array.ce_s3_subject)[5],
                        getActivity().getResources().getStringArray(R.array.ce_s3_subject)[6]
                };

                final List<String> sub1List1 = new ArrayList<>(Arrays.asList(subject6));

                final ArrayAdapter<String> sub6 = setSelection(sub1List1);
                sub6.setDropDownViewResource(R.layout.spinner_item);
                SpinnerSubject6_sem.setAdapter(sub6);


                spinnerGrade1 = (Spinner) rowView.findViewById(R.id.spinner_s_grade1);
                spinnerGrade2 = (Spinner) rowView.findViewById(R.id.spinner_s_grade2);
                spinnerGrade3 = (Spinner) rowView.findViewById(R.id.spinner_s_grade3);
                spinnerGrade4 = (Spinner) rowView.findViewById(R.id.spinner_s_grade4);
                spinnerGrade5 = (Spinner) rowView.findViewById(R.id.spinner_s_grade5);
                spinnerGrade6 = (Spinner) rowView.findViewById(R.id.spinner_s_grade6);
                spinnerGrade7 = (Spinner) rowView.findViewById(R.id.spinner_s_lab_grade1);
                spinnerGrade8 = (Spinner) rowView.findViewById(R.id.spinner_s_lab_grade2);

            }




        ArrayAdapter<String> gradeList= setSelection(grade);
        gradeList.setDropDownViewResource(R.layout.spinner_item);
        spinnerGrade1.setAdapter(gradeList);
        spinnerGrade2.setAdapter(gradeList);
        spinnerGrade3.setAdapter(gradeList);
        spinnerGrade4.setAdapter(gradeList);
        spinnerGrade5.setAdapter(gradeList);
        spinnerGrade6.setAdapter(gradeList);
        spinnerGrade7.setAdapter(gradeList);
        spinnerGrade8.setAdapter(gradeList);
        if (prefManager.getSemester().equals("S1&S2")) {
            spinnerGrade9.setAdapter(gradeList);

            spinnerGrade9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    gradePoint9=  getGradePoint(selectedItemText);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

        spinnerGrade1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                gradePoint1=  getGradePoint(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerGrade2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                gradePoint2=  getGradePoint(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerGrade3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                gradePoint3=  getGradePoint(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerGrade4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                gradePoint4=  getGradePoint(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerGrade5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                gradePoint5=  getGradePoint(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerGrade6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                gradePoint6=  getGradePoint(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerGrade7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                gradePoint7=  getGradePoint(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerGrade8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                gradePoint8=  getGradePoint(selectedItemText);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonClear= (Button) rowView.findViewById(R.id.buttonClear);
        buttonSgpa= (Button) rowView.findViewById(R.id.buttonSgpa);
        buttonSgpaOther= (Button) rowView.findViewById(R.id.buttonSgpa_s);
        buttonClearOther= (Button) rowView.findViewById(R.id.buttonClear_s);



        buttonSgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"sgpa");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


                if (spinnerSubject1.getSelectedItem().equals("Subject 1")||spinnerSubject2.getSelectedItem().equals("Subject 2")||spinnerSubject3.getSelectedItem().equals("Subject 4")||spinnerSubject2.getSelectedItem().equals("Subject 4")||spinnerSubject5.getSelectedItem().equals("Subject 5")||spinnerSubject6.getSelectedItem().equals("Subject 6")||spinnerSubject7.getSelectedItem().equals("Lab 1")||spinnerSubject8.getSelectedItem().equals("Lab 2")||spinnerSubject9.getSelectedItem().equals("Lab 3")||spinnerGrade1.getSelectedItem().equals("Grade")||spinnerGrade2.getSelectedItem().equals("Grade")||spinnerGrade3.getSelectedItem().equals("Grade")||spinnerGrade4.getSelectedItem().equals("Grade")||spinnerGrade5.getSelectedItem().equals("Grade")||spinnerGrade6.getSelectedItem().equals("Grade")||spinnerGrade7.getSelectedItem().equals("Grade")||spinnerGrade8.getSelectedItem().equals("Grade")||spinnerGrade9.getSelectedItem().equals("Grade")){
                    Toast.makeText(getActivity(),"Please fill out all fields",Toast.LENGTH_SHORT).show();
                }
                else {

                    double sumOfValues=(credit1*gradePoint1)+(credit2*gradePoint2)+(credit3*gradePoint3)+(credit4*gradePoint4)+(credit5*gradePoint5)+(credit6*gradePoint6)+(credit7*gradePoint7)+(credit8*gradePoint8)+(credit9*gradePoint9);
                    double sumOfCredits=credit1+credit2+credit3+credit4+credit5+credit6+credit7+credit8+credit9;
                    double sgpa=sumOfValues/sumOfCredits;

                    showGpaDialog(sgpa);


                }


            }
        });

        buttonSgpaOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SpinnerSubject6_sem.getSelectedItem().equals("Subject 6")||spinnerGrade1.getSelectedItem().equals("Grade")||spinnerGrade2.getSelectedItem().equals("Grade")||spinnerGrade3.getSelectedItem().equals("Grade")||spinnerGrade4.getSelectedItem().equals("Grade")||spinnerGrade5.getSelectedItem().equals("Grade")||spinnerGrade6.getSelectedItem().equals("Grade")||spinnerGrade7.getSelectedItem().equals("Grade")||spinnerGrade8.getSelectedItem().equals("Grade")){
                    Toast.makeText(getActivity(),"Please fill out all fields",Toast.LENGTH_SHORT).show();

                }
                else {
                    double sumOfValues=(credit1*gradePoint1)+(credit2*gradePoint2)+(credit3*gradePoint3)+(credit4*gradePoint4)+(credit5*gradePoint5)+(credit6*gradePoint6)+(credit7*gradePoint7)+(credit8*gradePoint8);
                    double sumOfCredits=credit1+credit2+credit3+credit4+credit5+credit6+credit7+credit8;
                    double sgpa=sumOfValues/sumOfCredits;

                    showGpaDialog(sgpa);

                }
            }
        });



        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
                Intent intentClear=new Intent(getActivity(),CalculatorActivity.class);
                startActivity(intentClear);

            }
        });

        buttonClearOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
                Intent intentClear=new Intent(getActivity(),CalculatorActivity.class);
                startActivity(intentClear);

            }
        });


        return rowView;
        }

    private int  getGradePoint(String grade){

        int gradePoint = 0;

        switch (grade){

            case "O" :
                gradePoint= 10;
                break;

            case "A+" :
                gradePoint= 9;
                break;

            case "A" :
                gradePoint= 8;
                break;

            case "B+" :
                gradePoint= 7;
                break;

            case "B" :
                gradePoint= 6;
                break;

            case "C" :
                gradePoint= 5;
                break;

            case "P" :
                gradePoint= 4;
                break;

            case "F" :
                gradePoint= 0;
                break;

            default:
                gradePoint=0;
                break;
        }

        return gradePoint;

      }
    private ArrayAdapter<String> setSelection(List<String> sub){

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(), R.layout.spinner_item,sub){
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


    public  void showGpaDialog(double sgpa) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        TextView myMsg = new TextView(getActivity());
        myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
        myMsg.setTypeface(null, Typeface.BOLD);

        myMsg.setPadding(0,25,0,0);
        myMsg.setText(String.format("%.2f", sgpa));
        myMsg.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));
        myMsg.setTextSize(20);
        builder.setView(myMsg);

        String negativeText = "Cancel";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        getActivity().finish();
                        Intent intent=new Intent(getActivity(),CalculatorActivity.class);
                        getActivity().startActivity(intent);
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();



    }
    }
