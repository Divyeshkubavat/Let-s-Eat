package com.example.letseat.Retrofit;

import com.example.letseat.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/user/")
    Call<List<User>> getAllUser();

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/user/")
    Call<User> save(@Body User user);
}
