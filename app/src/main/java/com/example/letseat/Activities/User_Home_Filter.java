package com.example.letseat.Activities;

import static com.example.letseat.Activities.MainActivity.listener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

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

public class User_Home_Filter extends AppCompatActivity {

    SearchView User_Home_Filter_Searchview;
    RecyclerView User_home_Filter_Recyclerview;
    RetrofitServices retrofitServices;
    UserApi userApi;
    productAdapterExplore adapter;
    ArrayList<Product> list;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_filter);

        User_Home_Filter_Searchview=findViewById(R.id.User_Home_Filter_Searchview);
        User_home_Filter_Recyclerview=findViewById(R.id.User_home_Filter_Recyclerview);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        list = new ArrayList<>();
        pg = new ProgressDialog(this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait we fetch your data... ");
        pg.setIcon(R.drawable.logo);
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        setData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                pg.dismiss();
            }
        },1500);
    }

    private void setData(){
        Intent i = getIntent();
        String type=i.getStringExtra("type");
        int categoryId=i.getIntExtra("categoryId",0);
        double price=i.getDoubleExtra("price",0);
        userApi.filterProduct(categoryId,type,price).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list= (ArrayList<Product>) response.body();
                adapter=new productAdapterExplore(list,User_Home_Filter.this);
                User_home_Filter_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Home_Filter.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                User_home_Filter_Recyclerview.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
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