package com.example.mymusic2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mymusic2.Adapter.SongAdapter;
import com.example.mymusic2.R;
import com.example.mymusic2.model.response.SongResponse;

import java.util.List;

public class ListSongPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView ten;
    private String ten1;
    private RecyclerView dsphat;

    private SongAdapter songAdapter;

    private List<SongResponse> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_phat);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ten1 = getIntent().getStringExtra("pos");
        ten.setText(ten1);
        songs = (List<SongResponse>) getIntent().getSerializableExtra("listSong");
        songAdapter = new SongAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        dsphat.setLayoutManager(linearLayoutManager);
        songAdapter.setData(ListSongPlayActivity.this, songs);
        dsphat.setAdapter(songAdapter);
    }

    private void initView() {
        ten =findViewById(R.id.ten);
        dsphat = findViewById(R.id.dsphat);

    }

    @Override
    public void onClick(View v) {

    }
}