package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.syllabus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.PrefManager;


public class SyllabusAdapter  extends RecyclerView.Adapter<SyllabusAdapter.MyViewHolder> {

    private Context context;
    private String subject[];
    private PrefManager prefManager;
    public SyllabusAdapter(Context context,String[] subject)
    {
        this.context=context;
        this.subject=subject;
        prefManager=new PrefManager(context);
        Log.i("sublenght",String.valueOf(subject.length));

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_syllabus_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SyllabusAdapter.MyViewHolder holder, int position) {
        holder.title.setText(subject[position]);


        if (!prefManager.getSemester().equals("S1&S2")) {
            if (prefManager.getBranch().equals("CE")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ce_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ce_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("CS")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.cs_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.cs_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("CH")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ch_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ch_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("EC")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ec_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ec_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("EE")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ee_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ee_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("IC")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ic_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ic_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("IE")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ie_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ie_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("IT")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.it_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.it_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("ME")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.me_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.me_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("AE")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ec_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ae_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("AO")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ao_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.ao_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("AU")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.au_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.au_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("BM")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.bm_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.bm_s4_course_no)[position]);
                }
            } else if (prefManager.getBranch().equals("MR")) {
                if (prefManager.getSemester().equals("S3")) {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.mr_s3_course_no)[position]);
                } else {
                    holder.courseNO.setText(context.getResources().getStringArray(R.array.mr_s4_course_no)[position]);
                }
            }
        } else {
            holder.courseNO.setText(context.getResources().getStringArray(R.array.s1_s2_course_no)[position]);
        }

        if (prefManager.getSemester().equals("S1&S2")){
            holder.credit.setText(context.getResources().getStringArray(R.array.s1_s2_credit)[position]);

        }else if(prefManager.getSemester().equals("S3")){
            holder.credit.setText(context.getResources().getStringArray(R.array.s3_credit)[position]);
        }
        else {
            holder.credit.setText(context.getResources().getStringArray(R.array.s4_credit)[position]);
        }
    }


    @Override
    public int getItemCount() {

        Log.i("semester",prefManager.getSemester());
        if (prefManager.getSemester().equals("S1&S2")){
            return subject.length;

        }
        else {
            return subject.length-2;

        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView courseNO;
        TextView credit;


        MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewSubjectName);
            courseNO = itemView.findViewById(R.id.textViewCourseNo);
            credit = itemView.findViewById(R.id.textViewCredits);
        }
    }

}
