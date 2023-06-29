package com.example.mymusic2.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {

    public static Retrofit getClient(String baseUrl, String token){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.cache(null);
        if (token != null && !token.isEmpty()) {
            BearerInterceptor bearerInterceptor = new BearerInterceptor(token);
            httpClient.addInterceptor(bearerInterceptor);
        }

        OkHttpClient okHttpClient = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
    public static Retrofit getClient(String baseUrl){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);
        OkHttpClient okHttpClient = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }
    private static class BearerInterceptor implements Interceptor {

        private String authToken;
        public BearerInterceptor(String token) {
            this.authToken = token;
        }

        @NonNull
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + authToken);

            Request newRequest = builder.build();
            Log.d("REQUEST", "URL: " + newRequest.url());
            Log.d("REQUEST", "Method: " + newRequest.method());
            Log.d("REQUEST", "Headers: " + newRequest.headers().toMultimap());
            Log.d("REQUEST", "Body: " + newRequest.body());
            return chain.proceed(newRequest);
        }
    }
}