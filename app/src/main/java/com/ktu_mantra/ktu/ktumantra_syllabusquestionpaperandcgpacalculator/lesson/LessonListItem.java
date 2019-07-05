package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.lesson;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.HTLesson;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.color_list.ColorCollection;

public class LessonListItem extends LinearLayout implements View.OnClickListener{

    private boolean deleteMode;
    View colorView;
    View highlightView;
    TextView textViewSubName;
    TextView textViewProfName;
    Button buttonDeleteItem;

    LessonListInterface lessonListItem;
    HTLesson htLesson;
    public LessonListItem(Context context) {
        super(context);
        deleteMode=false;
        lessonList(context);
    }

    public void lessonList(Context context){

        LayoutInflater.from(context).inflate(R.layout.lesson_list_item, this, true);
        setId(R.id.highlight_view);

        colorView = (LinearLayout) findViewById(R.id.subject_color_view);
        highlightView = findViewById(R.id.highlight_view);
        textViewSubName = (TextView) findViewById(R.id.text_view_subject_name);
        textViewProfName = (TextView) findViewById(R.id.text_view_professor_name);
        buttonDeleteItem = (Button) findViewById(R.id.button_delete_item);
        buttonDeleteItem.setVisibility(GONE);
        buttonDeleteItem.setOnClickListener(this);
        deleteMode = false;
        lessonListItem = null;

    }

    public void setLesson(HTLesson hTLesson) {
        this.htLesson = hTLesson;
        textViewSubName.setText(hTLesson.subName);
        textViewProfName.setText(hTLesson.profName);
        textViewSubName.setTextColor(hTLesson.fontColor);
        textViewProfName.setTextColor(hTLesson.fontColor);
        this.colorView.setBackgroundColor(hTLesson.color);
    }

    public HTLesson getLesson() {
        return htLesson;
    }


    public void setEventListener(LessonListInterface lessonListItem) {
        this.lessonListItem = lessonListItem;
    }

    public void setDeleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
        if (deleteMode) {
            buttonDeleteItem.setVisibility(VISIBLE);
        } else {
            buttonDeleteItem.setVisibility(GONE);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (!deleteMode) {
            if (motionEvent.getAction() == 0) {
                this.highlightView.setBackgroundColor(ColorCollection.onClickAlpha);
            } else if (motionEvent.getAction() == 1) {
                this.highlightView.setBackgroundColor(ColorCollection.defaultColor);
            } else if (motionEvent.getAction() == 3) {
                this.highlightView.setBackgroundColor(ColorCollection.defaultColor);
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_delete_item && this.lessonListItem != null) {
            this.lessonListItem.setLessonListItem(this);
        }
    }

}
