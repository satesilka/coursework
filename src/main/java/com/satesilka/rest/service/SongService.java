package com.satesilka.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.Song;
import com.satesilka.rest.storage.SongStorage;

@Service
@ApplicationScope
public class SongService {
    @Autowired
    private SongStorage songStorage;
    private LocalDateTime lastSaveDate = LocalDateTime.now();

    private void checkMidnight() throws IOException {
        songStorage.saveToCSV();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
        if (!dtf.format(lastSaveDate).equals(dtf.format(LocalDateTime.now()))) {
            songStorage.clear();
            lastSaveDate = LocalDateTime.now();
        }
    }

    public List<Song> getSongs() throws IOException {
        checkMidnight();
        return songStorage.findAll();
    }

    public Optional<Song> getSong(final int id) throws IOException {
        checkMidnight();
        return songStorage.findById(id);
    }

    public Song createSong(final Song song) throws IOException {
        songStorage.save(song);
        checkMidnight();
        return song;
    }

    public Optional<Song> updateSong(final int id, final Song updatedSong) throws IOException {
        Optional<Song> song = songStorage.findById(id);

        if (song.isPresent()) {
            updatedSong.setId(id);
            songStorage.update(updatedSong);

            checkMidnight();
            return Optional.of(updatedSong);
        }

        return Optional.empty();
    }

    public Optional<Song> deleteSong(final int id) throws IOException {
        Optional<Song> song = songStorage.findById(id);

        if (song.isPresent()) {
            songStorage.delete(song.get());
            checkMidnight();
            return song;
        }

        return Optional.empty();
    }
}
