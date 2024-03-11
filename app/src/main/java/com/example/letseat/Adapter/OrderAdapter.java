package com.example.letseat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseat.Activities.User_Account_Order_Detail;
import com.example.letseat.Model.Order;
import com.example.letseat.Model.Payment;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    ArrayList<Order> list;
    Context context;
    RetrofitServices retrofitServices;
    UserApi userApi;

    public OrderAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        return new OrderAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_user_account_order_design,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Order o = list.get(position);
        holder.date.setText(o.getDate());
        holder.mobile.setText(String.valueOf(o.getMobileNo()));
        holder.orderId.setText(String.valueOf(o.getId()));
        int state = o.getState();
        if(state==1){
            holder.status.setText("Order Placed");
        } else if (state==2) {
            holder.status.setText("Order Accepted");
        } else if (state==3) {
            holder.status.setText("Order Proccessing");
        } else if (state==4) {
            holder.status.setText("Out For Delivery");
        }else {
            holder.status.setText("Delivered");
        }
        holder.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),User_Account_Order_Detail.class);
                i.putExtra("oid",o.getId());
                i.putExtra("status",o.getState());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView orderId,mobile,date,status;
        Button nextBtn;
        CircleImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.User_Account_Order_Design_Date);
            mobile=itemView.findViewById(R.id.User_Account_Order_Design_Mobile);
            nextBtn=itemView.findViewById(R.id.User_Account_Order_Design_NextBtn);
            status=itemView.findViewById(R.id.User_Account_Order_Design_Status);
            orderId=itemView.findViewById(R.id.User_Account_Order_Design_OrderId);
            image=itemView.findViewById(R.id.User_Account_Order_Design_Image);
        }
    }

}
