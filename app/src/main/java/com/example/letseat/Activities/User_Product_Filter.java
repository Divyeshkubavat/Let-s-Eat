package com.example.letseat.Activities;

import static com.example.letseat.Activities.MainActivity.listener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.letseat.R;
import com.google.android.material.slider.Slider;

public class User_Product_Filter extends AppCompatActivity {

    RadioButton burger,pizza,combo,drink;
    SearchView searchView;
    RadioButton veg,nonveg;
    Slider range;
    Button Submit;
    int categoryId;
    double price;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_filter);

        burger=findViewById(R.id.Admin_Product_Filter_Burger_Radio);
        pizza=findViewById(R.id.Admin_Product_Filter_Pizza_Radio);
        combo=findViewById(R.id.Admin_Product_Filter_Combo_Radio);
        drink=findViewById(R.id.Admin_Product_Filter_Drink_Radio);
        veg=findViewById(R.id.Admin_Product_Filter_veg);
        nonveg=findViewById(R.id.Admin_Product_Filter_Nonveg);
        range=findViewById(R.id.Admin_Product_Filter_Slider);
        Submit=findViewById(R.id.Admin_Product_Submit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData();
                Intent i = new Intent(getApplicationContext(),User_Home_Filter.class);
                i.putExtra("categoryId",categoryId);
                i.putExtra("type",type);
                i.putExtra("price",price);
                startActivity(i);
            }
        });

    }
    private void setData(){
        if(burger.isChecked())
        {
            categoryId=201;
        } else if (pizza.isChecked()) {
            categoryId=202;
        } else if (combo.isChecked()) {
            categoryId=203;
        } else if (drink.isChecked()) {
            categoryId=204;
        }else {
            categoryId=0;
        }
        if(veg.isChecked()){
            type="veg";
        } else if (nonveg.isChecked()) {
            type="nonveg";
        }else {
            type="";
        }
        price=  range.getValue();
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