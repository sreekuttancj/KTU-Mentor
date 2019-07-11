package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "syllabus")
data class SyllabusItem(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") var pos: Int,
        @ColumnInfo(name = "sub_name") var name: String,
        @ColumnInfo(name = "module_1") var m1: String,
        @ColumnInfo(name = "module_2") var m2: String,
        @ColumnInfo(name = "module_3") var m3: String,
        @ColumnInfo(name = "module_4") var m4: String,
        @ColumnInfo(name = "module_5") var m5: String,
        @ColumnInfo(name = "module_6") var m6: String,
        @ColumnInfo(name = "refference") var t_r: String,
        val course: String,
        val branch: String,
        val semester: String
)
{
    constructor() : this(0, "", "", "", "", "", "", "", "","","","")
}

