package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.lesson;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.HTLesson;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.UIManager;

import java.util.ArrayList;


public class HTLessonDialog extends Dialog implements View.OnClickListener,LessonListInterface,HtDialogInterface {

    LinearLayout linearLayoutListView;
    Button buttonDeleteLesson;
    Button buttonDeleteAll;

    LessonListItem lessonList;

    private ArrayList subItems;
    boolean deleteMode;

    public HTLessonDialog(Context context) {
        super(context);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.lesson_list_dialog);

        linearLayoutListView = (LinearLayout) findViewById(R.id.list_view);
        buttonDeleteLesson = (Button) findViewById(R.id.button_delete_lesson);
        buttonDeleteAll = (Button) findViewById(R.id.button_delete_all);
        buttonDeleteLesson.setOnClickListener(this);
        buttonDeleteAll.setOnClickListener(this);
        ((ImageButton) findViewById(R.id.button_close)).setOnClickListener(this);

        subItems = new ArrayList();

        HTLesson[] htLessonsArr = UIManager.staticObject().getSubInfoFromDb();
        if (htLessonsArr != null) {
            for (HTLesson hTLesson : htLessonsArr) {
                LessonListItem lessonListItem = new LessonListItem(getContext());
                lessonListItem.setLesson(hTLesson);
                lessonListItem.setEventListener(this);
                lessonListItem.setOnClickListener(this);
                linearLayoutListView.addView(lessonListItem);
                subItems.add(lessonListItem);
            }
        }
        deleteMode = false;
        lessonList = null;
    }



    public void deleteMode(boolean deleteMode) {
        this.deleteMode = deleteMode;
        buttonDeleteLesson.setSelected(deleteMode);
        if (deleteMode) {
            buttonDeleteAll.setVisibility(View.VISIBLE);
        } else {
            buttonDeleteAll.setVisibility(View.GONE);
        }
        for (int i = 0; i < subItems.size(); i++) {
            ((LessonListItem) subItems.get(i)).setDeleteMode(deleteMode);
        }
    }



        @Override
    public void onClick(View v) {

            boolean z = false;
            switch (v.getId()) {
                case R.id.highlight_view:
                    if (!deleteMode) {
                        UIManager.staticObject().setHTLesson(((LessonListItem) v).getLesson());
                        dismiss();
                    }
                case R.id.button_close:
                    dismiss();
                case R.id.button_delete_lesson:
                    if (!deleteMode) {
                        z = true;
                    }
                    deleteMode(z);
                    break;
                case R.id.button_delete_all:
                    Context context = getContext();
                    HTDialog hTDialog = new HTDialog(getContext());
                    hTDialog.checkOneOrAll(10);
                    hTDialog.setSetButtonTrue();
                    hTDialog.setHtDialog(this);
                    hTDialog.setMessage(context.getString(R.string.message_delete_all_lessons));
                    hTDialog.setButtonText(context.getString(R.string.button_delete_all_lessons));
                    hTDialog.setButtonText(context.getString(R.string.cancel_button));
                    hTDialog.setDeleteBtnProperty(0);
                    hTDialog.setDeleteBtnProperty1(1);
                    hTDialog.show();
                    break;
                default:
            }

        }

    @Override
    public void setLessonListItem(LessonListItem lessonListItem) {

        Context mContext = getContext();
        lessonList = lessonListItem;
        HTDialog htDialog=new HTDialog(getContext());
        htDialog.checkOneOrAll(20);
        htDialog.setSetButtonTrue();
        htDialog.setHtDialog(this);
        htDialog.setMessage(mContext.getString(R.string.message_delete_item_1) + lessonListItem.getLesson().subName + mContext.getString(R.string.message_delete_item_2));
        htDialog.setButtonText(mContext.getString(R.string.button_delete_this_lesson));
        htDialog.setButtonText(mContext.getString(R.string.cancel_button));
        htDialog.setDeleteBtnProperty(0);
        htDialog.setDeleteBtnProperty1(1);
        htDialog.show();
    }

    @Override
    public void setClickAction(HTDialog htDialog, int paramInt) {
         if (paramInt == 0) {
                if (htDialog.getOneOrAll() == 10) {
                    UIManager.staticObject().deleteAllSudjects();
                    linearLayoutListView.removeAllViews();
                    subItems.clear();
                } else if (htDialog.getOneOrAll() == 20 && lessonList != null) {
                    UIManager.staticObject().deleteFullSubData(lessonList.getLesson());
                    linearLayoutListView.removeView(lessonList);
                    subItems.remove(lessonList);
                }
            }
        else {
             deleteMode(false);

         }

        }


}
