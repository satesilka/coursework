package com.satesilka.rest.controller;

import com.satesilka.model.Song;
import com.satesilka.rest.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/song")
public class SongController {
    @Autowired
    private SongService songService;

    @GetMapping(produces = "application/json")
    public List<Song> getSongs() {
        return songService.getSongs();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Song> getSongById(@PathVariable("id") final int id) {
        Optional<Song> shoes = songService.getSong(id);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Song> createSong(@RequestBody final Song newSong) {
        return new ResponseEntity<>(songService.createSong(newSong), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Song> updateSong(@PathVariable("id") final int id, @RequestBody final Song updatedSong) {
        Optional<Song> shoes = songService.updateSong(id, updatedSong);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable("id") final int id) {
        Optional<Song> song = songService.deleteSong(id);
        return song.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
