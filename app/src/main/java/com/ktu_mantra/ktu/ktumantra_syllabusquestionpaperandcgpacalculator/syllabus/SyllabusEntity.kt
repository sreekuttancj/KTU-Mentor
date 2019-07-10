package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "syllabus")
data class SyllabusEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val course: String,
        val branch: String,
        val semester: String,
        val sub_name: String,
        val module_1: String,
        val module_2: String,
        val module_3: String,
        val module_4: String,
        val module_5: String,
        val module_6: String,
        val refference: String)
