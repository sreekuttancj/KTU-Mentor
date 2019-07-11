package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.AppDatabase
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager

class SyllabusRepository(private val appDatabase: AppDatabase) {

    var database: FirebaseDatabase = FirebaseDatabase.getInstance()




    //get syllabus from db
    fun getSyllabusContent (position: Int, course: String, branch: String, semester: String): SyllabusItem{
        val syllabusItem = appDatabase.syllabusDao().getSyllabusContentItem(position,course,branch,semester)
        Log.i("cursoritem",syllabusItem.m1)
        return syllabusItem
    }

            suspend fun insert(syllabusItem: SyllabusItem,prefManager: PrefManager) {
        appDatabase.syllabusDao().insert(syllabusItem.pos,prefManager.getCourse(),prefManager.getBranch(),prefManager.getSemester(),
                syllabusItem.name,syllabusItem.m1,syllabusItem.m2,syllabusItem.m3,syllabusItem.m4,syllabusItem.m5,syllabusItem.m6,
                syllabusItem.t_r)
    }

    fun getFirebaseRef(course: String,semester: String, branch: String) : DatabaseReference {

        Log.e("check_pref","course is $course, semester is $semester and branch is $branch")
        return if (semester == "s1_s2") {
            database.getReference().child("syllabus").child("course").child(course).child(semester)
        }else{
            database.getReference().child("syllabus").child("course").child(course).child(branch).child(semester)
        }
    }
}