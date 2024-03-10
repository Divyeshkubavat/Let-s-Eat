package com.example.letseat.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.Adapter.CartAdapter;
import com.example.letseat.Adapter.productAdapterExplore;
import com.example.letseat.Model.Cart;
import com.example.letseat.Model.Product;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Home_Screen_Search extends AppCompatActivity {

    SearchView User_Home_Screen_Search_Searchview;
    RecyclerView User_Home_Screen_Search_Recyclerview;
    RetrofitServices retrofitServices;
    UserApi userApi;
    productAdapterExplore adapter;
    ArrayList<Product> list;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_screen_search);
        User_Home_Screen_Search_Recyclerview=findViewById(R.id.User_home_Screen_Search_Recyclerview);
        User_Home_Screen_Search_Searchview=findViewById(R.id.User_Home_Screen_Search_Searchview);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        list = new ArrayList<>();
        pg = new ProgressDialog(User_Home_Screen_Search.this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait we fetch your data... ");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        setData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                pg.dismiss();
            }
        },1500);
        User_Home_Screen_Search_Searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void setData(){
        userApi.getAllProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list= (ArrayList<Product>) response.body();
                adapter=new productAdapterExplore(list,User_Home_Screen_Search.this);
                User_Home_Screen_Search_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Home_Screen_Search.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                User_Home_Screen_Search_Recyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    private void search(String key){
        userApi.searchProduct(key).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list= (ArrayList<Product>) response.body();
                adapter=new productAdapterExplore(list,User_Home_Screen_Search.this);
                User_Home_Screen_Search_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Home_Screen_Search.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                User_Home_Screen_Search_Recyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
}