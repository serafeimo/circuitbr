package com.example.circuitbr.adapter;

import com.example.circuitbr.dto.AlbumsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AlbumsAdapter
{
    @Autowired
    private RestTemplate restTemplate;

    public AlbumsResponse getAlbums() {
        return restTemplate.getForObject("http://localhost:8443/albums", AlbumsResponse.class);
    }
}
