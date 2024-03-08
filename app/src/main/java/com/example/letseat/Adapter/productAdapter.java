package com.example.letseat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.letseat.Activities.User_Add_Product_Cart;
import com.example.letseat.Model.Product;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;

public class productAdapter extends RecyclerView.Adapter<productAdapter.MyViewHolder> {
    ArrayList<Product> list;
    Context context;
    RetrofitServices retrofitServices;
    UserApi userApi;
    public productAdapter(ArrayList<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        return new productAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_user_home_product_view_design,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Product p = list.get(position);
        holder.name.setText(p.getName());
        String pri = String.valueOf(p.getPrice());
        holder.price.setText(pri);
        Glide.with(context).load(p.getImageUrl()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), User_Add_Product_Cart.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name,price;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.User_Home_Product_View_Design_Image);
            name=itemView.findViewById(R.id.User_Home_Product_View_Design_Name);
            price=itemView.findViewById(R.id.User_Home_Product_View_Design_Price);
        }
    }
}
