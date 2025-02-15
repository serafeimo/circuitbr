package com.example.circuitbr.service;

import com.example.circuitbr.adapter.AlbumsAdapter;
import com.example.circuitbr.dto.AlbumsResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AlbumService {
    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);
    private static final String CIRCUIT_BREAKER_NAME = "albumsService";

    @Autowired
    private AlbumsAdapter albumsAdapter;

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "getFallbackAlbums")
    public AlbumsResponse getAlbums() {
        logger.info("Attempting to fetch albums");
        return albumsAdapter.getAlbums();
    }

    private AlbumsResponse getFallbackAlbums(Throwable t) {
        logger.warn("Circuit breaker fallback: returning empty albums list. Error: {}", t.getMessage());
        AlbumsResponse fallbackResponse = new AlbumsResponse();
        fallbackResponse.setAlbums(new ArrayList<>());
        return fallbackResponse;
    }
}
