package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.QuestionsItem;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.service.DownloadService;

import java.io.File;
import java.util.List;

public class QuestionPaperAdapter extends RecyclerView.Adapter<QuestionPaperAdapter.MyViewHolder>{

    Context mContext;
    List<QuestionsItem> questionsItemList;
    QuestionsItem questionsItem;

    public QuestionPaperAdapter(Context context, List<QuestionsItem> questionsFromFirebasesList)
    {
        mContext=context;
        this.questionsItemList =questionsFromFirebasesList;

    }

    @Override
    public QuestionPaperAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_question_paper_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionPaperAdapter.MyViewHolder holder, int position) {


        questionsItem = questionsItemList.get(position);
       File myExternalFile = new File(mContext.getExternalFilesDir(DownloadService.filepath), questionsItem.getName()+" "+ questionsItem.getYear()+".pdf");

        holder.title.setText(questionsItem.getName());
        holder.year.setText(questionsItem.getYear());

        if (myExternalFile.exists()){

            holder.download.setImageResource(R.drawable.ic_download_completed);
        }
        else {
            holder.download.setImageResource(R.drawable.ic_file_download_grey);

        }


    }

    @Override
    public int getItemCount() {
        return questionsItemList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView year;
        ImageView download;



        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textViewSubjectName);
            year = (TextView) itemView.findViewById(R.id.textViewYear);
            download = (ImageView) itemView.findViewById(R.id.imageView_download);
        }
    }
}
