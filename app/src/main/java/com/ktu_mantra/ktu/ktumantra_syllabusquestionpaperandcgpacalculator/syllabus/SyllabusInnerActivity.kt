package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R

class SyllabusInnerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textViewModuleNo: TextView
    private lateinit var imageButtonLeft: ImageButton
    private lateinit var imageButtonRight: ImageButton
    private lateinit var mToolbar: Toolbar
    private lateinit var viewPagerSyllabus: ViewPager
    private lateinit var viewPagerSyllabusAdapter: ViewPagerSyllabusAdapter
    private lateinit var buttonInstall: Button
    private lateinit var relativeLayout: RelativeLayout

    private var position: Int = 0
    private var modulePosition: Int = 0

    private lateinit var syllabusViewModel: SyllabusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_syllabus_inner)

        val myIntent = intent
        val subject = myIntent.getStringExtra("subject")
        position = Integer.parseInt(myIntent.getStringExtra("position"))
        modulePosition = Integer.parseInt(myIntent.getStringExtra("module_position"))

        mToolbar = findViewById(R.id.toolbar)
        mToolbar.title = subject
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolbar.setNavigationOnClickListener { finish() }

        textViewModuleNo = findViewById(R.id.textViewModuleNo)
        imageButtonLeft = findViewById(R.id.imageLeft)
        imageButtonLeft.setOnClickListener(this)
        imageButtonRight = findViewById(R.id.imageRight)
        imageButtonRight.setOnClickListener(this)
        buttonInstall = findViewById(R.id.button_install)
        buttonInstall.setOnClickListener(this)

        relativeLayout = findViewById(R.id.adView)

        syllabusViewModel= ViewModelProviders.of(this).get(SyllabusViewModel::class.java)

        viewPagerSyllabus = findViewById(R.id.viewPagerSyllabus)
        viewPagerSyllabusAdapter = ViewPagerSyllabusAdapter(this@SyllabusInnerActivity, position)
        //get item and set in adapter
        syllabusViewModel.getSyllabusItem(position)
        viewPagerSyllabusAdapter.setModules( syllabusViewModel.setModules())
        viewPagerSyllabus.adapter = viewPagerSyllabusAdapter
        viewPagerSyllabus.currentItem = modulePosition

        viewPagerSyllabus.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {


                if (subject == "Engineering Graphics") {
                    textViewModuleNo.text = applicationContext.resources.getStringArray(R.array.graphics_weight)[position]

                } else if (subject == "Basics of Electronics Engineering") {
                    textViewModuleNo.text = applicationContext.resources.getStringArray(R.array.basics_electronics_weight)[position]

                } else if (subject == "Life Skills") {
                    textViewModuleNo.text = SyllabusViewModel.moduleNO[position]

                } else {
                    textViewModuleNo.text = applicationContext.resources.getStringArray(R.array.common_weight)[position]

                }

                imageButtonLeftRight(position)

            }

            override fun onPageSelected(position: Int) {


            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        //show ad
        if (syllabusViewModel.checkNetworkConnection()){
            if (syllabusViewModel.showAdd()){
                relativeLayout.visibility = View.VISIBLE
            }else{
                relativeLayout.visibility = View.GONE
            }
        }else{
            relativeLayout.visibility = View.GONE
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imageLeft ->
                viewPagerSyllabus.setCurrentItem(getItem(-1), true)

            R.id.imageRight ->
                viewPagerSyllabus.setCurrentItem(getItem(+1), true)


            R.id.button_install ->{
                if (syllabusViewModel.checkNetworkConnection()){
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://goo.gl/eLHdcp")
                    if (!myStartActivity(intent))
                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                        intent.data = Uri.parse("https://goo.gl/eLHdcp")
                    if (!myStartActivity(intent)) {
                        //Well if this also fails, we have run out of options, inform the user.
                        Toast.makeText(applicationContext, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Snackbar.make(relativeLayout, "No internet connection", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun myStartActivity(aIntent: Intent): Boolean {
        return try {
            aIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            applicationContext.startActivity(aIntent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }

    }

    private fun getItem(i: Int): Int {
        return viewPagerSyllabus.currentItem + i
    }

    fun imageButtonLeftRight(currentPage: Int) {
        val lastPage = viewPagerSyllabus.adapter!!.count - 1

        if (currentPage != 0) {
            imageButtonLeft.visibility = View.VISIBLE

        } else {
            imageButtonLeft.visibility = View.INVISIBLE
        }

        if (currentPage == lastPage) {
            imageButtonRight.visibility = View.INVISIBLE
        } else {
            imageButtonRight.visibility = View.VISIBLE

        }
    }
}