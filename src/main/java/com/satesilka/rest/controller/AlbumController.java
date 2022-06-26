package com.satesilka.rest.controller;

import com.satesilka.model.Album;
import com.satesilka.rest.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/album")
public final class AlbumController {
    @Autowired
    private AlbumService albumService;

    @GetMapping(produces = "application/json")
    public List<Album> getAlbums() throws IOException {
        return albumService.getAlbums();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Album> getAlbumById(@PathVariable("id") final int id) throws IOException {
        Optional<Album> shoes = albumService.getAlbum(id);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Album> createAlbum(@RequestBody final Album newAlbum) throws IOException {
        return new ResponseEntity<>(albumService.createAlbum(newAlbum), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Album> updateAlbum(@PathVariable("id") final int id, @RequestBody final Album updatedAlbum) throws IOException {
        Optional<Album> shoes = albumService.updateAlbum(id, updatedAlbum);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable("id") final int id) throws IOException {
        Optional<Album> album = albumService.deleteAlbum(id);
        return album.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
