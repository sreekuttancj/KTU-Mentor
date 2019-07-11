package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R

class ModuleActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mToolbar: Toolbar
    private lateinit var cardViewM1: CardView
    private lateinit var cardViewM2: CardView
    private lateinit var cardViewM3: CardView
    private lateinit var cardViewM4: CardView
    private lateinit var cardViewM5: CardView
    private lateinit var cardViewM6: CardView
    private lateinit var cardViewTextBook: CardView
    private lateinit var buttonInstall: Button
    private lateinit var relativeLayout: RelativeLayout
    //    private AdView mAdView;

    private lateinit var subject: String
    private lateinit var position: String
    private lateinit var modulePosition: String

    lateinit var syllabusViewModel: SyllabusViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module)

        //take intend from syllabusActivity
        val intentFromSyllabus = intent
        subject = intentFromSyllabus.getStringExtra("subject")
        position = intentFromSyllabus.getStringExtra("position")

        mToolbar = findViewById(R.id.toolbar)
        mToolbar.title = subject
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mToolbar.setNavigationOnClickListener { finish() }

        relativeLayout = findViewById(R.id.adView)
        buttonInstall = findViewById(R.id.button_install)
        cardViewM1 = findViewById(R.id.cardView_module1)
        cardViewM2 = findViewById(R.id.cardView_module2)
        cardViewM3 = findViewById(R.id.cardView_module3)
        cardViewM4 = findViewById(R.id.cardView_module4)
        cardViewM5 = findViewById(R.id.cardView_module5)
        cardViewM6 = findViewById(R.id.cardView_module6)
        cardViewTextBook = findViewById(R.id.cardView_textbook)

        buttonInstall.setOnClickListener(this)
        cardViewM1.setOnClickListener(this)
        cardViewM2.setOnClickListener(this)
        cardViewM3.setOnClickListener(this)
        cardViewM4.setOnClickListener(this)
        cardViewM5.setOnClickListener(this)
        cardViewM6.setOnClickListener(this)
        cardViewTextBook.setOnClickListener(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        syllabusViewModel = ViewModelProviders.of(this).get(SyllabusViewModel::class.java)

        //show ad
        if (syllabusViewModel.checkNetworkConnection()){
            if (syllabusViewModel.showAdd()){
                relativeLayout.visibility = View.VISIBLE
            }else{
                relativeLayout.visibility = View.GONE
            }
        } else run {
            relativeLayout.visibility = View.GONE
        }

    }

    override fun onClick(v: View) {
    when(v.id){

        R.id.cardView_module1 -> {
            modulePosition = "0"
        }
        R.id.cardView_module2 ->{
            modulePosition = "1"
        }
        R.id.cardView_module3 ->{
            modulePosition = "2"
        }
        R.id.cardView_module4 ->{
            modulePosition = "3"
        }

        R.id.cardView_module5 ->{
            modulePosition = "4"
        }

        R.id.cardView_module6 ->{
            modulePosition = "5"
        }

        R.id.cardView_textbook ->{
            modulePosition = "6"
        }

        R.id.button_install ->
        {
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

        Log.i("mod_pos",modulePosition)
        val intentSyllabusInner = Intent(this@ModuleActivity, SyllabusInnerActivity::class.java)
        intentSyllabusInner.putExtra("subject", subject)
        intentSyllabusInner.putExtra("position", position)
        intentSyllabusInner.putExtra("module_position", modulePosition)
        startActivity(intentSyllabusInner)

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