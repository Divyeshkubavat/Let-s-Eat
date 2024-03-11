package com.example.letseat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.letseat.Model.OrderProduct;
import com.example.letseat.Model.Payment;
import com.example.letseat.Model.Product;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.MyViewHolder> {

    ArrayList<OrderProduct> list;
    Context context;
    RetrofitServices retrofitServices;
    UserApi userApi;
    String name,price,image,qty;

    public OrderProductAdapter(ArrayList<OrderProduct> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        return new OrderProductAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_user_product_payment_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductAdapter.MyViewHolder holder, int position) {
        final OrderProduct p = list.get(position);
        int id = p.getProductId();
        userApi.getProductById(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                name = response.body().getName();
                price = String.valueOf(response.body().getPrice());
                qty = String.valueOf(p.getQuantity());
                image = response.body().getImageUrl();
                holder.name.setText(name);
                holder.price.setText(price);
                Glide.with(context).load(image).into(holder.image);
                holder.qty.setText(qty);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image,deleteBtn,editBtn;
        TextView name,price,qty;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.User_Cart_Product_Image);
            name=itemView.findViewById(R.id.User_Cart_Product_Name);
            price=itemView.findViewById(R.id.User_Cart_Product_Price);
            qty=itemView.findViewById(R.id.User_Cart_Product_QTY);
            deleteBtn=itemView.findViewById(R.id.User_Cart_Product_Delete_Button);
            editBtn=itemView.findViewById(R.id.User_Cart_Product_Edit_Button);
        }
    }
}
