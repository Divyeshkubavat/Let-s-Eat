package com.example.letseat.Activities;

import static com.example.letseat.Activities.MainActivity.listener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
    ProgressDialog pg;
    productAdapterExplore burgerAdapter;
    ArrayList<Product> burgerList;
    LottieAnimationView burger_lottie;
    ImageView User_burger_Explore_Filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_burger_explore);

        User_Burger_Explore_Searchview = findViewById(R.id.User_Burger_Explore_Searchview);
        User_Burger_Explore_Recyclerview=findViewById(R.id.User_burger_Explore_Recyclerview);
        User_burger_Explore_Filter=findViewById(R.id.User_burger_Explore_Filter);
        burger_lottie=findViewById(R.id.burger_lottie);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        burgerList=new ArrayList<>();
        pg = new ProgressDialog(User_Burger_Explore.this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait we fetch your data... ");
        pg.setIcon(R.drawable.logo);
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        setProduct();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },600);
        User_burger_Explore_Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),User_Product_Filter.class));
            }
        });
        User_Burger_Explore_Searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    private  void setProduct(){
        userApi.getSingleProduct(201).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                burgerList = (ArrayList<Product>) response.body();
                burgerAdapter = new productAdapterExplore(burgerList,User_Burger_Explore.this);
                User_Burger_Explore_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Burger_Explore.this,LinearLayoutManager.VERTICAL,false));
                if(burgerAdapter.getItemCount()==0)
                {
                    burger_lottie.setVisibility(View.VISIBLE);
                }
                else {
                    burger_lottie.setVisibility(View.GONE);
                    burgerAdapter.notifyDataSetChanged();
                    User_Burger_Explore_Recyclerview.setAdapter(burgerAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    private void search(String key){
        userApi.searchProduct(key,201).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                burgerList= (ArrayList<Product>) response.body();
                burgerAdapter=new productAdapterExplore(burgerList,User_Burger_Explore.this);
                User_Burger_Explore_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Burger_Explore.this,LinearLayoutManager.VERTICAL,false));

                    burger_lottie.setVisibility(View.GONE);
                    burgerAdapter.notifyDataSetChanged();
                    User_Burger_Explore_Recyclerview.setAdapter(burgerAdapter);

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