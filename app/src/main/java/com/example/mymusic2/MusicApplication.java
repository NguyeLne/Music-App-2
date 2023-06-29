package com.example.mymusic2;

import android.app.Application;

import com.example.mymusic2.sharedpreferences.MySharedPreferences;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;

public class MusicApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(getApplicationContext());
        SharedPreferencesManager.setBaseUrl("http://10.20.50.184:8080");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SharedPreferencesManager.removeSharedPreferences();
    }
}