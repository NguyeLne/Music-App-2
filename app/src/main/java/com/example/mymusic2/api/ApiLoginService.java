package com.example.mymusic2.api;

import com.example.mymusic2.model.response.Jwt;
import com.example.mymusic2.model.response.UserResponse;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiLoginService {

//    api : http://192.168.1.41:8080/api/login POST
//    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//
//
//    ApiLoginService apiLoginService = new Retrofit.Builder()
//            .baseUrl("http://192.168.1.41:8080")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(ApiLoginService.class);

    ApiLoginService api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL()).create(ApiLoginService.class);



    @FormUrlEncoded
    @POST("/api/login")
    Call<Jwt>getJWT(@Field("username") String username , @Field("password") String password);

    @POST("/api/user")
    Call<UserResponse> saveUser(@Body UserResponse userResponse);

}
