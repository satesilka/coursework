package com.satesilka.rest.storage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVReader;
import com.satesilka.model.Album;

public class AlbumStorage {
    private final Map<Integer, Album> data = new HashMap<>();
    private static AlbumStorage instance;

    private AlbumStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("album-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<Album> albums = CSVReader.read(file, Album::new);
            for (Album album : albums) {
                maxId = Integer.max(maxId, album.getId());
                data.put(album.getId(), album);
            }
            Album.getIdGenerator().set(maxId + 1);
        }
    }

    public static AlbumStorage getInstance() throws IOException, CSVParseException {
        if (instance == null) {
            instance = new AlbumStorage();
        }
        return instance;
    }

    public List<Album> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<Album> findById(int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(Album album) {
        data.put(album.getId(), album);
        // TODO: save to csv
    }

    public void delete(Album album) {
        data.remove(album.getId());
    }
}
