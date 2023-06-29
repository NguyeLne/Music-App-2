package com.example.mymusic2.api;

import com.example.mymusic2.model.response.PlaylistResponse;
import com.example.mymusic2.model.response.ResponseObject;
import com.example.mymusic2.model.response.SongResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface API {

    @GET("/api/song")
    Call<ResponseObject<SongResponse>> findSongByAlbumName(@Query("abumName") String abumName);

    @GET("/api/song/new")
    Call<ResponseObject<SongResponse>> findNewSong(@Query("abumName") String abumName);

    @GET("/api/song")
    Call<ResponseObject<SongResponse>> findSongsByName(@Query("name") String name);

    @GET("/api/song")
    Call<ResponseObject<SongResponse>> findSongByCategory(@Query("category") String category);

    @GET("/api/album/addSong")
    Call<SongResponse> addSongToAlbum(@Query("songId") Long songId, @Query("playlistId") Long playlistId);

    @GET("/api/album")
    Call<List<PlaylistResponse>> findAllAlbum();

    @DELETE("/api/album/deleteSong")
    Call<SongResponse> deleteSongFromAlbum(@Query("songId") Long songId, @Query("playlistId") Long playlistId);

    @POST("/api/album")
    Call<PlaylistResponse> createAlbum(@Body PlaylistResponse playlistResponse);

    @GET("/api/album/songs")
    Call<List<SongResponse>> findSongInAlbum(@Query("playlistId") Long playlistId);

    @DELETE("/api/album")
    Call <PlaylistResponse> deleteAlbum(@Query("playlistId") Long playlistId);

    @GET("/api/album/findAlbumBySong")
    Call<List<PlaylistResponse>> findAlbumBySong(@Query("songId") Long songId);
}
