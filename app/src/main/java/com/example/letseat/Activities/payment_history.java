package com.example.letseat.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.R;

public class payment_history extends AppCompatActivity {
    RecyclerView payment;
    String mo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable drawable = new ColorDrawable(Color.parseColor("#281818"));
        actionBar.setBackgroundDrawable(drawable);
        payment = findViewById(R.id.payment_recyclerview);
        SharedPreferences getpreference = getSharedPreferences("login", Context.MODE_PRIVATE);
        mo = getpreference.getString("mobile","");

    }

}