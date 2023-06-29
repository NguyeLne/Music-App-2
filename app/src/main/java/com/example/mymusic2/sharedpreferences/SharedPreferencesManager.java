package com.example.mymusic2.sharedpreferences;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.mymusic2.model.response.PlaylistResponse;
import com.example.mymusic2.model.response.SongResponse;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesManager {
    private static final String PREFERENCE_BASE_URL = "PREFERENCE_BASE_URL";
    private static SharedPreferencesManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new SharedPreferencesManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static SharedPreferencesManager getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesManager();
        }
        return instance;
    }

    public static void setBaseUrl(String baseUrl) {
        SharedPreferencesManager.getInstance().mySharedPreferences.put(PREFERENCE_BASE_URL, baseUrl);
    }

    public static String getBaseURL() {
        return SharedPreferencesManager.getInstance().mySharedPreferences.get(PREFERENCE_BASE_URL);
    }

    public static void removeSharedPreferences(){
        SharedPreferencesManager.getInstance().mySharedPreferences.destroy();
    }
    public static void setAccessToken(String accessToken) {
        SharedPreferencesManager.getInstance().mySharedPreferences.put("AccessToken", accessToken);
    }

    public static String getAccessToken() {
        return SharedPreferencesManager.getInstance().mySharedPreferences.get("AccessToken");
    }

    public static void setAlbums(List<PlaylistResponse> albums){
        Gson gson = new Gson();
        String json = gson.toJson(albums);
        SharedPreferencesManager.getInstance().mySharedPreferences.put("albums", json);
    }

    public static List<PlaylistResponse> getAlbums(){
        Gson gson = new Gson();
        Type type = new TypeToken<List<PlaylistResponse>>() {}.getType();
        String json = SharedPreferencesManager.getInstance().mySharedPreferences.get("albums");
        return gson.fromJson(json, type);
    }

    public static void setFavoriteId(Long favoriteId){
        Gson gson = new Gson();
        String json = gson.toJson(favoriteId);
        SharedPreferencesManager.getInstance().mySharedPreferences.put("favoriteId", json);
    }
    public static Long getFavoriteId(){
        Gson gson = new Gson();
        Type type = new TypeToken<Long>() {}.getType();
        String json = SharedPreferencesManager.getInstance().mySharedPreferences.get("favoriteId");
        return gson.fromJson(json, type);
    }

    public static void setFavoriteSongs(List<SongResponse>songs){
        Gson gson = new Gson();
        String json = gson.toJson(songs);
        SharedPreferencesManager.getInstance().mySharedPreferences.put("favoriteSongs", json);
    }
    public static List<SongResponse> getFavoriteSongs(){
        Gson gson = new Gson();
        Type type = new TypeToken<List<SongResponse>>() {}.getType();
        String json = SharedPreferencesManager.getInstance().mySharedPreferences.get("favoriteSongs");
        return gson.fromJson(json, type);
    }
}
