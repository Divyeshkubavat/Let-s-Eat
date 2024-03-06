package com.example.letseat.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.letseat.Model.Login;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Login extends AppCompatActivity {

    TextInputEditText User_Login_Password,User_Login_ID;
    Button User_Login_Button;
    private ProgressDialog User_Login_Progressbar;
    LottieAnimationView User_Login_Animation;
    TextView User_Login_Signup;
    boolean ischeck = false;
    RetrofitServices retrofitServices;
    UserApi userApi;

    List<Login> Login_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        User_Login_Progressbar = new ProgressDialog(this);
        User_Login_Progressbar.setTitle("Loading..... ");
        User_Login_Progressbar.setMessage("Login Your Account ... ");
        User_Login_Progressbar.setCanceledOnTouchOutside(false);
        User_Login_Button = findViewById(R.id.User_Login_Button);
        User_Login_ID=findViewById(R.id.User_Login_ID);
        User_Login_Password=findViewById(R.id.User_Login_Password);
        User_Login_Animation=findViewById(R.id.User_Login_Animation);
        User_Login_Signup=findViewById(R.id.User_Login_Signup);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
       User_Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ischeck = check();
                if(ischeck)
                {
                   User_Login_Progressbar.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            VerifyLogin();
                        }
                    },1500);
                }
                else {
                    Toast.makeText(User_Login.this, "Please enter valid Admin ID or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        User_Login_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),User_Registration.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit 🥺🥺🥺 ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private boolean check() {
        if(User_Login_ID.length() == 0)
        {
            User_Login_ID.setError("This Field Is Required");
            return false;
        } else if (User_Login_ID.length() < 10) {
           User_Login_ID.setError("Mobile Number Must Be 10 Character");
        }
        if(User_Login_Password.length() == 0)
        {
           User_Login_Password.setError("This Field Is Required");
            return false;
        }
        else if (User_Login_Password.length() < 8) {
            User_Login_Password.setError("password minimum length is 8 characters");
            return false;
        }
        return true;
    }

    public void VerifyLogin(){
        String Id,Pass;

        Id = User_Login_ID.getText().toString();
        Pass = User_Login_Password.getText().toString();
        ischeck = check();
        if(ischeck)
        {
            Login login = new Login();
            login.setMobileNo(Long.parseLong(Id));
            login.setPassword(Pass);
            userApi.Verify(login).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body().equals("401"))
                    {
                        Toast.makeText(User_Login.this, "User Noy ", Toast.LENGTH_SHORT).show();
                    } else if (response.body().equals("401")) {
                        Toast.makeText(User_Login.this, "Password Incorect", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }


    }
}