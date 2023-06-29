package com.example.mymusic2.model;

import java.io.Serializable;

public class BaiHat implements Serializable {
    private int musicId;
    private int musicrsc;
    private String name;
    private String description;

    public BaiHat() {
    }

    public BaiHat(int musicId, int musicrsc, String name, String description) {
        this.musicId = musicId;
        this.musicrsc = musicrsc;
        this.name = name;
        this.description = description;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public int getMusicrsc() {
        return musicrsc;
    }

    public void setMusicrsc(int musicrsc) {
        this.musicrsc = musicrsc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
