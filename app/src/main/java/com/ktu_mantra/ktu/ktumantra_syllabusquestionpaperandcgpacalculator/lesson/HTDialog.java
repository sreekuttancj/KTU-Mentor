package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.lesson;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.BuildConfig;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;

public class HTDialog extends Dialog implements View.OnClickListener{

    HtDialogInterface htDialogInterface;

    EditText editTextContent;

    TextView textViewtitle;
    TextView textViewMessage;

    String title;
    String message;
    String btnText;
    String btn1Text;
    String btn2Text;
    String btn3Text;


    int btnproperty;
    int btnproperty1;
    int btnTextCheck;
    int onrOrAll;
    boolean setButtonTrue;

    public HTDialog(Context context) {
        super(context);
        setButtonTrue=false;
        btnTextCheck=0;
        btnproperty=-1;
        onrOrAll=-1;
    }



    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_normal);

        textViewtitle= (TextView) findViewById(R.id.text_view_title);
        textViewMessage= (TextView) findViewById(R.id.text_view_message);

        View verticalMode = findViewById(R.id.layout_vertical_mode);
        View horizontalMode = findViewById(R.id.layout_horizontal_mode);

        Button button = (Button) findViewById(R.id.dialog_vertical_item_1);
        Button button2 = (Button) findViewById(R.id.dialog_vertical_item_2);
        Button button3 = (Button) findViewById(R.id.dialog_vertical_item_3);
        Button button4 = (Button) findViewById(R.id.dialog_vertical_item_4);
        Button button5 = (Button) findViewById(R.id.dialog_horizontal_item_1);
        Button button6 = (Button) findViewById(R.id.dialog_horizontal_item_2);
        Button button7 = (Button) findViewById(R.id.dialog_horizontal_item_3);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);

        if (title == null || title.equals(BuildConfig.VERSION_NAME)) {
            textViewtitle.setVisibility(View.GONE);
        } else {
            textViewtitle.setText(title);
        }


        if (message == null || message.equals(BuildConfig.VERSION_NAME)) {
            textViewMessage.setVisibility(View.GONE);

        } else {
            textViewMessage.setText(message);
        }

        if (setButtonTrue){
            verticalMode.setVisibility(View.VISIBLE);
            horizontalMode.setVisibility(View.GONE);
            button.setText(btnText);
            button2.setText(btn1Text);
            button3.setText(btn2Text);
            button4.setText(btn3Text);
            if (btnTextCheck==0){
                button.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                button4.setVisibility(View.GONE);

            }
            else if (btnTextCheck==1){
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                button4.setVisibility(View.GONE);
            }
            else if (btnTextCheck==2){
                button3.setVisibility(View.GONE);
                button4.setVisibility(View.GONE);
            }
            else if (btnTextCheck==3){
                button3.setVisibility(View.GONE);
            }

            if (btnproperty1 == 0) {
                button.setTextColor(Color.argb(255, 100, 100, 100));
                button.setBackgroundResource(R.drawable.dialog_item_gray_selector);
            } else if (btnproperty1 == 1) {
                button2.setTextColor(Color.argb(255, 100, 100, 100));
                button2.setBackgroundResource(R.drawable.dialog_item_gray_selector);
            } else if (btnproperty1 == 2) {
                button3.setTextColor(Color.argb(255, 100, 100, 100));
                button3.setBackgroundResource(R.drawable.dialog_item_gray_selector);
            } else if (btnproperty1 == 3) {
                button4.setTextColor(Color.argb(255, 100, 100, 100));
                button4.setBackgroundResource(R.drawable.dialog_item_gray_selector);
            }
        }
        else {
            verticalMode.setVisibility(View.GONE);
            horizontalMode.setVisibility(View.VISIBLE);
            button5.setText(btnText);
            button6.setText(btn1Text);
            button7.setText(btn2Text);
            if (btnTextCheck == 0) {
                button5.setVisibility(View.GONE);
                button6.setVisibility(View.GONE);
                button7.setVisibility(View.GONE);
            } else if (btnTextCheck == 1) {
                button6.setVisibility(View.GONE);
                button7.setVisibility(View.GONE);
            } else if (btnTextCheck == 2) {
                button7.setVisibility(View.GONE);
            }
            if (btnproperty1 == 0) {
                button5.setTextColor(Color.argb(255, 100, 100, 100));
                button5.setBackgroundResource(R.drawable.dialog_item_gray_selector);
            } else if (btnproperty1 == 1) {
                button6.setTextColor(Color.argb(255, 100, 100, 100));
                button6.setBackgroundResource(R.drawable.dialog_item_gray_selector);
            } else if (btnproperty1 == 2) {
                button7.setTextColor(Color.argb(255, 100, 100, 100));
                button7.setBackgroundResource(R.drawable.dialog_item_gray_selector);
            }



        }



        if (btnproperty==0){

            button.setTextColor(Color.argb(255, 255, 255, 255));
            button.setBackgroundResource(R.drawable.dialog_item_red_selector);
        }



    }



    public void setTitle(String title){

        this.title=title;
    }
    public void setMessage(String message){

        this.message=message;
    }

    public void setButtonText(String btnText){
        if (btnTextCheck==0){
            this.btnText=btnText;
            btnTextCheck=1;
        }
        else if (btnTextCheck==1){
            this.btn1Text=btnText;
            btnTextCheck=2;

        }
        else if (btnTextCheck==2){
            this.btn2Text=btnText;
            btnTextCheck=3;

        }
        else if (btnTextCheck==3){
            this.btn3Text=btnText;
            btnTextCheck=4;

        }
    }

    public void checkOneOrAll(int i) {
        this.onrOrAll = i;
    }

    public int getOneOrAll() {
        return this.onrOrAll;
    }


    public void setHtDialog(HtDialogInterface htDialog){
        this.htDialogInterface=htDialog;
    }
    public void setSetButtonTrue(){
        setButtonTrue=true;
    }
    public void setDeleteBtnProperty(int property){
        btnproperty=property;
    }
    public void setDeleteBtnProperty1(int property){
        btnproperty1=property;
    }

    @Override
    public void onClick(View view) {

        if (htDialogInterface != null) {

            switch (view.getId()) {
                case R.id.dialog_vertical_item_1:

                    this.htDialogInterface.setClickAction(this, 0);

                    break;
                case R.id.dialog_vertical_item_2:

                    this.htDialogInterface.setClickAction(this, 1);
                    break;
                case R.id.dialog_vertical_item_3:

                    this.htDialogInterface.setClickAction(this, 2);

                    break;
                case R.id.dialog_vertical_item_4:

                    this.htDialogInterface.setClickAction(this, 3);

                    break;
                case R.id.dialog_horizontal_item_1:

                    this.htDialogInterface.setClickAction(this, 0);

                    break;
                case R.id.dialog_horizontal_item_2:

                    this.htDialogInterface.setClickAction(this, 1);

                    break;
                case R.id.dialog_horizontal_item_3:

                    this.htDialogInterface.setClickAction(this, 2);

                    break;
            }


        }
        dismiss();


    }
}
