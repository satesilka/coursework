package com.satesilka.rest.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVReader;
import com.satesilka.io.CSVWriter;
import com.satesilka.model.Song;

@Component
public final class SongStorage {
    private final Map<Integer, Song> data = new HashMap<>();

    public SongStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("song-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<Song> songs = CSVReader.read(file, Song::new);
            for (Song song : songs) {
                maxId = Integer.max(maxId, song.getId());
                data.put(song.getId(), song);
            }
            Song.setIdGenerator(maxId + 1);
        }
    }

    public void saveToCSV() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        CSVWriter.write("song-" + dtf.format(LocalDateTime.now()) + ".csv", new ArrayList<>(data.values()));
    }

    public void clear() {
        data.clear();
    }

    public List<Song> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<Song> findById(final int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(final Song song) {
        song.regenerateId();
        data.put(song.getId(), song);
    }

    public void update(final Song song) {
        data.put(song.getId(), song);
    }

    public void delete(final Song song) {
        data.remove(song.getId());
    }
}
