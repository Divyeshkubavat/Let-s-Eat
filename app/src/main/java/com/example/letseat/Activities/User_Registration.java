package com.example.letseat.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.letseat.Model.User;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Registration extends AppCompatActivity {

    LottieAnimationView User_registration_Animation;
    TextInputEditText User_Registration_Mobile,User_Registration_Pass,User_Registration_Email,User_Registration_Fullname;
    Button User_Reistration_Button;
    TextView User_Registration_Login;
    String User_New_UID;
    SharedPreferences User_Registration_Check_UID;

    private ProgressDialog User_Registration_Progressbar;

    boolean ischeck = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        User_Registration_Progressbar = new ProgressDialog(this);
        User_Registration_Progressbar.setTitle("Loading..... ");
        User_Registration_Progressbar.setMessage("UID will send to your email .... ");
        User_Registration_Progressbar.setCanceledOnTouchOutside(false);
        User_registration_Animation = findViewById(R.id.User_Registration_Animation);
        User_Registration_Fullname = findViewById(R.id.User_Registration_Fullname);
        User_Registration_Login = findViewById(R.id.User_Registration_Login);
        User_Registration_Email = findViewById(R.id.User_Registration_Email);
        User_Registration_Mobile=findViewById(R.id.User_Registration_Mobile);
        User_Registration_Pass=findViewById(R.id.User_Registration_Pass);
        User_Reistration_Button = findViewById(R.id.User_Registration_Button);
        RetrofitServices retrofitServices = new RetrofitServices();
        UserApi userApi = retrofitServices.getRetrofit().create(UserApi.class);

        User_Reistration_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ischeck=check();
                if(ischeck)
                {
                    User user = new User();
                    user.setName("Divyesh");
                    user.setAddress("Sitanagar");
                    user.setEmail("dkubavat0@gmail.com");
                    user.setMobileNo(Long.parseLong("9904037428"));
                    user.setDateOfBirth("2003/12/12");
                    user.setPassword("diyesh123");
                    userApi.save(user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                            Toast.makeText(User_Registration.this, response+"Save Successfull", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(User_Registration.this, "Sorry", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        User_Registration_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),User_Login.class));
            }
        });


    }
    private boolean check() {
        if(User_Registration_Fullname.length() == 0)
        {
            User_Registration_Fullname.setError("Username Is Requird Field");
            return false;
        }
        if(User_Registration_Mobile.length() < 0)
        {
            User_Registration_Mobile.setError("Mobile number is Requird");
            return  false;
        } else if (User_Registration_Mobile.length() < 10) {
            User_Registration_Mobile.setError("Mobile Number Must Be 10 Digits");
            return false;
        }
        if(User_Registration_Email.length() == 0)
        {
            User_Registration_Email.setError("Email Is Requird Field");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(User_Registration_Email.getText().toString()).matches()) {
            User_Registration_Email.setError("Email Must Be In Email Formate");
            return false;
        }
        if(User_Registration_Pass.length() == 0)
        {
            User_Registration_Pass.setError("Password Is Requird Field");
            return false;
        } else if (User_Registration_Pass.length() < 8) {
            User_Registration_Pass.setError("Password Must Be * Character");
            return false;
        }
        return  true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}