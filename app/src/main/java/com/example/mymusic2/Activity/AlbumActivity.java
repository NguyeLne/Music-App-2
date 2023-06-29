package com.example.mymusic2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.mymusic2.Adapter.AlbumAdapter;
import com.example.mymusic2.R;
import com.example.mymusic2.api.API;
import com.example.mymusic2.api.ApiConfig;
import com.example.mymusic2.model.response.PlaylistResponse;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView dsalbum;
    private AlbumAdapter albumAdapter;
    private FloatingActionButton fab;

    private List<PlaylistResponse> albums;
    private API api;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        dsalbum = findViewById(R.id.dsalbum);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        albumAdapter = new AlbumAdapter();
        albums = SharedPreferencesManager.getAlbums();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        dsalbum.setLayoutManager(linearLayoutManager);
        albumAdapter.setData(this,albums);
        dsalbum.setAdapter(albumAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        albumAdapter.setData(this,SharedPreferencesManager.getAlbums());
        dsalbum.setAdapter(albumAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v == fab){
            openDialog();
        }
    }

    private void findAlbumByUser(){
    }
    private void openDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_new_playlist);
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        EditText text = dialog.findViewById(R.id.add_playlist);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnAdd = dialog.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(view -> {
            api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL(),SharedPreferencesManager.getAccessToken()).create(API.class);
            PlaylistResponse playlistResponse = new PlaylistResponse();
            playlistResponse.setName(text.getText().toString());
            api.createAlbum(playlistResponse).enqueue(new Callback<PlaylistResponse>() {
                @Override
                public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                    albums.add(response.body());
                    SharedPreferencesManager.setAlbums(albums);
                    albumAdapter.setData(AlbumActivity.this,albums);
                    dsalbum.setAdapter(albumAdapter);
                }

                @Override
                public void onFailure(Call<PlaylistResponse> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.setCancelable(true);
        dialog.show();
    }
}