package com.example.mymusic2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mymusic2.Adapter.SongAdapter;
import com.example.mymusic2.R;
import com.example.mymusic2.api.ApiConfig;
import com.example.mymusic2.api.API;
import com.example.mymusic2.model.response.ResponseObject;
import com.example.mymusic2.model.response.SongResponse;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongsActivity extends AppCompatActivity {
    private SongAdapter songAdapter;
    private RecyclerView dsbaihat1;

    private SearchView searchView;
    private API api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);
        initView();
        songAdapter = new SongAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        dsbaihat1.setLayoutManager(linearLayoutManager);
        searchListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL(),SharedPreferencesManager.getAccessToken()).create(API.class);
        loadSongs();
    }

    private void searchListener(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSongs(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchSongs(newText);
                return false;
            }
        });
    }
    private void loadSongs(){
        API api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL(),SharedPreferencesManager.getAccessToken()).create(API.class);
        api.findSongByAlbumName("").enqueue(new Callback<ResponseObject<SongResponse>>() {
            @Override
            public void onResponse(Call<ResponseObject<SongResponse>> call, Response<ResponseObject<SongResponse>> response) {
                if(response.isSuccessful()){
                    songAdapter.setData(SongsActivity.this,response.body().getContent());
                    dsbaihat1.setAdapter(songAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<SongResponse>> call, Throwable t) {

            }
        });
    }

    private void searchSongs(String name){

        api.findSongsByName(name).enqueue(new Callback<ResponseObject<SongResponse>>() {
            @Override
            public void onResponse(Call<ResponseObject<SongResponse>> call, Response<ResponseObject<SongResponse>> response) {
                if(response.isSuccessful()){
                    songAdapter.setData(SongsActivity.this,response.body().getContent());
                    dsbaihat1.setAdapter(songAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<SongResponse>> call, Throwable t) {

            }
        });
    }

    private void initView() {
        dsbaihat1 = findViewById(R.id.dsbaihat);
        searchView = findViewById(R.id.searchMusic);
    }

}