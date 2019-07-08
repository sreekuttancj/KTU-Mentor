package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.BaseActivity
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R

class SyllabusActivityBeta : BaseActivity() {

    lateinit var mToolbar: Toolbar
    lateinit var recyclerViewSyllabus: RecyclerView

    lateinit var syllabusAdapter: SyllabusAdapter

    var subject = arrayOfNulls<String>(30)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_syllabus)

        mToolbar = findViewById(R.id.toolbar)
        mToolbar.title = resources.getString(R.string.syllabus)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        recyclerViewSyllabus = findViewById(R.id.recyclerViewSyllabus)
        syllabusAdapter = SyllabusAdapter(this, subject)
        recyclerViewSyllabus.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewSyllabus.adapter = syllabusAdapter


    }
}