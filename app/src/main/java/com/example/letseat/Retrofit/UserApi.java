package com.example.letseat.Retrofit;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;

import com.example.letseat.Model.Login;
import com.example.letseat.Model.Offer;
import com.example.letseat.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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


    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/user/")
    Call<List<User>> getUser();


    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/user/add/")
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
}
