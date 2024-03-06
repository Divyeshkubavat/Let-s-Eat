package com.example.letseat.Retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServices {
    private Retrofit retrofit;
    public RetrofitServices(){
        initializedRetrofit();
    }

    private void initializedRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
