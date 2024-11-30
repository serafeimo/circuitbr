package com.example.circuitbr.dto;

import java.util.List;

public class AlbumsResponse {
    private List<Album> albums;

    public AlbumsResponse() {
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}

class Album {
    private int id;
    private String title;

    public Album() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
