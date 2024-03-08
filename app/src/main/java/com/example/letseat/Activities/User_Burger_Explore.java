package com.example.letseat.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.Adapter.productAdapter;
import com.example.letseat.Adapter.productAdapterExplore;
import com.example.letseat.Model.Product;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Burger_Explore extends AppCompatActivity {

    RecyclerView User_Burger_Explore_Recyclerview;
    SearchView User_Burger_Explore_Searchview;
    RetrofitServices retrofitServices;
    UserApi userApi;
    productAdapterExplore burgerAdapter;
    ArrayList<Product> burgerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_burger_explore);
        User_Burger_Explore_Searchview = findViewById(R.id.User_Burger_Explore_Searchview);
        User_Burger_Explore_Recyclerview=findViewById(R.id.User_burger_Explore_Recyclerview);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        burgerList=new ArrayList<>();
        setProduct();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private  void setProduct(){
        userApi.getSingleProduct(201).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                burgerList = (ArrayList<Product>) response.body();
                burgerAdapter = new productAdapterExplore(burgerList,User_Burger_Explore.this);
                User_Burger_Explore_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Burger_Explore.this,LinearLayoutManager.VERTICAL,false));
                burgerAdapter.notifyDataSetChanged();
                User_Burger_Explore_Recyclerview.setAdapter(burgerAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}