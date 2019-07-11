package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SyllabusDao {

    @Query("SELECT * FROM syllabus WHERE course = :course AND branch = :branch AND semester = :semester AND id= :position")
    fun getSyllabusContentItem(position: Int, course: String, branch: String, semester: String): SyllabusItem

    @Query("SELECT count(*) FROM syllabus  WHERE course = :course AND branch = :branch AND semester = :semester")
    fun checkDb(course: String,branch: String,semester: String): Cursor

    @Query ("INSERT INTO syllabus (id,course,branch,semester,sub_name,module_1,module_2,module_3,module_4,module_5,module_6,refference) VALUES (:id,:course,:branch,:semester,:sub_name,:module_1,:module_2,:module_3,:module_4,:module_5,:module_6,:refference)")
    suspend fun insert(id: Int, course: String, branch: String, semester: String, sub_name: String, module_1: String, module_2: String, module_3: String, module_4: String, module_5: String, module_6: String, refference: String)

}