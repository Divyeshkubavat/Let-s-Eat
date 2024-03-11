package com.example.letseat.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.letseat.Adapter.OrderAdapter;
import com.example.letseat.Model.Order;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Account_Order extends AppCompatActivity {

    RecyclerView User_Account_Order_Rcyclerview;
    RetrofitServices retrofitServices;
    UserApi userApi;
    ProgressDialog pg;
    ArrayList<Order> list;
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_order);
        User_Account_Order_Rcyclerview=findViewById(R.id.User_Account_Order_Rcyclerview);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        list=new ArrayList<>();
        pg = new ProgressDialog(User_Account_Order.this);
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
        },1000);
    }
    private void setData(){
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile","");
        userApi.getAllOrder(Long.parseLong(mobile)).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                list= (ArrayList<Order>) response.body();
                adapter=new OrderAdapter(list,getApplicationContext());
                User_Account_Order_Rcyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                User_Account_Order_Rcyclerview.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }
}