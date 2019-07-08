package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

data class SyllabusItem(var name: String, var m1: String, var m2: String,
                        var m3: String, var m4: String, var m5: String,
                        var m6: String, var t_r: String, var pos: Int)
{
    constructor() : this("", "", "", "", "", "", "", "", -1)
}

