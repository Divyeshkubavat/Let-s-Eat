package com.example.letseat.Activities;

import static com.example.letseat.Activities.MainActivity.listener;
import static com.example.letseat.Activities.User_Cart.cartTotal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letseat.Adapter.CartAdapter;
import com.example.letseat.Adapter.paymentProductAdapter;
import com.example.letseat.Model.Cart;
import com.example.letseat.Model.Order;
import com.example.letseat.Model.Payment;
import com.example.letseat.Model.Product;
import com.example.letseat.Model.User;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Product_Payment extends AppCompatActivity implements PaymentResultListener {

    TextInputEditText temp;
    Button User_Product_Payment_Code_Check,User_Product_Payment_Confirm_Button;
    RetrofitServices retrofitServices;
    UserApi userApi;
    paymentProductAdapter adapter;
    ArrayList<Cart> list;
    RecyclerView User_Product_Payment_Recyclerview;
    RadioButton User_Product_Payment_Cash_Delivery_Radio,User_Product_Payment_Razorpay;
    TextInputEditText User_Product_Payment_Address,User_Product_Payment_Apply_Code;
    TextView User_Product_Payment_Total,User_Product_Payment_Delivery_Charge,User_Product_Payment_Discount,User_Product_Payment_Final_Total;
    ProgressDialog pg;
    double deliveryCharge=50,finalTotal;
    double discount=0;
    double total;
    String Payment_Method,Payment_Status;
    int id;
    double code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_payment);

        Checkout.preload(getApplicationContext());
        User_Product_Payment_Code_Check=findViewById(R.id.User_Product_Payment_Code_Check);
        User_Product_Payment_Recyclerview=findViewById(R.id.User_Product_Payment_Recyclerview);
        User_Product_Payment_Cash_Delivery_Radio=findViewById(R.id.User_Product_Payment_Cash_Delivery_Radio);
        User_Product_Payment_Razorpay=findViewById(R.id.User_Product_Payment_Razorpay);
        User_Product_Payment_Address=findViewById(R.id.User_Product_Payment_Address);
        User_Product_Payment_Apply_Code=findViewById(R.id.User_Product_Payment_Apply_Code);
        User_Product_Payment_Total=findViewById(R.id.User_Product_Payment_Total);
        User_Product_Payment_Delivery_Charge=findViewById(R.id.User_Product_Payment_Delivery_Charge);
        User_Product_Payment_Discount=findViewById(R.id.User_Product_Payment_Discount);
        User_Product_Payment_Confirm_Button=findViewById(R.id.User_Product_Payment_Confirm_Button);
        User_Product_Payment_Final_Total=findViewById(R.id.User_Product_Payment_Final_Total);
        User_Product_Payment_Delivery_Charge.setText(String.valueOf(deliveryCharge));
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        list = new ArrayList<>();
        pg = new ProgressDialog(User_Product_Payment.this);
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
        User_Product_Payment_Confirm_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentMethod();
            }
        });
        User_Product_Payment_Code_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeVerify();
            }
        });
    }
    public void startPayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_OJfCigxEwvZrAz");

        checkout.setImage(R.drawable.logo);

        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Let's Eat");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            options.put("theme.color", "#281818");
            options.put("currency", "INR");
            options.put("amount", finalTotal*100);//500*100
            options.put("prefill.email", "dkubavat0@gmail.com");
            options.put("prefill.contact","7096011908");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("Error in starting Razorpay Checkout", e.toString());
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        setOrder();
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile","");
        userApi.deleteCartByMobileNo(Long.parseLong(mobile)).enqueue(new Callback<String>()  {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

        startActivity(new Intent(getApplicationContext(), Payment_Thankyou.class));
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }
    private  void setData(){

        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile","");
        userApi.getCartByMobileNumber(Long.parseLong(mobile)).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                list= (ArrayList<Cart>) response.body();
                adapter=new paymentProductAdapter(list,User_Product_Payment.this);
                User_Product_Payment_Recyclerview.setLayoutManager(new LinearLayoutManager(User_Product_Payment.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();;
                User_Product_Payment_Recyclerview.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {

            }
        });
        userApi.getSingleUser(Long.parseLong(mobile)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                String address = response.body().getAddress();
                User_Product_Payment_Address.setText(address);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        userApi.getCartTotalByMobileNo(Long.parseLong(mobile)).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                total =response.body();
                User_Product_Payment_Total.setText(String.valueOf(total));
                finalTotal = total + deliveryCharge+discount;
                User_Product_Payment_Final_Total.setText(String.valueOf(finalTotal));
            }
            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }
    private void paymentMethod(){
        if(User_Product_Payment_Razorpay.isChecked())
        {
            Payment_Method="RazorPay";
            Payment_Status="Done";
            startPayment();
        }else
        {
            Payment_Method="Cash On Delivery";
            Payment_Status="Pending";
            setOrder();
            SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
            String mobile = preferences.getString("Login_Mobile","");
            userApi.deleteCartByMobileNo(Long.parseLong(mobile)).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
            startActivity(new Intent(getApplicationContext(), Payment_Thankyou.class));
        }
    }
    private void payment(){
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile","");
        Payment p = new Payment();
        p.setDate(String.valueOf(new Date()));
        p.setDiscount(discount);
        p.setMobileNo(Long.parseLong(mobile));
        p.setPaymentMethod(Payment_Method);
        p.setStatus(Payment_Status);
        p.setTotal(total);
        p.setDeliveryCharge(deliveryCharge);
        p.setFinalTotal(finalTotal);
        p.setOrderId(id);
        userApi.setPayment(p).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
            }
            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(User_Product_Payment.this, "Failed"+t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setOrder(){
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile","");
        Order o = new Order();
        o.setMobileNo(Long.parseLong(mobile));
        o.setAddress(User_Product_Payment_Address.getText().toString());
        o.setDate(String.valueOf(new Date()));
        o.setState(1);
        o.setFinalPayment(finalTotal);
        userApi.setOrder(o).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                id=response.body().getId();
                id=id+0;
                payment();
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
            }
        });
    }

    private void   codeVerify(){
        userApi.verify(User_Product_Payment_Apply_Code.getText().toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                code = Double.parseDouble(response.body());
                if(code==0){
                    Toast.makeText(User_Product_Payment.this, "Sorry Code Is Expire", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(User_Product_Payment.this, "Congratulations you Got Discount of "+String.valueOf(code)+"%", Toast.LENGTH_SHORT).show();
                   double temp = total*code/100;
                   User_Product_Payment_Discount.setText(String.valueOf(temp));
                    total=total-temp;
                    finalTotal=total+deliveryCharge;
                    User_Product_Payment_Final_Total.setText(String.valueOf(total+deliveryCharge));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

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