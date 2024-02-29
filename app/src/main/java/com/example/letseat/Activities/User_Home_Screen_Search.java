package com.example.letseat.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.R;

public class User_Home_Screen_Search extends AppCompatActivity {

    SearchView User_Home_Screen_Search_Searchview;
    RecyclerView User_Home_Screen_Search_Recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen_search);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}