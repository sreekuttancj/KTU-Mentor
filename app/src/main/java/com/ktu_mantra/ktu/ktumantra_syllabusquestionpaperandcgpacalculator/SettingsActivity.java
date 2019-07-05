package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.Preferencemanager;

public class SettingsActivity extends AppCompatActivity {

    Toolbar settingsToolbar;
    AppCompatSpinner spinnerPeriod;
    SwitchCompat switchWeek;
    String selectedPeriod;
    boolean enableSaturday;

    Preferencemanager preferencemanager;


    String[] periodlength={
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsToolbar= (Toolbar) findViewById(R.id.toolbar);
        switchWeek= (SwitchCompat) findViewById(R.id.switch_saturday);

        preferencemanager=new Preferencemanager(SettingsActivity.this);

        if (preferencemanager.getWeak().equals("6")){
            switchWeek.setChecked(true);
        }
        else {
            switchWeek.setChecked(false);

        }

        spinnerPeriod= (AppCompatSpinner) findViewById(R.id.spinner_period);
        setSupportActionBar(settingsToolbar);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settingsToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        settingsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ArrayAdapter<String> periodAdapter=new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_spinner_item, periodlength);
        periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriod.setAdapter(periodAdapter);

        switch (Integer.parseInt(preferencemanager.getPeriod())){

            case 5: spinnerPeriod.setSelection(0);
                    break;
            case 6: spinnerPeriod.setSelection(1);
                    break;
            case 7: spinnerPeriod.setSelection(2);
                    break;
            case 8: spinnerPeriod.setSelection(3);
                    break;
            case 9: spinnerPeriod.setSelection(4);
                    break;
            case 10:spinnerPeriod.setSelection(5);
                    break;
            case 11:spinnerPeriod.setSelection(6);
                    break;
            case 12:spinnerPeriod.setSelection(7);
                    break;
        }


        switchWeek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    enableSaturday=true;
                }
                else {

                    enableSaturday=false;

                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:

                selectedPeriod=spinnerPeriod.getSelectedItem().toString();
                preferencemanager.setPeriod(selectedPeriod);

                if (enableSaturday){
                    preferencemanager.setWeak("6");

                }
                else {
                    preferencemanager.setWeak("5");

                }

                finish();
                TimetableActivity.mainActivity.finish();
                Intent intentTimeTable=new Intent(this,TimetableActivity.class);
                startActivity(intentTimeTable);

                Log.i("week_period","week:"+preferencemanager.getWeak()+"period:"+preferencemanager.getPeriod());

                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();

                break;

        }
        return true;
    }




}
