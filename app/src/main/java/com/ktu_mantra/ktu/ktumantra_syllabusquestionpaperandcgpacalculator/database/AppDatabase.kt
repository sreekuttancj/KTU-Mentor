package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus.SyllabusDao
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus.SyllabusItem

@Database(entities = [SyllabusItem::class], version = 6)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun syllabusDao(): SyllabusDao

    private val mIsDatabaseCreated = MutableLiveData<Boolean>()


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
                ).fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }


    private fun setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true)
    }
    fun getDatabaseCreated(): LiveData<Boolean> {
        return mIsDatabaseCreated
    }

}