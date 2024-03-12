package com.example.letseat.Activities;

import static com.example.letseat.Activities.MainActivity.listener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.letseat.R;

public class User_Home_Filter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_filter);
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