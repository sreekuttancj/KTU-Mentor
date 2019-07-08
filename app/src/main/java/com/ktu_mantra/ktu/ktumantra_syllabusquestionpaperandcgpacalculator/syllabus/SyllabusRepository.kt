package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.AppDatabase

class SyllabusRepository(private val appDatabase: AppDatabase) {


    //get syllabus from db
    fun getSyllabusContent(position: Int, course: String, branch: String, semester: String): LiveData<SyllabusItem>{
        return appDatabase.syllabusDao().getSyllabusContent(position,course,branch,semester)
    }

}