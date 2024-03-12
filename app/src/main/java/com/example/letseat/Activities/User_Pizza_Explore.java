package com.example.letseat.Activities;

import static com.example.letseat.Activities.MainActivity.listener;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
    ProgressDialog pg;
    productAdapterExplore pizzaAdapter;
    ArrayList<Product> pizzaList;
    LottieAnimationView pizza_lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pizza_explore);
        User_Pizza_Explore_Searchview = findViewById(R.id.User_Pizza_Explore_Searchview);
        User_Pizza_Explore_Recyclerview=findViewById(R.id.User_Pizza_Explore_Recyclerview);
        pizza_lottie=findViewById(R.id.pizza_lottie);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        pizzaList=new ArrayList<>();
        pg = new ProgressDialog(User_Pizza_Explore.this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait we fetch your data... ");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        setProduct();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },600);
        User_Pizza_Explore_Searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void setProduct() {
        userApi.getSingleProduct(202).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                pizzaList = (ArrayList<Product>) response.body();
                pizzaAdapter = new productAdapterExplore(pizzaList,User_Pizza_Explore.this);
                User_Pizza_Explore_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Pizza_Explore.this,LinearLayoutManager.VERTICAL,false));
                if(pizzaAdapter.getItemCount()==0){
                    pizza_lottie.setVisibility(View.VISIBLE);
                }
                else{
                    pizza_lottie.setVisibility(View.GONE);
                    pizzaAdapter.notifyDataSetChanged();
                    User_Pizza_Explore_Recyclerview.setAdapter(pizzaAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    private void search(String key){
        userApi.searchProduct(key,202).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                pizzaList= (ArrayList<Product>) response.body();
                pizzaAdapter=new productAdapterExplore(pizzaList,User_Pizza_Explore.this);
                User_Pizza_Explore_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Pizza_Explore.this,LinearLayoutManager.VERTICAL,false));

                    pizza_lottie.setVisibility(View.GONE);
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