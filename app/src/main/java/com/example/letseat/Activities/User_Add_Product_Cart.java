package com.example.letseat.Activities;

import android.os.Bundle;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.letseat.R;

public class User_Add_Product_Cart extends AppCompatActivity {

    RatingBar User_Add_Product_Cart_Ratingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_product_cart);
        User_Add_Product_Cart_Ratingbar=findViewById(R.id.User_Add_Product_Cart_Ratingbar);
        User_Add_Product_Cart_Ratingbar.setFocusable(false);
        float i = 3;
        User_Add_Product_Cart_Ratingbar.setRating(i);

    }
}