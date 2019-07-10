package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import android.app.Application
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.AppDatabase
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ConnectionDetector
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager
import kotlinx.coroutines.launch
import java.util.*

class SyllabusViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext= application
    private val repository: SyllabusRepository = SyllabusRepository(AppDatabase.getDatabase(applicationContext))
    private val prefManager: PrefManager = PrefManager(applicationContext)
    //check network connection
    private val connectionDetector: ConnectionDetector = ConnectionDetector(applicationContext)

//    private val syllabusItem: LiveData<SyllabusItem>

    var course = "btech"
    var branch = ""
    var semester = prefManager.getSemester().toLowerCase()
    var subject = arrayOfNulls<String>(30)

//    init {
//        syllabusItem = repository.getSyllabusContent(syllabusID,prefManager.getCourse(),prefManager.getBranch(),prefManager.getSemester())
//    }

    fun checkNetworkConnection(): Boolean{
        return connectionDetector.isConnectingToInternet
    }

    //get syllabus from firebase and save it in sqlite

     fun saveSyllabusFromFirebase(){
        repository.getFirebaseRef(course,semester, branch).addValueEventListener(
               object : ValueEventListener {
                   override fun onCancelled(p0: DatabaseError?) {
                       TODO("show snackbar")
                       // Snackbar.make(relativeLayout, "Failed to load please try again", Snackbar.LENGTH_LONG).show()
                   }

                   override fun onDataChange(dataSnapshot: DataSnapshot?) {
                       if (dataSnapshot != null) {
                           for (postSnapshot in dataSnapshot.children) {
                               Log.i("check_value", "enter")

                               val tempSyllabusItem = postSnapshot.getValue(SyllabusItem::class.java)
                               Log.i("check_value", "id is ${tempSyllabusItem.pos} and name ${tempSyllabusItem.name}")
                               insert(tempSyllabusItem)
                               //todo hideProgressDialog
                           }
                       }
                   }
               })
    }


     fun insert(syllabusItem: SyllabusItem) = viewModelScope.launch{
        repository.insert(syllabusItem,prefManager)
    }

    fun checkDb(): Int{

        val cursor= AppDatabase.getDatabase(applicationContext).syllabusDao().checkDb(prefManager.getCourse(),prefManager.getBranch(),prefManager.getSemester())
        cursor.moveToFirst()
        val count= cursor.getInt(0)
        cursor.close()
        return count
    }

    fun setBranchAndSem(){
        if (prefManager.getBranch() == "CE" && prefManager.getSemester() != "S1&S2") {
            branch = "ce"
        } else if (prefManager.getBranch() == "CS" && prefManager.getSemester() != "S1&S2") {
            branch = "cs"

        } else if (prefManager.getBranch() == "CH" && prefManager.getSemester() != "S1&S2") {
            branch = "ch"

        } else if (prefManager.getBranch() == "EC" && prefManager.getSemester() != "S1&S2") {
            branch = "ec"

        } else if (prefManager.getBranch() == "EE" && prefManager.getSemester() != "S1&S2") {
            branch = "ee"

        } else if (prefManager.getBranch() == "IC" && prefManager.getSemester() != "S1&S2") {
            branch = "ic"

        } else if (prefManager.getBranch() == "IE" && prefManager.getSemester() != "S1&S2") {
            branch = "ie"

        } else if (prefManager.getBranch() == "IT" && prefManager.getSemester() != "S1&S2") {
            branch = "it"

        } else if (prefManager.getBranch() == "CS" && prefManager.getSemester() != "S1&S2") {
            branch = "cs"

        } else if (prefManager.getBranch() == "ME" && prefManager.getSemester() != "S1&S2") {
            branch = "me"

        } else if (prefManager.getBranch() == "AE" && prefManager.getSemester() != "S1&S2") {
            if (prefManager.getSemester() == "S4") {
                branch = "ae"
            } else {
                branch = "ec"
            }
        } else if (prefManager.getBranch() == "AO" && prefManager.getSemester() != "S1&S2") {
            branch = "ao"

        } else if (prefManager.getBranch() == "AU" && prefManager.getSemester() != "S1&S2") {
            branch = "au"

        } else if (prefManager.getBranch() == "BM" && prefManager.getSemester() != "S1&S2") {
            branch = "bm"

        } else if (prefManager.getBranch() == "MR" && prefManager.getSemester() != "S1&S2") {
            branch = "mr"

        } else {
            semester = "s1_s2"
        }

//        Log.i("finale_check", "$semester and $branch")
    }

    fun setSubject(){
        //TODO create subject array for s4 and change condition loop here

        if (prefManager.getSemester() != "S1&S2") {
            if (prefManager.getBranch() == "CE") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.ce_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.ce_s4_subject)
                }
            } else if (prefManager.getBranch() == "CS") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.cs_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.cs_s4_subject)
                }
            } else if (prefManager.getBranch() == "CH") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.ch_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.ch_s4_subject)
                }
            } else if (prefManager.getBranch() == "EC") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.ec_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.ec_s4_subject)
                }
            } else if (prefManager.getBranch() == "EE") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.ee_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.ee_s4_subject)
                }
            } else if (prefManager.getBranch() == "IC") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.ic_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.ic_s4_subject)
                }
            } else if (prefManager.getBranch() == "IE") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.ie_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.ie_s4_subject)
                }
            } else if (prefManager.getBranch() == "IT") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.it_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.it_s4_subject)
                }
            } else if (prefManager.getBranch() == "ME") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.me_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.me_s4_subject)
                }
            } else if (prefManager.getBranch() == "AE") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.ec_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.ae_s4_subject)
                }
            } else if (prefManager.getBranch() == "AO") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.ao_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.ao_s4_subject)
                }
            } else if (prefManager.getBranch() == "AU") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.au_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.au_s4_subject)
                }
            } else if (prefManager.getBranch() == "BM") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.bm_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.bm_s4_subject)
                }
            } else if (prefManager.getBranch() == "MR") {
                subject = if (prefManager.getSemester() == "S3") {
                    applicationContext.resources.getStringArray(R.array.mr_s3_subject)
                } else {
                    applicationContext.resources.getStringArray(R.array.mr_s4_subject)
                }
            }
        } else {
            subject = applicationContext.resources.getStringArray(R.array.s1_s2_subjects)
        }

    }

    fun getSubjects() = subject

}