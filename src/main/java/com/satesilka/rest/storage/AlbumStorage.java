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
import com.satesilka.model.Album;

@Component
public final class AlbumStorage {
    private final Map<Integer, Album> data = new HashMap<>();

    public AlbumStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("album-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<Album> albums = CSVReader.read(file, Album::new);
            for (Album album : albums) {
                maxId = Integer.max(maxId, album.getId());
                data.put(album.getId(), album);
            }
            Album.setIdGenerator(maxId + 1);
        }
    }

    public void saveToCSV() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        CSVWriter.write("album-" + dtf.format(LocalDateTime.now()) + ".csv", new ArrayList<>(data.values()));
    }

    public void clear() {
        data.clear();
    }

    public List<Album> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<Album> findById(final int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(final Album album) {
        album.regenerateId();
        data.put(album.getId(), album);
    }

    public void update(final Album album) {
        data.put(album.getId(), album);
    }

    public void delete(final Album album) {
        data.remove(album.getId());
    }
}
