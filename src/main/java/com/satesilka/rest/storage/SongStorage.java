package com.satesilka.rest.storage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVReader;
import com.satesilka.model.Song;

public class SongStorage {
    private final Map<Integer, Song> data = new HashMap<>();
    private static SongStorage instance;

    private SongStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("song-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<Song> songs = CSVReader.read(file, Song::new);
            for (Song song : songs) {
                maxId = Integer.max(maxId, song.getId());
                data.put(song.getId(), song);
            }
            Song.getIdGenerator().set(maxId + 1);
        }
    }

    public static SongStorage getInstance() throws IOException, CSVParseException {
        if (instance == null) {
            instance = new SongStorage();
        }
        return instance;
    }

    public List<Song> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<Song> findById(int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(Song song) {
        data.put(song.getId(), song);
    }

    public void delete(Song song) {
        data.remove(song.getId());
    }
}
