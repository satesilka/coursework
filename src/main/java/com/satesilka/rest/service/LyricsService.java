package com.satesilka.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.Lyrics;
import com.satesilka.rest.storage.LyricsStorage;

@Service
@ApplicationScope
public class LyricsService {
    @Autowired
    private LyricsStorage lyricsStorage;
    private LocalDateTime lastSaveDate = LocalDateTime.now();

    private void checkMidnight() throws IOException {
        lyricsStorage.saveToCSV();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
        if (!dtf.format(lastSaveDate).equals(dtf.format(LocalDateTime.now()))) {
            lyricsStorage.clear();
            lastSaveDate = LocalDateTime.now();
        }
    }

    public List<Lyrics> getLyricss() throws IOException {
        checkMidnight();
        return lyricsStorage.findAll();
    }

    public Optional<Lyrics> getLyrics(final int id) throws IOException {
        checkMidnight();
        return lyricsStorage.findById(id);
    }

    public Lyrics createLyrics(final Lyrics lyrics) throws IOException {
        lyricsStorage.save(lyrics);
        checkMidnight();
        return lyrics;
    }

    public Optional<Lyrics> updateLyrics(final int id, final Lyrics updatedLyrics) throws IOException {
        Optional<Lyrics> lyrics = lyricsStorage.findById(id);

        if (lyrics.isPresent()) {
            updatedLyrics.setId(id);
            lyricsStorage.update(updatedLyrics);

            checkMidnight();
            return Optional.of(updatedLyrics);
        }

        return Optional.empty();
    }

    public Optional<Lyrics> deleteLyrics(final int id) throws IOException {
        Optional<Lyrics> lyrics = lyricsStorage.findById(id);

        if (lyrics.isPresent()) {
            lyricsStorage.delete(lyrics.get());
            checkMidnight();
            return lyrics;
        }

        return Optional.empty();
    }
}
