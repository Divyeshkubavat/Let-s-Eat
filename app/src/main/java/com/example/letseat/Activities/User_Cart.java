package com.example.letseat.Activities;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.letseat.Adapter.CartAdapter;
import com.example.letseat.Model.Cart;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class User_Cart extends Fragment {

    public static int item;

    RecyclerView cart_RecyclerView;
    public static TextView cartTotal;
    Button cartCheckOut;
    RetrofitServices retrofitServices;
    UserApi userApi;
    CartAdapter adapter;
    ArrayList<Cart> list;
    ProgressDialog pg;
    public  static double total;
    public static LottieAnimationView cart_lottie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_user__cart, container, false);
        cart_RecyclerView=view.findViewById(R.id.cart_recycler_view);
        cartTotal=view.findViewById(R.id.final_total);
        cartCheckOut=view.findViewById(R.id.cart_check_out);
        cart_lottie=view.findViewById(R.id.cart_lottie);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        list = new ArrayList<>();
        pg = new ProgressDialog(getActivity());
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

        cartCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.getItemCount()==0)
                {
                    Toast.makeText(getContext(), "Cart Is Empty", Toast.LENGTH_SHORT).show();
                }else
                {
                    Intent i = new Intent(getContext(), User_Product_Payment.class);
                    startActivity(i);
                }

            }
        });

        return view;
    }
    private  void setData(){
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile","");
        userApi.getCartByMobileNumber(Long.parseLong(mobile)).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                list= (ArrayList<Cart>) response.body();
                adapter=new CartAdapter(list,getContext());
                cart_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                if(adapter.getItemCount()==0)
                {
                    cart_lottie.setVisibility(View.VISIBLE);
                }
                else
                {
                    adapter.notifyDataSetChanged();
                    cart_lottie.setVisibility(View.GONE);
                    cart_RecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        pg.show();
        setData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                pg.dismiss();
            }
        },1000);
    }

    @Override
    public void onStart() {
        super.onStart();
        pg.show();
        setData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                pg.dismiss();
            }
        },1500);
    }

}