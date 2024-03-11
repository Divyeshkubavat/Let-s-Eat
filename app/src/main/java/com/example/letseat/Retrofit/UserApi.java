package com.example.letseat.Retrofit;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;

import com.example.letseat.Model.Cart;
import com.example.letseat.Model.Login;
import com.example.letseat.Model.Offer;
import com.example.letseat.Model.Order;
import com.example.letseat.Model.OrderProduct;
import com.example.letseat.Model.Payment;
import com.example.letseat.Model.Product;
import com.example.letseat.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface UserApi {


    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/user")
    Call<List<User>> getUser();


    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/user/add")
    Call<User> save(@Body User user);


    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/user/login")
    Call<String> Verify(@Body Login login);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/user/get-by-mobile-no")
    Call<User> getSingleUser(
           @Query("mobileNo") long id
           );

    @PUT("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/user/update")
    Call<User> updateProfile(
            @Query("userId") int id,@Body User user
    );

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/offer/get-all")
    Call<List<Offer>> getAllOffer();

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/product/get-by-category-id")
    Call<List<Product>> getSingleProduct(
            @Query("categoryId") int id
    );

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/product/get")
    Call<Product> getProductById(
            @Query("productId") int id
    );

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/product/get-all")
    Call<List<Product>> getAllProduct();

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/product/search")
    Call<List<Product>> searchProduct(
            @Query("keyword") String key
    );

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/cart/add")
    Call<Cart> setCart(@Body Cart cart);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/cart/get-by-mobile-no")
    Call<List<Cart>> getCartByMobileNumber(
            @Query("mobileNo") long mobileNo
    );

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/cart/get-total-by-mobile-no")
    Call<Double> getCartTotalByMobileNo(
            @Query("mobileNo") long mobileNo
    );

    @PUT("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/cart/update")
    Call<Cart> updateCart(
            @Query("cartId") int id,@Body Cart cart
    );

    @DELETE("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/cart/delete")
    Call<String> deleteCartById(
            @Query("cartId") int id
    );

    @DELETE("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/cart/delete-by-mobile-no")
    Call<String> deleteCartByMobileNo(
            @Query("mobileNo") long mobileNo
    );

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/payment/add")
    Call<Payment> setPayment(@Body Payment payment);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/payment/get-by-mobile-no")
    Call<List<Payment>> getPaymentByMobileNo(
            @Query("mobileNo") long id
    );

    @PUT("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/payment/update-by-mobile-no")
    Call<Payment> updatePaymentByMobileNo(
            @Query("mobileNo") long id,@Body Payment p
    );

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/orders/execute-order")
    Call<Order> setOrder(@Body Order order);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/orders/get-by-mobile-no")
    Call<List<Order>> getAllOrder(
            @Query("mobileNo") long mobile
    );

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/orders/get")
    Call<Order> getSingleOrder(
            @Query("orderId") int id
    );

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/orders/update-by-mobile-no")
    Call<Order> updateOrderByMobileNo();
}
