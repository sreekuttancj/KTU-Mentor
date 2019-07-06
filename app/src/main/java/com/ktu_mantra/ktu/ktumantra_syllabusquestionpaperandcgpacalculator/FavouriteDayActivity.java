package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter.FavouriteDayAdapter;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.DatabaseHandler;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.CalenderItem;

import java.util.List;

public class FavouriteDayActivity extends AppCompatActivity {

    Toolbar mToolbar;
    DatabaseHandler databaseHandler;
    List<CalenderItem> calenderFavourite;
    public static ListView listViewFavourite;
    FavouriteDayAdapter favouriteDayAdapter;
    public static TextView textViewEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_days);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Important Days");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        databaseHandler=new DatabaseHandler(getApplicationContext());
        calenderFavourite=databaseHandler.getCalenderFavourite();
        listViewFavourite= (ListView) findViewById(R.id.listView_favourite);
        textViewEmpty= (TextView) findViewById(R.id.textView_no_favourites);
        if (calenderFavourite.size()>0){
            textViewEmpty.setVisibility(View.GONE);
            listViewFavourite.setVisibility(View.VISIBLE);
            favouriteDayAdapter=new FavouriteDayAdapter(FavouriteDayActivity.this,calenderFavourite);
            listViewFavourite.setAdapter(favouriteDayAdapter);
        }
        else {
            listViewFavourite.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
        }

    }
}
