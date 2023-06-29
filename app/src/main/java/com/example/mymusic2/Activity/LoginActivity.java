package com.example.mymusic2.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymusic2.R;
import com.example.mymusic2.api.API;
import com.example.mymusic2.api.ApiConfig;
import com.example.mymusic2.api.ApiLoginService;
import com.example.mymusic2.model.response.PlaylistResponse;
import com.example.mymusic2.model.response.Jwt;
import com.example.mymusic2.model.response.ResponseObject;
import com.example.mymusic2.model.response.SongResponse;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username,password;
    private String taikhoan,matkhau;
    private Button login,mainlayout;


    private API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        login.setOnClickListener(this);
        mainlayout.setOnClickListener(this);
    }
    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        mainlayout = findViewById(R.id.mainlayout);
    }
    @Override
    public void onClick(View v) {
        if(v == login){
            login();





//            Intent intent =  new Intent(this,HomeActivity.class);
//            startActivity(intent);
        }
        if(v == mainlayout){

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }

    private void getUserData() {
        // lay tu data
    }

    private void login() {
        taikhoan = username.getText().toString().trim();
        matkhau = password.getText().toString().trim();


        //call api login
        ApiLoginService.api.getJWT(taikhoan,matkhau).enqueue(new Callback<Jwt>() {
            @Override
            public void onResponse(Call<Jwt> call, Response<Jwt> response) {
                if(response.code() == 200){
                    Jwt jwt = response.body();
                    System.out.println(jwt);

                    SharedPreferencesManager.setAccessToken(jwt.getAccess_token());
                    api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL(),SharedPreferencesManager.getAccessToken()).create(API.class);
                    loadAlbum();
                    loadFavoriteSongs();
                    Intent intent =  new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"Username or password is incorrect",Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Jwt> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Connection to server failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadAlbum(){

        api.findAllAlbum().enqueue(new Callback<List<PlaylistResponse>>() {
            @Override
            public void onResponse(Call<List<PlaylistResponse>> call, Response<List<PlaylistResponse>> response) {
                SharedPreferencesManager.setAlbums(response.body());
                for (PlaylistResponse album : response.body()){
                    if(album.getName().equals("Favorite")){
                        SharedPreferencesManager.setFavoriteId(album.getId());
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PlaylistResponse>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
    private void loadFavoriteSongs(){
        api.findSongByAlbumName("Favorite").enqueue(new Callback<ResponseObject<SongResponse>>() {
            @Override
            public void onResponse(Call<ResponseObject<SongResponse>> call, Response<ResponseObject<SongResponse>> response) {
                if(response.isSuccessful()){
                    SharedPreferencesManager.setFavoriteSongs(response.body().getContent());
                    System.out.println("success");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<SongResponse>> call, Throwable t) {

            }
        });
    }
}