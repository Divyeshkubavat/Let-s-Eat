package com.example.letseat.Activities;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.letseat.Model.User;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Account_Edit_Profile extends AppCompatActivity {

    TextInputEditText User_Account_Edit_Name,User_Account_Edit_Mobile,User_Account_Edit_Address,User_Account_Edit_Pass,User_Account_Edit_DOB,User_Account_Edit_Email;
    Button User_Account_Update_Button;
    boolean isCheck = false;
    private DatePickerDialog.OnDateSetListener SetDate;
    String Login_Mobile;
    RetrofitServices retrofitServices;
    UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_edit_profile);
        User_Account_Edit_Email=findViewById(R.id.User_Account_Edit_Email);
        User_Account_Edit_DOB=findViewById(R.id.User_Account_Edit_DOB);
        User_Account_Edit_Address=findViewById(R.id.User_Account_Edit_Address);
        User_Account_Edit_Pass=findViewById(R.id.User_Account_Edit_Pass);
        User_Account_Edit_Name=findViewById(R.id.User_Account_Edit_Name);
        User_Account_Edit_Mobile=findViewById(R.id.User_Account_Edit_Mobile);
        User_Account_Update_Button=findViewById(R.id.User_Account_Update_Button);
        SharedPreferences preferences =getSharedPreferences("Login", MODE_PRIVATE);
        Login_Mobile = preferences.getString("Login_Mobile","");
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            User_Account_Edit_Mobile.setFocusable(View.NOT_FOCUSABLE);
        }
        getSingleUserData();

        User_Account_Edit_Mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(User_Account_Edit_Mobile,"You Not Change Mobile Number",Snackbar.LENGTH_SHORT).show();
            }
        });
        User_Account_Update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck=check();
                if(isCheck)
                {
                    Toast.makeText(User_Account_Edit_Profile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        User_Account_Edit_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getApplicationContext(), android.R.style.Theme_Holo_Dialog_MinWidth,SetDate,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        SetDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String dob = day+"/"+month+"/"+year;
                User_Account_Edit_DOB.setText(dob);
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean check() {
        if(User_Account_Edit_Name.length() == 0)
        {
            User_Account_Edit_Name.setError("Username Is Required Field");
            return false;
        }
        if(User_Account_Edit_Mobile.length() < 0)
        {
            User_Account_Edit_Mobile.setError("Mobile number is Required");
            return  false;
        } else if (User_Account_Edit_Mobile.length() < 10) {
            User_Account_Edit_Mobile.setError("Mobile Number Must Be 10 Digits");
            return false;
        }
        if(User_Account_Edit_Email.length() == 0)
        {
            User_Account_Edit_Email.setError("Email Is Required Field");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(User_Account_Edit_Email.getText().toString()).matches()) {
            User_Account_Edit_Email.setError("Email Must Be In Email Format");
            return false;
        }
        if(User_Account_Edit_Pass.length() == 0)
        {
            User_Account_Edit_Pass.setError("Password Is Required Field");
            return false;
        } else if (User_Account_Edit_Pass.length() < 8) {
            User_Account_Edit_Pass.setError("Password Must Be * Character");
            return false;
        }
        if(User_Account_Edit_Address.length() == 0)
        {
            User_Account_Edit_Address.setError("Address Is Required Field");
            return false;
        }
        if(User_Account_Edit_DOB.length() == 0)
        {
            User_Account_Edit_DOB.setError("Date Of Birth is Required");
            return  false;
        }
        return  true;
    }
    public void getSingleUserData(){
        userApi.getSingleUser(30).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String name = response.body().getName();
                String pass = response.body().getPassword();
                String mobile = String.valueOf(response.body().getMobileNo());
                String address = response.body().getAddress();
                String dob = response.body().getDateOfBirth();
                String email = response.body().getEmail();

                User_Account_Edit_DOB.setText(dob);
                User_Account_Edit_Address.setText(address);
                User_Account_Edit_Email.setText(email);
                User_Account_Edit_Mobile.setText(mobile);
                User_Account_Edit_Name.setText(name);
                User_Account_Edit_Pass.setText(pass);

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}