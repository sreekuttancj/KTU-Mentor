package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R

class SyllabusAdapter(var context: Context,
                      var subject: Array<String?>,
                      private var syllabusViewModel: SyllabusViewModel
) : RecyclerView.Adapter<SyllabusAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_syllabus_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (syllabusViewModel.getSelectedSemester() == "S1&S2") {
            subject.size
        } else {
            subject.size - 2
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = subject[position]
        holder.courseNO.text = syllabusViewModel.getCourseNo(position)
        holder.credit.text = syllabusViewModel.getCredit(position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.textViewSubjectName)
        var courseNO: TextView = itemView.findViewById(R.id.textViewCourseNo)
        var credit: TextView = itemView.findViewById(R.id.textViewCredits)
    }
}