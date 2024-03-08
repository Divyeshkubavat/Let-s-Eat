package com.example.letseat.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class User_Pizza_Explore extends AppCompatActivity {
    RecyclerView User_Pizza_Explore_Recyclerview;
    SearchView User_Pizza_Explore_Searchview;
    RetrofitServices retrofitServices;
    UserApi userApi;
    productAdapterExplore pizzaAdapter;
    ArrayList<Product> pizzaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pizza_explore);
        User_Pizza_Explore_Searchview = findViewById(R.id.User_Pizza_Explore_Searchview);
        User_Pizza_Explore_Recyclerview=findViewById(R.id.User_Pizza_Explore_Recyclerview);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        pizzaList=new ArrayList<>();
        setProduct();
    }

    private void setProduct() {
        userApi.getSingleProduct(202).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                pizzaList = (ArrayList<Product>) response.body();
                pizzaAdapter = new productAdapterExplore(pizzaList,User_Pizza_Explore.this);
                User_Pizza_Explore_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Pizza_Explore.this,LinearLayoutManager.VERTICAL,false));
                pizzaAdapter.notifyDataSetChanged();
                User_Pizza_Explore_Recyclerview.setAdapter(pizzaAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}