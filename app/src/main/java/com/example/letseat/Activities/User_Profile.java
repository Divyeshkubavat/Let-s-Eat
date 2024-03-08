package com.example.letseat.Activities;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.letseat.Model.User;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Profile extends Fragment {
    ProgressDialog pg;
    TextView User_Account_Mobile,User_Account_Name,User_Account_Email,User_Account_Address,User_Account_DOB;
    Button User_Account_Order_button,User_Account_Payment_button,User_Account_Edit_Button,User_Account_Logout_button;
    String Login_Mobile;
    RetrofitServices retrofitServices;
    UserApi userApi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user__profile, container, false);
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        getSingleUserData();
        pg = new ProgressDialog(getActivity());
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait we fetch your data... ");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                pg.dismiss();
            }
        },1500);
        User_Account_Address = view.findViewById(R.id.User_Account_Address);
        User_Account_Email = view.findViewById(R.id.User_Account_Email);
        User_Account_Mobile = view.findViewById(R.id.User_Account_Mobile);
        User_Account_Name = view.findViewById(R.id.User_Account_Name);
        User_Account_DOB = view.findViewById(R.id.User_Account_DOB);
        User_Account_Edit_Button=view.findViewById(R.id.User_Account_Edit_button);
        User_Account_Logout_button=view.findViewById(R.id.User_Account_Logout_Button);
        User_Account_Order_button=view.findViewById(R.id.User_Account_Order_Button);
        User_Account_Payment_button=view.findViewById(R.id.User_Account_Payment_Button);
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        Login_Mobile = preferences.getString("Login_Mobile","");


        User_Account_Order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), User_Account_Order.class));
            }
        });
        User_Account_Payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),User_Account_Payment_History.class));
            }
        });
        User_Account_Edit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),User_Account_Edit_Profile.class));
            }
        });
        User_Account_Logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Logout ?");
                builder.setMessage("Are you sure you want to logout ?");
                builder.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        editor.apply();
                        startActivity(new Intent(getContext().getApplicationContext(), User_Login.class));
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        return view;
    }
    public void getSingleUserData(){
        SharedPreferences preferences = getActivity().getSharedPreferences("Login",MODE_PRIVATE);
        long Login_Mobile = Long.parseLong(preferences.getString("Login_Mobile",""));
        userApi.getSingleUser(Login_Mobile).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String name = response.body().getName();
                String pass = response.body().getPassword();
                String mobile = String.valueOf(response.body().getMobileNo());
                String address = response.body().getAddress();
                String dob = response.body().getDateOfBirth();
                String email = response.body().getEmail();

                User_Account_DOB.setText(dob);
                User_Account_Address.setText(address);
                User_Account_Email.setText(email);
                User_Account_Mobile.setText(mobile);
                User_Account_Name.setText(name);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        getSingleUserData();
    }
}