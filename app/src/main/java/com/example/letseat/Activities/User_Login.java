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
import com.example.letseat.R;
import com.google.android.material.textfield.TextInputEditText;

public class User_Login extends AppCompatActivity {

    TextInputEditText User_Login_Password,User_Login_ID;
    Button User_Login_Button;

    private ProgressDialog User_Login_Progressbar;
    LottieAnimationView User_Login_Animation;
    TextView User_Login_Signup;
    boolean ischeck = false;

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
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
        } else if (User_Login_ID.length() < 18) {
           User_Login_ID.setError("Admin ID Must Be 18 Character");
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
}