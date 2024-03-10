package com.example.letseat.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.Adapter.CartAdapter;
import com.example.letseat.Adapter.PaymentAdapter;
import com.example.letseat.Model.Cart;
import com.example.letseat.Model.Payment;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Account_Payment_History extends AppCompatActivity {

    RecyclerView User_Account_Payment_History_Recyclerview;
    SearchView User_Account_Payment_History_Searchview;
    RetrofitServices retrofitServices;
    UserApi userApi;
    ArrayList<Payment> list;
    PaymentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_payment_history);
        User_Account_Payment_History_Recyclerview=findViewById(R.id.User_Account_Payment_History_Recyclerview);
        User_Account_Payment_History_Searchview=findViewById(R.id.User_Account_Payment_History_Searchview);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        list = new ArrayList<>();
        setData();
    }
    private void setData(){
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile","");
        userApi.getPaymentByMobileNo(Long.parseLong(mobile)).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                list= (ArrayList<Payment>) response.body();
                adapter=new PaymentAdapter(list,User_Account_Payment_History.this);
                User_Account_Payment_History_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Account_Payment_History.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                User_Account_Payment_History_Recyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
    }
}