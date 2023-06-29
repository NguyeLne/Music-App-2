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
import com.example.mymusic2.model.response.UserResponse;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username,password,repassword;
    private Button resign;
    private String taikhoan,matkhau,matkhau2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        initView();
        resign.setOnClickListener(this);
    }
    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        resign = findViewById(R.id.resign);
    }

    @Override
    public void onClick(View v) {
        if(v == resign){
            Resign();

        }
    }

    public void Resign(){
        taikhoan = username.getText().toString().trim();
        matkhau = password.getText().toString().trim();
        matkhau2 = repassword.getText().toString().trim();
        if(!matkhau.equals(matkhau2)){
            Toast.makeText(this, "Mật khẩu và mật khẩu xác nhận phải trùng nhau", Toast.LENGTH_SHORT).show();
        }
        else {
            UserResponse user = new UserResponse("", "", taikhoan, matkhau, new ArrayList<>());
            ApiLoginService.api.saveUser(user).enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    PlaylistResponse favorite = new PlaylistResponse();
                    favorite.setName("Favorite");
                    favorite.setUserId(response.body().getId());
                    createFavorite(favorite);


                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
        }
    }
    private void createFavorite(PlaylistResponse album){
        API api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL()).create(API.class);
        api.createAlbum(album).enqueue(new Callback<PlaylistResponse>() {
            @Override
            public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                Intent intent =  new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<PlaylistResponse> call, Throwable t) {

            }
        });
    }

}