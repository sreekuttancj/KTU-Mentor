package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.DatabaseHandler;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus.SyllabusItem;


public class ViewPagerSyllabusAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Activity activity;
    private  int subjectPosition;
    PrefManager prefManager;

    SyllabusItem syllabusItem;
    DatabaseHandler databaseHandler;

    public ViewPagerSyllabusAdapter(Activity activity, int subjectPosition)
    {
        this.activity=activity;
        this.subjectPosition=subjectPosition;
    }


    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        databaseHandler=new DatabaseHandler(activity);
        prefManager=new PrefManager(activity);
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View viewLayout = inflater.inflate(R.layout.viewpager_modules, container,
                false);

        TextView textViewModules= (TextView) viewLayout.findViewById(R.id.textViewModuleContent);

        syllabusItem =databaseHandler.getSyllabusContent(subjectPosition,prefManager.getCourse(),prefManager.getBranch(),prefManager.getSemester());
        String[] modules=new String[10];
        modules[0]= syllabusItem.getM1();
        modules[1]= syllabusItem.getM2();
        modules[2]= syllabusItem.getM3();
        modules[3]= syllabusItem.getM4();
        modules[4]= syllabusItem.getM5();
        modules[5]= syllabusItem.getM6();
        modules[6]= syllabusItem.getT_r();


         if (modules[position].equals("empty")){
             String noSyllabus="<b>This subject has only 5 modules</b>";
             textViewModules.setText(Html.fromHtml(noSyllabus));
         }
        else {
             textViewModules.setText(Html.fromHtml(modules[position]));

         }
                ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }


        @Override
    public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
