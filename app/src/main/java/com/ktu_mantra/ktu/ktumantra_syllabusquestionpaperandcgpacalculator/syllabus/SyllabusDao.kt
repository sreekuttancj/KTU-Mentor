package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SyllabusDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(position: Int, course: String, branch: String, semester: String, subName: String, module1: String, module2: String, module3: String, module4: String, module5: String, module6: String, refference: String)

    @Query("SELECT module_1 , module_2, module_3 , module_4 , module_5 , module_6 , refference FROM syllabus WHERE course = :course AND branch = :branch AND semester = :semester AND id= :position")
    fun getSyllabusContent(position: Int, course: String, branch: String, semester: String): LiveData<SyllabusItem>
}