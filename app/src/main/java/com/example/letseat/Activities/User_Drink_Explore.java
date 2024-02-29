package com.example.letseat.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.R;

public class User_Drink_Explore extends AppCompatActivity {

    RecyclerView User_Drink_Explore_Recyclerview;
    SearchView User_Drink_Explore_Searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_drink_explore);
        User_Drink_Explore_Searchview = findViewById(R.id.User_Drink_Explore_Searchview);
        User_Drink_Explore_Recyclerview=findViewById(R.id.User_Drink_Explore_Recyclerview);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}