package com.satesilka.rest.service;

import com.satesilka.io.CSVParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.Song;
import com.satesilka.rest.storage.SongStorage;

@Service
@ApplicationScope
public class SongService {
    private final SongStorage songRepository;

    public SongService() throws IOException, CSVParseException {
        songRepository = SongStorage.getInstance();
    }

    public List<Song> getSongs() {
        return songRepository.findAll();
    }

    public Optional<Song> getSong(final int id) {
        return songRepository.findById(id);
    }

    public Song createSong(final Song song){
        songRepository.save(song);
        return song;
    }

    public Optional<Song> updateSong(final int id, final Song updatedSong) {
        Optional<Song> song = songRepository.findById(id);

        if (song.isPresent()) {
            updatedSong.setId(id);
            songRepository.save(updatedSong);

            return Optional.of(updatedSong);
        }

        return Optional.empty();
    }

    public Optional<Song> deleteSong(final int id) {
        Optional<Song> song = songRepository.findById(id);

        if (song.isPresent()) {
            songRepository.delete(song.get());
            return song;
        }

        return Optional.empty();
    }
}
