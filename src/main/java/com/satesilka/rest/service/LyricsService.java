package com.satesilka.rest.service;

import com.satesilka.io.CSVParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.Lyrics;
import com.satesilka.rest.storage.LyricsStorage;

@Service
@ApplicationScope
public class LyricsService {
    private final LyricsStorage lyricsRepository;

    public LyricsService() throws IOException, CSVParseException {
        lyricsRepository = LyricsStorage.getInstance();
    }

    public List<Lyrics> getLyricss() {
        return lyricsRepository.findAll();
    }

    public Optional<Lyrics> getLyrics(final int id) {
        return lyricsRepository.findById(id);
    }

    public Lyrics createLyrics(final Lyrics lyrics){
        lyricsRepository.save(lyrics);
        return lyrics;
    }

    public Optional<Lyrics> updateLyrics(final int id, final Lyrics updatedLyrics) {
        Optional<Lyrics> lyrics = lyricsRepository.findById(id);

        if (lyrics.isPresent()) {
            updatedLyrics.setId(id);
            lyricsRepository.save(updatedLyrics);

            return Optional.of(updatedLyrics);
        }

        return Optional.empty();
    }

    public Optional<Lyrics> deleteLyrics(final int id) {
        Optional<Lyrics> lyrics = lyricsRepository.findById(id);

        if (lyrics.isPresent()) {
            lyricsRepository.delete(lyrics.get());
            return lyrics;
        }

        return Optional.empty();
    }
}
