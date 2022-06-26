package com.satesilka.rest.controller;

import com.satesilka.model.MusicalLabel;
import com.satesilka.rest.service.MusicalLabelService;
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
@RequestMapping(path = "/musical_label")
public final class MusicalLabelController {
    @Autowired
    private MusicalLabelService lyricsService;

    @GetMapping(produces = "application/json")
    public List<MusicalLabel> getMusicalLabels() throws IOException {
        return lyricsService.getMusicalLabels();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<MusicalLabel> getMusicalLabelById(@PathVariable("id") final int id) throws IOException {
        Optional<MusicalLabel> shoes = lyricsService.getMusicalLabel(id);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<MusicalLabel> createMusicalLabel(@RequestBody final MusicalLabel newMusicalLabel) throws IOException {
        return new ResponseEntity<>(lyricsService.createMusicalLabel(newMusicalLabel), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<MusicalLabel> updateMusicalLabel(@PathVariable("id") final int id, @RequestBody final MusicalLabel updatedMusicalLabel) throws IOException {
        Optional<MusicalLabel> shoes = lyricsService.updateMusicalLabel(id, updatedMusicalLabel);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMusicalLabel(@PathVariable("id") final int id) throws IOException {
        Optional<MusicalLabel> lyrics = lyricsService.deleteMusicalLabel(id);
        return lyrics.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
