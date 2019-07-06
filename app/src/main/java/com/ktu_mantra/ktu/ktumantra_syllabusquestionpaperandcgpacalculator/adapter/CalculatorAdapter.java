package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.CgpaFragment;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.SgpaFragment;

public class CalculatorAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public CalculatorAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SgpaFragment tab1 = new SgpaFragment();
                return tab1;
            case 1:
                CgpaFragment tab2 = new CgpaFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
