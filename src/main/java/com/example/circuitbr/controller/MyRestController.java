package com.example.circuitbr.controller;

import com.example.circuitbr.dto.AlbumsResponse;
import com.example.circuitbr.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    @Autowired
    private AlbumService albumService;

    @GetMapping("/v1/albums")
    public AlbumsResponse hello() {
        return albumService.getAlbums();
    }
}
