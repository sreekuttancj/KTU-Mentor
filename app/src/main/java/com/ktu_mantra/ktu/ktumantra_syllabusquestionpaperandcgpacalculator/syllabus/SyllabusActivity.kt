package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.BaseActivity
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R

class SyllabusActivity : BaseActivity() {

    lateinit var mToolbar: Toolbar
    lateinit var recyclerViewSyllabus: RecyclerView

    lateinit var syllabusAdapter: SyllabusAdapter
    lateinit var syllabusViewModel: SyllabusViewModel

    private val SHARE_REQUEST = 1  // The request code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_syllabus)

        mToolbar = findViewById(R.id.toolbar)
        mToolbar.title = resources.getString(R.string.syllabus)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolbar.setNavigationOnClickListener { finish() }

        val relativeLayout = findViewById<RelativeLayout>(R.id.re_main)

        recyclerViewSyllabus = findViewById(R.id.recyclerViewSyllabus)
        recyclerViewSyllabus.layoutManager = LinearLayoutManager(applicationContext)


        recyclerViewSyllabus.addOnItemTouchListener(RecyclerTouchListener(applicationContext, recyclerViewSyllabus , object : ClickListener {
            override fun onClick(view: View, position: Int) {
                val intentSyllabusInner = Intent(this@SyllabusActivity, ModuleActivity::class.java)
                intentSyllabusInner.putExtra("subject", syllabusViewModel.getSubjects()[position])
                intentSyllabusInner.putExtra("position", position.toString())
                startActivity(intentSyllabusInner)
            }

            override fun onLongClick(view: View, position: Int) {
            }
        }))

        // Get a new or existing ViewModel from the ViewModelProvider.
        syllabusViewModel = ViewModelProviders.of(this).get(SyllabusViewModel::class.java)

        //set semester and branch values
        syllabusViewModel.setBranchAndSem()

        //set subject values
        syllabusViewModel.setSubject()

        //set adapter
        syllabusAdapter = SyllabusAdapter(this, syllabusViewModel.getSubjects())
        recyclerViewSyllabus.adapter = syllabusAdapter


        //todo check the requirement of an observer

        if (syllabusViewModel.checkDb()==0){
//            todo showProgressDialog
            if (syllabusViewModel.checkNetworkConnection()){
                syllabusViewModel.saveSyllabusFromFirebase()
            }else{
                Snackbar.make(relativeLayout, "No network connection", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    internal interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View, position: Int)
    }

    private class RecyclerTouchListener internal constructor(context: Context, recyclerView: RecyclerView, val clickListener: ClickListener) : RecyclerView.OnItemTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child))
                    }
                }
            })
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

            val child = rv.findChildViewUnder(e.x, e.y)
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child))
            }
            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {

            R.id.share_app -> {
                var imageUri: Uri? = null
                try {
                    imageUri = Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(),
                            BitmapFactory.decodeResource(resources, R.drawable.share_image), null, null))
                } catch (e: NullPointerException) {
                }

                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download KTU Mentor Android App developed for KTU University students :https://goo.gl/1Gy9QT")
                sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
                sendIntent.type = "image/*"
                sendIntent.setPackage("com.whatsapp")
                startActivityForResult(sendIntent, SHARE_REQUEST)

                return true
            }


            R.id.rate_us -> {


                val intent = Intent(Intent.ACTION_VIEW)

                intent.data = Uri.parse("http://goo.gl/abcK2p")
                if (!myStartActivity(intent))
                //Market (Google play) app seems not installed, let's try to open a webbrowser
                    intent.data = Uri.parse("http://goo.gl/abcK2p")
                if (!myStartActivity(intent)) {
                    //Well if this also fails, we have run out of options, inform the user.
                    Toast.makeText(applicationContext, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show()
                }

                return true
            }


            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun myStartActivity(aIntent: Intent): Boolean {
        return try {
            startActivity(aIntent)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }
}