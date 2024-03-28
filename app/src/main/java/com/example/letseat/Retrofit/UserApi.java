package com.example.letseat.Retrofit;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;

import com.example.letseat.Model.Cart;
import com.example.letseat.Model.ForgetPassword;
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


    @GET(AppConstants.api+"/lets-eat/user")
    Call<List<User>> getUser();


    @POST(AppConstants.api+"/lets-eat/user/add")
    Call<User> save(@Body User user);


    @POST(AppConstants.api+"/lets-eat/user/login")
    Call<String> Verify(@Body Login login);

    @GET(AppConstants.api+"/lets-eat/user/get-by-mobile-no")
    Call<User> getSingleUser(
           @Query("mobileNo") long id
           );

    @PUT(AppConstants.api+"/lets-eat/user/update")
    Call<User> updateProfile(
            @Query("userId") int id,@Body User user
    );

    @PUT(AppConstants.api+"/lets-eat/user/update-password-by-email")
    Call<User> updatePasswordByEmail(
            @Query("email") String mail ,@Query("password") String pass
    );

    @GET(AppConstants.api+"/lets-eat/user/verify-email")
    Call<String> verifyEmailForForgetPass(
            @Query("email") String mail
    );

    @GET(AppConstants.api+"/lets-eat/offer/get-all")
    Call<List<Offer>> getAllOffer();

    @GET(AppConstants.api+"/lets-eat/product/get-by-category-id")
    Call<List<Product>> getSingleProduct(
            @Query("categoryId") int id
    );

    @GET(AppConstants.api+"/lets-eat/product/get")
    Call<Product> getProductById(
            @Query("productId") int id
    );

    @GET(AppConstants.api+"/lets-eat/product/get-all")
    Call<List<Product>> getAllProduct();

    @GET(AppConstants.api+"/lets-eat/product/search")
    Call<List<Product>> searchProduct(
            @Query("keyword") String key
    );

    @POST(AppConstants.api+"/lets-eat/cart/add")
    Call<Cart> setCart(@Body Cart cart);

    @GET(AppConstants.api+"/lets-eat/cart/get-by-mobile-no")
    Call<List<Cart>> getCartByMobileNumber(
            @Query("mobileNo") long mobileNo
    );

    @GET(AppConstants.api+"/lets-eat/cart/get-total-by-mobile-no")
    Call<Double> getCartTotalByMobileNo(
            @Query("mobileNo") long mobileNo
    );

    @PUT(AppConstants.api+"/lets-eat/cart/update")
    Call<Cart> updateCart(
            @Query("cartId") int id,@Body Cart cart
    );

    @DELETE(AppConstants.api+"/lets-eat/cart/delete")
    Call<String> deleteCartById(
            @Query("cartId") int id
    );

    @DELETE(AppConstants.api+"/lets-eat/cart/delete-by-mobile-no")
    Call<String> deleteCartByMobileNo(
            @Query("mobileNo") long mobileNo
    );

    @POST(AppConstants.api+"/lets-eat/payment/add")
    Call<Payment> setPayment(@Body Payment payment);

    @GET(AppConstants.api+"/lets-eat/payment/get-by-mobile-no")
    Call<List<Payment>> getPaymentByMobileNo(
            @Query("mobileNo") long id
    );

    @PUT(AppConstants.api+"/lets-eat/payment/update-by-mobile-no")
    Call<Payment> updatePaymentByMobileNo(
            @Query("mobileNo") long id,@Body Payment p
    );

    @POST(AppConstants.api+"/lets-eat/orders/execute-order")
    Call<Order> setOrder(@Body Order order);

    @GET(AppConstants.api+"/lets-eat/orders/get-by-mobile-no")
    Call<List<Order>> getAllOrder(
            @Query("mobileNo") long mobile
    );

    @GET(AppConstants.api+"/lets-eat/orders/get")
    Call<Order> getSingleOrder(
            @Query("orderId") int id
    );

    @DELETE(AppConstants.api+"/lets-eat/orders/delete")
    Call<String> deleteByOrderId(
            @Query("orderId") int id
    );

    @DELETE(AppConstants.api+"/lets-eat/payment/delete-by-order-id")
    Call<String> deletePaymentByOrderId(
            @Query("orderId") int id
    );

    @PUT(AppConstants.api+"/lets-eat/orders/update")
    Call<Order> updateOrder(
            @Query("orderId") int id,@Body Order order
            );

    @GET(AppConstants.api+"/lets-eat/product/category-wise-search")
    Call<List<Product>> searchProduct(
            @Query("keyword") String key,@Query("categoryId") int id
    );

    @GET(AppConstants.api+"/lets-eat/product/filter")
    Call<List<Product>> filterProduct(
            @Query("categoryId") int cid,@Query("type") String type,@Query("maxPrice") double price
    );

    @GET(AppConstants.api+"/lets-eat/discount-code/verify")
    Call<String> verify(
            @Query("discountCode") String code
    );
    @GET(AppConstants.api+"/lets-eat/payment/get")
    Call<Payment> getSinglePayment(
            @Query("paymentId") int id
    );
}
