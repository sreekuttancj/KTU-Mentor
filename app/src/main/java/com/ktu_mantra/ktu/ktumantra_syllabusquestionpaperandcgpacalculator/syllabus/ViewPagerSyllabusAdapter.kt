package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R

class ViewPagerSyllabusAdapter(val context: Context,val subjectPosition: Int): PagerAdapter() {

    private lateinit var modules: Array<String?>

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewLayout = inflater.inflate(R.layout.viewpager_modules, container, false)

        Log.i("check_module",modules[position])

        val textViewModules = viewLayout.findViewById<TextView>(R.id.textViewModuleContent)

        if (modules[position].equals("empty")) {
            val noSyllabus = "<b>This subject has only 5 modules</b>"
            textViewModules.text = Html.fromHtml(noSyllabus)
        } else {
            textViewModules.text = Html.fromHtml(modules[position])
        }

        (container as ViewPager).addView(viewLayout)
        return viewLayout
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun getCount(): Int {
        return 7
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as RelativeLayout)
    }

    fun setModules(modules: Array<String?>){
        this.modules = modules
    }
}