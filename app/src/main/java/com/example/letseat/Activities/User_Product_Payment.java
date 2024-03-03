package com.example.letseat.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.letseat.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class User_Product_Payment extends AppCompatActivity implements PaymentResultListener {

    TextInputEditText temp;
    Button tempbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_payment);
        Checkout.preload(getApplicationContext());
        temp=findViewById(R.id.User_Product_Payment_Apply_Code);
        tempbn=findViewById(R.id.User_Product_Payment_Code_Check);
        tempbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
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
            options.put("amount", "10000");//500*100
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
        Toast.makeText(this, "Payment Successfully :- "+s, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), Payment_Thankyou.class));
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed :- "+s, Toast.LENGTH_SHORT).show();

    }
}