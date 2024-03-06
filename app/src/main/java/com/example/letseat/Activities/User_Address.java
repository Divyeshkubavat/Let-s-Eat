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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.letseat.Model.User;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Address extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener SetDate;
    boolean ischeck = false;
    ProgressDialog pg;
    EditText User_Address_Address,User_Address_DOB;
    Button User_Address_Button;
    RetrofitServices retrofitServices;
    UserApi userApi;

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

        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);

        User_Address_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
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
    public void sendData()
    {
        String name,pass,email,address,date;
        String mobile;
        Intent intent = getIntent();
        name= intent.getStringExtra("User_Name");
        pass = intent.getStringExtra("User_Pass");
        email = intent.getStringExtra("User_Email");
        mobile = intent.getStringExtra("User_Mobile");
        address=User_Address_Address.getText().toString();
        date=User_Address_DOB.getText().toString();
        ischeck = check();
        if(ischeck)
        {
            User u = new User();
            u.setMobileNo(Long.parseLong(mobile));
            u.setName(name);
            u.setEmail(email);
            u.setPassword(pass);
            u.setAddress(address);
            u.setDateOfBirth(date);
            userApi.save(u).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(User_Address.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), User_Login.class));
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(User_Address.this, "Mobile Number Already Exist", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }else
        {
            Toast.makeText(this, "Fill All The Field", Toast.LENGTH_SHORT).show();
        }
    }
}