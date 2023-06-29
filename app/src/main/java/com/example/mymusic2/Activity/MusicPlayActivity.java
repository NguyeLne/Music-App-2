package com.example.mymusic2.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mymusic2.R;
import com.example.mymusic2.api.API;
import com.example.mymusic2.api.ApiConfig;
import com.example.mymusic2.model.response.PlaylistResponse;
import com.example.mymusic2.model.response.SongResponse;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private TextView textsong, songstart, songend;
    private ImageView imageView;

    private SeekBar seekbar;
    private Button btnplay, btnprevious, btnnext, btnfforward, btnfrewind, btnLike, btnAddToPlaylist;

    private int currentSongPosition = 0;
    private MediaPlayer mediaPlayer;
    private RotateAnimation rotate;

    private API api;

    private boolean isLike;

    private List<SongResponse> listSong;

    private int position;

    private SongResponse song;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        initView();
        listener();
        song = (SongResponse) getIntent().getSerializableExtra("song");
        playMusic();
    }

    private void playMusic(){
        textsong.setText(song.getName());
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(10000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setRepeatMode(Animation.RESTART);

        imageView.startAnimation(rotate);
        Picasso.get().load(song.getAvatarLink()).into(imageView);


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        try {
            mediaPlayer.setDataSource(song.getMusicLink());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        seekbar.setMax(mediaPlayer.getDuration());
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                mediaPlayer.start();
            }
        });
        final Handler handler = new Handler();
        MusicPlayActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    songstart.setText(formatDuration(currentPosition));
                    seekbar.setProgress(currentPosition);
                }
                handler.postDelayed(this, 1000);
            }
        });
        songend.setText(formatDuration(mediaPlayer.getDuration()));
        btnplay.setBackgroundResource(com.google.android.exoplayer2.R.drawable.exo_icon_pause);
    }
    @Override
    protected void onResume() {
        super.onResume();
        api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL(),SharedPreferencesManager.getAccessToken()).create(API.class);
        isLike = false;
        listSong = (List<SongResponse>) getIntent().getSerializableExtra("listSong");
        for(SongResponse songResponse : SharedPreferencesManager.getFavoriteSongs()){
            if (songResponse.equals(song)) {
                isLike = true;
                break;
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            currentSongPosition = mediaPlayer.getCurrentPosition();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnplay: {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    currentSongPosition = mediaPlayer.getCurrentPosition();
                    btnplay.setBackgroundResource(com.google.android.exoplayer2.R.drawable.exo_icon_play);
                    imageView.clearAnimation();
                } else {
                    mediaPlayer.seekTo(currentSongPosition);
                    mediaPlayer.start();
                    btnplay.setBackgroundResource(com.google.android.exoplayer2.R.drawable.exo_icon_pause);
                    imageView.startAnimation(rotate);
                }
                break;
            }
            case R.id.btnnext: {
                if(listSong != null && mediaPlayer != null){
                    mediaPlayer.release();
                    for(int i = 0; i < listSong.size();i++){
                        if(listSong.get(i).equals(song)){
                            position = i;
                        }
                    }
                    if(position + 1 < listSong.size()){
                        song = listSong.get(position + 1);
                    }
                    else{
                        song = listSong.get(0);
                    }
                    playMusic();
                }
                break;
            }
            case R.id.btnprevious: {
                if(listSong != null && mediaPlayer != null){
                    mediaPlayer.release();
                    for(int i = 0; i < listSong.size();i++){
                        if(listSong.get(i).equals(song)){
                            position = i;
                        }
                    }
                    if(position - 1 < 0){
                        song = listSong.get(listSong.size()-1);
                    }
                    else{
                        song = listSong.get(position - 1);
                    }
                    playMusic();
                }
                break;
            }
            case R.id.btnfforward:
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                if (currentPosition + 30000 < duration) {
                    mediaPlayer.seekTo(currentPosition + 30000);
                } else {
                    mediaPlayer.seekTo(duration);
                }
                break;

            case R.id.btnfrewind:
                currentPosition = mediaPlayer.getCurrentPosition();
                duration = mediaPlayer.getDuration();
                if (currentPosition - 30000 < duration) {
                    mediaPlayer.seekTo(currentPosition - 30000);
                } else {
                    mediaPlayer.seekTo(duration);
                }
                break;
            case R.id.btnlike: {
                Long songId = song.getId();
                Long favoriteId = SharedPreferencesManager.getFavoriteId();
                if (isLike) {
                    isLike = false;
                    api.deleteSongFromAlbum(songId, favoriteId).enqueue(new Callback<SongResponse>() {
                        @Override
                        public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                            List<SongResponse> list = SharedPreferencesManager.getFavoriteSongs();
                            for(int i = 0; i < list.size(); i++){
                                if(list.get(i).equals(response.body())){
                                    list.remove(i);
                                    break;
                                }
                            }
                            SharedPreferencesManager.setFavoriteSongs(list);
                        }
                        @Override
                        public void onFailure(Call<SongResponse> call, Throwable t) {
                        }
                    });
                } else {
                    isLike = true;
                    api.addSongToAlbum(songId, favoriteId).enqueue(new Callback<SongResponse>() {
                        @Override
                        public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                            List<SongResponse> list = SharedPreferencesManager.getFavoriteSongs();
                            list.add(response.body());
                            SharedPreferencesManager.setFavoriteSongs(list);

                        }

                        @Override
                        public void onFailure(Call<SongResponse> call, Throwable t) {
                        }
                    });
                }
                break;
            }
            case R.id.btn_add_playlist:{
                List<PlaylistResponse> albums = SharedPreferencesManager.getAlbums();
                boolean[] checkedPlaylists = new boolean[albums.size()];
                Arrays.fill(checkedPlaylists, false);
                api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL(),SharedPreferencesManager.getAccessToken()).create(API.class);
                api.findAlbumBySong(song.getId()).enqueue(new Callback<List<PlaylistResponse>>() {
                    @Override
                    public void onResponse(Call<List<PlaylistResponse>> call, Response<List<PlaylistResponse>> response) {
                        for(int i = 0 ; i < albums.size(); i++){
                            if(response.body().contains(albums.get(i))){
                                checkedPlaylists[i] = true;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PlaylistResponse>> call, Throwable t) {
                    }
                });
                List<String> albumNames = SharedPreferencesManager.getAlbums().stream().map(PlaylistResponse::getName).collect(Collectors.toList());
                CharSequence[] albumNamesSequence = albumNames.toArray(new CharSequence[albumNames.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(MusicPlayActivity.this);
                builder.setTitle("Thêm bài hát vào playlist");

                builder.setMultiChoiceItems(albumNamesSequence, checkedPlaylists, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index, boolean ischecked) {
                        Long albumId = albums.get(index).getId();
                        Long songId = song.getId();
                        if(ischecked){
                            api.addSongToAlbum(songId, albumId).enqueue(new Callback<SongResponse>() {
                                @Override
                                public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                                    if(albumId.equals(SharedPreferencesManager.getFavoriteId())){
                                        List<SongResponse> songs = SharedPreferencesManager.getFavoriteSongs();
                                        songs.add(response.body());
                                        SharedPreferencesManager.setFavoriteSongs(songs);
                                    }
                                }
                                @Override
                                public void onFailure(Call<SongResponse> call, Throwable t) {
                                }
                            });
                        }
                        else {
                            api.deleteSongFromAlbum(songId, albumId).enqueue(new Callback<SongResponse>() {
                                @Override
                                public void onResponse(Call<SongResponse> call, Response<SongResponse> response) {
                                    if(albumId.equals(SharedPreferencesManager.getFavoriteId())){
                                        List<SongResponse> songs = SharedPreferencesManager.getFavoriteSongs();
                                        songs.remove(response.body());
                                        SharedPreferencesManager.setFavoriteSongs(songs);
                                    }
                                }
                                @Override
                                public void onFailure(Call<SongResponse> call, Throwable t) {
                                }
                            });
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        currentSongPosition = 0;
        imageView.clearAnimation();
    }

    private void initView(){
        currentSongPosition = 0;
        isLike = false;
        textsong = findViewById(R.id.textsong);
        imageView = findViewById(R.id.imageview);
        seekbar = findViewById(R.id.seekbar);
        btnplay = findViewById(R.id.btnplay);
        btnprevious = findViewById(R.id.btnprevious);
        btnnext = findViewById(R.id.btnnext);
        btnfforward = findViewById(R.id.btnfforward);
        btnfrewind = findViewById(R.id.btnfrewind);
        btnLike = findViewById(R.id.btnlike);
        songstart = findViewById(R.id.songstart);
        songend = findViewById(R.id.songend);
        btnAddToPlaylist = findViewById(R.id.btn_add_playlist);
        mediaPlayer = new MediaPlayer();
    }

    private void listener(){
        btnplay.setOnClickListener(this);
        btnprevious.setOnClickListener(this);
        btnnext.setOnClickListener(this);
        btnfforward.setOnClickListener(this);
        btnfrewind.setOnClickListener(this);
        btnLike.setOnClickListener(this);
        btnAddToPlaylist.setOnClickListener(this);
    }

    private String formatDuration(int duration) {
        int minute = duration / 1000 / 60;
        int second = duration / 1000 % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minute, second);
    }
}