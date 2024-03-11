package com.example.letseat.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.Model.OrderProduct;
import com.example.letseat.Model.Payment;
import com.example.letseat.Model.Product;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

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
        String oid = String.valueOf(p.getOrderId());
        holder.orderId.setText(oid);
        String state=p.getStatus();
        if(Objects.equals(state,"Done"))
        {
            holder.status.setText(state+"   ");
            holder.status.setTextColor(Color.parseColor("#FF4CAF50"));
            holder.total.setText(String.valueOf(p.getFinalTotal())+"   ");
            holder.total.setTextColor(Color.parseColor("#FF4CAF50"));

        }else {
            holder.status.setText(state);
            holder.total.setText(String.valueOf(p.getFinalTotal()));
        }
        holder.date.setText(p.getDate());
        holder.method.setText(p.getPaymentMethod());

        String check=p.getPaymentMethod();
        if(Objects.equals(check,"RazorPay"))
        {
            holder.image.setImageResource(R.drawable.razorpay);
        }else {
            holder.image.setImageResource(R.drawable.cod);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView orderId,method,date,status,total;
        CircleImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId=itemView.findViewById(R.id.User_Account_Payment_History_Design_OrderId);
            method=itemView.findViewById(R.id.User_Account_Payment_History_Design_Method);
            date=itemView.findViewById(R.id.User_Account_Payment_History_Design_Date);
            status=itemView.findViewById(R.id.User_Account_Payment_History_Design_Status);
            total=itemView.findViewById(R.id.User_Account_Payment_History_Design_Amount);
            image=itemView.findViewById(R.id.User_Account_Payment_History_Design_Image);
        }
    }

}
