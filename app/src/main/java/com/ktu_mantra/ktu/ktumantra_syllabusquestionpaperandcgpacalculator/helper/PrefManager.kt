package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper

import android.content.Context
import android.content.SharedPreferences

class PrefManager(val context: Context) {

     val preferences: SharedPreferences
    // Editor for Shared preferences
    val editor: SharedPreferences.Editor


    init {
        preferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        editor = preferences.edit()
    }

    //key values
    companion object{
        private const val  PREF_NAME = "KTUMENTOR"

        private const val KEY_COURSE = "course"
        private const val KEY_BRANCH = "branch"
        private const val KEY_SEMESTER = "semester"
        private const val KEY_LOGIN = "login"
        const val KEY_COUNT = "countKey"
        // Shared pref mode
        private const val PRIVATE_MODE = 0
    }

    fun getCourse(): String {
        return preferences.getString(KEY_COURSE, "course")
    }
    fun setCourse(course: String) {
        editor.putString(KEY_COURSE, course)
        editor.commit()
    }

    fun getBranch(): String {
        return preferences.getString(KEY_BRANCH, "branch")
    }
    fun setBranch(branch: String) {
        editor.putString(KEY_BRANCH, branch)
        editor.commit()
    }

    fun getSemester(): String {
        return preferences.getString(KEY_SEMESTER, "semester")
    }
    fun setSemester(semester: String) {
        editor.putString(KEY_SEMESTER, semester)
        editor.commit()
    }

    fun getLogin(): String {
        return preferences.getString(KEY_LOGIN, "fail")
    }
    fun setLoginBit(login: String) {
        editor.putString(KEY_LOGIN, login)
        editor.commit()

    }

    fun getCount(): Int {
        return preferences.getInt(KEY_COUNT, 5)
    }
    fun setCount(count: Int) {
        editor.putInt(KEY_COUNT, count)
        editor.commit()
    }
}