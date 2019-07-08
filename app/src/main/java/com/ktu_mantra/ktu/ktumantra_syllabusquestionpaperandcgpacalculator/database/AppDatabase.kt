package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus.SyllabusEntity
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus.SyllabusDao

@Database(entities = [SyllabusEntity::class], version = 2)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun syllabusDao(): SyllabusDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "ktu"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}