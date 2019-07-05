package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter.CalculatorAdapter;


public class CalculatorActivity extends BaseActivity {

    public ViewPager viewPager;
    Toolbar mToolbar;
    static final int SHARE_REQUEST = 1;  // The request code

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        mToolbar= (Toolbar)findViewById(R.id.toolbar);
        mToolbar.setTitle("Calculator");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SGPA"));
        tabLayout.addTab(tabLayout.newTab().setText("CGPA"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final CalculatorAdapter adapter = new CalculatorAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());


                switch(tab.getPosition()) {

                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;

                    default:

                        viewPager.setCurrentItem(tab.getPosition());
                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                    imageUri = Uri.parse(MediaStore.Images.Media.insertImage(CalculatorActivity.this.getContentResolver(),
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
}
