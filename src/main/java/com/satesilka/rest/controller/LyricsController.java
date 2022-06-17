package com.satesilka.rest.controller;

import com.satesilka.model.Lyrics;
import com.satesilka.rest.service.LyricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/lyrics")
public class LyricsController {
    @Autowired
    private LyricsService lyricsService;

    @GetMapping(produces = "application/json")
    public List<Lyrics> getLyricss() {
        return lyricsService.getLyricss();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Lyrics> getLyricsById(@PathVariable("id") final int id) {
        Optional<Lyrics> shoes = lyricsService.getLyrics(id);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Lyrics> createLyrics(@RequestBody final Lyrics newLyrics) {
        return new ResponseEntity<>(lyricsService.createLyrics(newLyrics), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Lyrics> updateLyrics(@PathVariable("id") final int id, @RequestBody final Lyrics updatedLyrics) {
        Optional<Lyrics> shoes = lyricsService.updateLyrics(id, updatedLyrics);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteLyrics(@PathVariable("id") final int id) {
        Optional<Lyrics> lyrics = lyricsService.deleteLyrics(id);
        return lyrics.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
