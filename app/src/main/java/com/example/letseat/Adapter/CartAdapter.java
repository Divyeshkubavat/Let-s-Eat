package com.example.letseat.Adapter;

import static android.content.Context.MODE_PRIVATE;

import static com.example.letseat.Activities.User_Cart.cartTotal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.letseat.Activities.User_Add_Product_Cart;
import com.example.letseat.Activities.User_Burger_Explore;
import com.example.letseat.Model.Cart;
import com.example.letseat.Model.Product;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    ArrayList<Cart> list,temp;
    Context context;
    RetrofitServices retrofitServices;
    UserApi userApi;
    String name,price,image,qty;
    int i;


    public CartAdapter(ArrayList<Cart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        return new CartAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_user_cart_product_design,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Cart c = list.get(position);
        userApi.getProductById(c.getProductId()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                name = response.body().getName();
                price = String.valueOf(response.body().getPrice());
                qty = String.valueOf(c.getQuantity());
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
        SharedPreferences preferences = context.getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile","");
        userApi.getCartTotalByMobileNo(Long.parseLong(mobile)).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                double total = response.body();
                cartTotal.setText("Total : "+ String.valueOf(total));
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userApi.deleteCartById(c.getId()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(context, "Cart Product Deleted", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                list.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), User_Add_Product_Cart.class);
                i.putExtra("cid",c.getId());
                i.putExtra("id",c.getProductId());
                i.putExtra("qty",c.getQuantity());
                i.putExtra("type","cart");
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
