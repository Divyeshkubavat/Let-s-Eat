package com.example.letseat.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.letseat.Model.Cart;
import com.example.letseat.Model.Product;
import com.example.letseat.R;
import com.example.letseat.Retrofit.RetrofitServices;
import com.example.letseat.Retrofit.UserApi;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Add_Product_Cart extends AppCompatActivity {

    RatingBar User_Add_Product_Cart_Ratingbar;
    int id;
    RetrofitServices retrofitServices;
    UserApi userApi;
    ImageView Product_Image;
    TextView Product_Name,Product_Price,Product_Desc,User_Add_Product_Cart_Quantity;
    ImageView Product_Inc,Product_Dec;
    Button Product_Cartbtn;
    String qty;
    int inc=1;
    int cid ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_product_cart);
        User_Add_Product_Cart_Ratingbar=findViewById(R.id.User_Add_Product_Cart_Ratingbar);
        Product_Image=findViewById(R.id.User_Add_Product_Cart_Image);
        Product_Name=findViewById(R.id.User_Add_Product_Cart_Name);
        Product_Price=findViewById(R.id.User_Add_Product_Cart_Price);
        Product_Dec=findViewById(R.id.User_Add_Product_Cart_Minus_Button);
        Product_Desc=findViewById(R.id.User_Add_Product_Cart_Description);
        Product_Inc=findViewById(R.id.User_Add_Product_Cart_Plus_Button);
        Product_Cartbtn=findViewById(R.id.User_Add_Product_Cart_Add_To_Cart_Button);
        User_Add_Product_Cart_Quantity=findViewById(R.id.User_Add_Product_Cart_Quantity);
        User_Add_Product_Cart_Quantity.setText(String.valueOf(inc));
        retrofitServices = new RetrofitServices();
        userApi = retrofitServices.getRetrofit().create(UserApi.class);
        setData();
        Product_Inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inc++;
                User_Add_Product_Cart_Quantity.setText(String.valueOf(inc));
            }
        });
        Product_Dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inc==1)
                {
                }else {
                    inc--;
                    User_Add_Product_Cart_Quantity.setText(String.valueOf(inc));
                }
            }
        });
        Product_Cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCart();
            }
        });

    }
    private void setData(){
        String t;
        Intent i = getIntent();
        id = i.getIntExtra("id",0);
        t=i.getStringExtra("type");
        qty= String.valueOf(i.getIntExtra("qty",1));
        if(Integer.valueOf(qty)==0)
        {
            inc=1;
        }else {
            inc= Integer.parseInt(qty);
        }
        if(Objects.equals(t, "home"))
        {
            userApi.getProductById(id).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    String name = response.body().getName();
                    String price = String.valueOf(response.body().getPrice());
                    String des = response.body().getDescription();
                    String img = response.body().getImageUrl();
                    int rate = response.body().getRating();
                    if(rate==0)
                    {
                        User_Add_Product_Cart_Ratingbar.setFocusable(false);
                        User_Add_Product_Cart_Ratingbar.setRating(4.5F);
                    }
                    else
                    {
                        User_Add_Product_Cart_Ratingbar.setFocusable(false);
                        User_Add_Product_Cart_Ratingbar.setRating(rate);
                    }
                    Product_Name.setText(name);
                    Product_Desc.setText(des);
                    Product_Price.setText(price);
                    Glide.with(getApplicationContext()).load(img).into(Product_Image);
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {

                }
            });

        } else if (Objects.equals(t,"cart")) {
            userApi.getProductById(id).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    String name = response.body().getName();
                    String price = String.valueOf(response.body().getPrice());
                    String des = response.body().getDescription();
                    String img = response.body().getImageUrl();
                    int rate = response.body().getRating();
                    if(rate==0)
                    {
                        User_Add_Product_Cart_Ratingbar.setFocusable(false);
                        User_Add_Product_Cart_Ratingbar.setRating(4.5F);
                    }
                    else
                    {
                        User_Add_Product_Cart_Ratingbar.setFocusable(false);
                        User_Add_Product_Cart_Ratingbar.setRating(rate);
                    }
                    Product_Name.setText(name);
                    Product_Desc.setText(des);
                    Product_Price.setText(price);
                    User_Add_Product_Cart_Quantity.setText(qty);
                    Glide.with(getApplicationContext()).load(img).into(Product_Image);
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {

                }
            });

        } else {
            userApi.getProductById(id).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    String name = response.body().getName();
                    String price = String.valueOf(response.body().getPrice());
                    String des = response.body().getDescription();
                    String img = response.body().getImageUrl();
                    int rate = response.body().getRating();
                    if(rate==0)
                    {
                        User_Add_Product_Cart_Ratingbar.setFocusable(false);
                        User_Add_Product_Cart_Ratingbar.setRating(4.5F);
                    }
                    else
                    {
                        User_Add_Product_Cart_Ratingbar.setFocusable(false);
                        User_Add_Product_Cart_Ratingbar.setRating(rate);
                    }
                    Product_Name.setText(name);
                    Product_Desc.setText(des);
                    Product_Price.setText(price);
                    Glide.with(getApplicationContext()).load(img).into(Product_Image);
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {

                }
            });
        }
    }
    private void setCart(){
        Intent i = getIntent();
        cid=i.getIntExtra("cid",0);
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String mobile = preferences.getString("Login_Mobile", "");
        if (cid == 0) {
            Cart c = new Cart();
            c.setProductId(id);
            c.setMobileNo(Long.parseLong(mobile));
            c.setQuantity(inc);
            userApi.setCart(c).enqueue(new Callback<Cart>() {
                @Override
                public void onResponse(Call<Cart> call, Response<Cart> response) {
                    Toast.makeText(User_Add_Product_Cart.this, "Product Added to Cart", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Cart> call, Throwable t) {

                }
            });
        }else {
            Cart c = new Cart();
            c.setProductId(id);
            c.setQuantity(inc);
            c.setMobileNo(Long.parseLong(mobile));
            userApi.updateCart(cid,c).enqueue(new Callback<Cart>() {
                @Override
                public void onResponse(Call<Cart> call, Response<Cart> response) {
                    Toast.makeText(User_Add_Product_Cart.this, "Cart Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Cart> call, Throwable t) {

                }
            });
        }
    }

}