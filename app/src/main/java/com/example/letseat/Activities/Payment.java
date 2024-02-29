package com.example.letseat.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;

public class Payment extends AppCompatActivity {

    RadioButton cash_on_delivery,upi;


    TextView total,charge,final_total;
    RecyclerView recyclerView;
    TextInputEditText address;
    Button pay;
    String mo;

    String  temp;
    int year,month,date,hour,minute;

    int t_total = 0;
        int t_final;
    int t_charge = 40;
   List<String> list;

    String key;
    String add="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable drawable = new ColorDrawable(Color.parseColor("#281818"));
        actionBar.setBackgroundDrawable(drawable);
        cash_on_delivery = findViewById(R.id.payment_cash_on_delivery);
        upi = findViewById(R.id.payment_upi);
        total = findViewById(R.id.payment_total);
        charge = findViewById(R.id.payment_charge);
        final_total = findViewById(R.id.payment_Final_Total);
        recyclerView = findViewById(R.id.payment_recycler_view);
        address =  findViewById(R.id.payment_address);
        pay = findViewById(R.id.payment_pay);
        SharedPreferences getpreference = getSharedPreferences("login", Context.MODE_PRIVATE);
        mo = getpreference.getString("mobile","");

        Intent i = getIntent();
        temp = i.getStringExtra("final");
        String[] temp2 = temp.split(" ");
        total.setText(temp2[2]);
        t_total = Integer.parseInt(temp2[2]);
        t_final = t_charge + t_total;
        final_total.setText(String.valueOf(t_final));
        ProgressDialog pg = new ProgressDialog(Payment.this);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        date = calendar.get(Calendar.DAY_OF_MONTH);
        hour =calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

    }

    public void PaymentGatway() throws AppNotFoundException {
        String temp = final_total.getText().toString();
        String f = temp+".00";
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(Payment.this)
                .setPayeeName("Divyesh")
                .setPayeeVpa("dkubavat0@oksbi")
                .setPayeeMerchantCode("")
                .setAmount(f)
                .setDescription("Order Payment")
                .setTransactionId("1234567890")
                .setTransactionRefId("1234567890");
        EasyUpiPayment upi = builder.build();
        upi.startPayment();
    }
}