package com.example.letseat.Activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.letseat.R;

import java.util.Calendar;

public class User_Address extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener SetDate;
    boolean ischeck = false;
    ProgressDialog pg;
    EditText User_Address_Address,User_Address_DOB;
    Button User_Address_Button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        pg = new ProgressDialog(this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Address Recognise ... ");
        pg.setCanceledOnTouchOutside(false);
        User_Address_Address =findViewById(R.id.User_Address_Address);
        User_Address_DOB = findViewById(R.id.User_Address_DOB);
        User_Address_Button=findViewById(R.id.User_Address_Button);
        User_Address_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ischeck=check();
                if(ischeck)
                {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        });
        User_Address_DOB.setFocusable(false);
        User_Address_DOB.setClickable(true);
        User_Address_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(User_Address.this, android.R.style.Theme_Holo_Dialog_MinWidth,SetDate,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        SetDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String dob = day+"/"+month+"/"+year;
                User_Address_DOB.setText(dob);
            }
        };
    }
    private boolean check()
    {
        if(User_Address_Address.length() == 0)
        {
            User_Address_Address.setError("Address Is Required Field");
            return false;
        }
        if(User_Address_DOB.length() == 0)
        {
            User_Address_DOB.setError("Date Of Birth is Required");
            return  false;
        }
        return  true;
    }
}