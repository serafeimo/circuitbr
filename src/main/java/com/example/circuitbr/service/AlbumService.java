package com.example.circuitbr.service;

import com.example.circuitbr.adapter.AlbumsAdapter;
import com.example.circuitbr.dto.AlbumsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    @Autowired
    private AlbumsAdapter albumsAdapter;

    public AlbumsResponse getAlbums() {
        return albumsAdapter.getAlbums();
    }
}
