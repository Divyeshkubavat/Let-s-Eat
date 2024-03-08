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

public class User_Combo_Explore extends AppCompatActivity {

    RecyclerView User_Combo_Explore_Recyclerview;
    SearchView User_Combo_Explore_Searchview;
    RetrofitServices retrofitServices;
    UserApi userApi;
    productAdapterExplore comboAdapter;
    ArrayList<Product> comboList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_combo_explore);
        User_Combo_Explore_Searchview = findViewById(R.id.User_Combo_Explore_Searchview);
        User_Combo_Explore_Recyclerview=findViewById(R.id.User_Combo_Explore_Recyclerview);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        comboList=new ArrayList<>();
        setProduct();
    }

    private void setProduct() {
        userApi.getSingleProduct(203).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                comboList = (ArrayList<Product>) response.body();
                comboAdapter = new productAdapterExplore(comboList,User_Combo_Explore.this);
                User_Combo_Explore_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Combo_Explore.this,LinearLayoutManager.VERTICAL,false));
                comboAdapter.notifyDataSetChanged();
                User_Combo_Explore_Recyclerview.setAdapter(comboAdapter);
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