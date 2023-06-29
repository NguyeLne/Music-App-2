package com.example.mymusic2.model.response;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class PlaylistResponse implements Serializable {
    private Long id;
    private String name;

    private Long userId;

    private String avatarUrl;

    public PlaylistResponse() {
    }

    public PlaylistResponse(Long id, String name, Long userId, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "PlaylistResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaylistResponse that = (PlaylistResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getAvatarUrl(), that.getAvatarUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getUserId(), getAvatarUrl());
    }
}
