package com.example.letseat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.Model.Payment;
import com.example.letseat.Model.Product;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {
    ArrayList<Payment> list;
    Context context;
    RetrofitServices retrofitServices;
    UserApi userApi;

    public PaymentAdapter(ArrayList<Payment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        return new PaymentAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_user_account_payment_history_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Payment p = list.get(position);
        holder.orderId.setText(p.getOrderId());
        holder.status.setText(p.getStatus());
        holder.date.setText(p.getDate());
        holder.method.setText(p.getPaymentMethod());
        holder.total.setText(String.valueOf(p.getFinalTotal()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView orderId,method,date,status,total;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId=itemView.findViewById(R.id.User_Account_Payment_History_Design_OrderId);
            method=itemView.findViewById(R.id.User_Account_Payment_History_Design_Method);
            date=itemView.findViewById(R.id.User_Account_Payment_History_Design_Date);
            status=itemView.findViewById(R.id.User_Account_Payment_History_Design_Status);
            total=itemView.findViewById(R.id.User_Account_Payment_History_Design_Amount);
        }
    }

}
