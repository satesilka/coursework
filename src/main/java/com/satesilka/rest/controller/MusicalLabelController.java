package com.satesilka.rest.controller;

import com.satesilka.model.MusicalLabel;
import com.satesilka.rest.service.MusicalLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/musical_label")
public class MusicalLabelController {
    @Autowired
    private MusicalLabelService lyricsService;

    @GetMapping(produces = "application/json")
    public List<MusicalLabel> getMusicalLabels() {
        return lyricsService.getMusicalLabels();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<MusicalLabel> getMusicalLabelById(@PathVariable("id") final int id) {
        Optional<MusicalLabel> shoes = lyricsService.getMusicalLabel(id);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<MusicalLabel> createMusicalLabel(@RequestBody final MusicalLabel newMusicalLabel) {
        return new ResponseEntity<>(lyricsService.createMusicalLabel(newMusicalLabel), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MusicalLabel> updateMusicalLabel(@PathVariable("id") final int id, @RequestBody final MusicalLabel updatedMusicalLabel) {
        Optional<MusicalLabel> shoes = lyricsService.updateMusicalLabel(id, updatedMusicalLabel);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMusicalLabel(@PathVariable("id") final int id) {
        Optional<MusicalLabel> lyrics = lyricsService.deleteMusicalLabel(id);
        return lyrics.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
