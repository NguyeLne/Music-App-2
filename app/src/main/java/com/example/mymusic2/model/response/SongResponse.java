package com.example.mymusic2.model.response;

import java.io.Serializable;
import java.util.Objects;

public class SongResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String avatarLink;
    private String musicLink;
    private String category;

    public SongResponse() {
    }

    public SongResponse(Long id, String name, String description, String avatarLink, String musicLink, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avatarLink = avatarLink;
        this.musicLink = musicLink;
        this.category = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getMusicLink() {
        return musicLink;
    }

    public void setMusicLink(String musicLink) {
        this.musicLink = musicLink;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "SongResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", avatarLink='" + avatarLink + '\'' +
                ", musicLink='" + musicLink + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongResponse that = (SongResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getAvatarLink(), that.getAvatarLink()) && Objects.equals(getMusicLink(), that.getMusicLink()) && Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getAvatarLink(), getMusicLink(), getCategory());
    }
}
