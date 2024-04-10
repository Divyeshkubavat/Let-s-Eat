package com.example.letseat.Activities;

import static com.example.letseat.Activities.MainActivity.listener;
import static java.lang.Long.parseLong;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Address;
import okhttp3.FormBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Registration extends AppCompatActivity {

    LottieAnimationView User_registration_Animation;
    TextInputEditText User_Registration_Mobile,User_Registration_Pass,User_Registration_Email,User_Registration_Fullname;
    Button User_Reistration_Button;
    TextView User_Registration_Login;


    RetrofitServices retrofitServices;
    List<User> user;
    UserApi userApi;
    String User_New_UID;
    SharedPreferences User_Registration_Check_UID;

    private ProgressDialog User_Registration_Progressbar;
    TextView registrartiontext;

    boolean ischeck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        User_Registration_Progressbar = new ProgressDialog(this);
        User_Registration_Progressbar.setTitle("Loading..... ");
        User_Registration_Progressbar.setMessage("Please wait register your account .... ");
        User_Registration_Progressbar.setIcon(R.drawable.logo);
        User_Registration_Progressbar.setCanceledOnTouchOutside(false);
        User_registration_Animation = findViewById(R.id.User_Registration_Animation);
        User_Registration_Fullname = findViewById(R.id.User_Registration_Fullname);
        User_Registration_Login = findViewById(R.id.User_Registration_Login);
        User_Registration_Email = findViewById(R.id.User_Registration_Email);
        User_Registration_Mobile=findViewById(R.id.User_Registration_Mobile);
        User_Registration_Pass=findViewById(R.id.User_Registration_Pass);

        User_Registration_Fullname.setText("");
        User_Registration_Email.setText("");
        User_Registration_Mobile.setText("");
        User_Registration_Pass.setText("");
        User_Reistration_Button = findViewById(R.id.User_Registration_Button);
        registrartiontext = findViewById(R.id.registrartiontext);

        User_Reistration_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ischeck=check();
                if(ischeck)
                {
                    sendData();
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
            User_Registration_Mobile.setError("Mobile number is Required");
            return  false;
        } else if (User_Registration_Mobile.length() < 10) {
            User_Registration_Mobile.setError("Mobile Number Must Be 10 Digits");
            return false;
        }
        if(User_Registration_Email.length() == 0)
        {
            User_Registration_Email.setError("Email Is Required Field");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(User_Registration_Email.getText().toString()).matches()) {
            User_Registration_Email.setError("Email Must Be In Email Format");
            return false;
        }
        if(User_Registration_Pass.length() == 0)
        {
            User_Registration_Pass.setError("Password Is Required Field");
            return false;
        } else if (User_Registration_Pass.length() < 8) {
            User_Registration_Pass.setError("Password Must Be 8 Character");
            return false;
        }
        return  true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void sendData() {
        String name,pass,email;
        String mobile;
        name = User_Registration_Fullname.getText().toString();
        pass = User_Registration_Pass.getText().toString();
        email = User_Registration_Email.getText().toString();
        mobile = User_Registration_Mobile.getText().toString();

        Intent intent = new Intent(getApplicationContext(), User_Address.class);
        intent.putExtra("User_Name",name);
        intent.putExtra("User_Pass",pass);
        intent.putExtra("User_Email",email);
        intent.putExtra("User_Mobile",mobile);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(listener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(listener);
        super.onStop();
    }
}