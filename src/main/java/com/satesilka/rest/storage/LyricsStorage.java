package com.satesilka.rest.storage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVReader;
import com.satesilka.model.Lyrics;

public class LyricsStorage {
    private final Map<Integer, Lyrics> data = new HashMap<>();
    private static LyricsStorage instance;

    private LyricsStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("lyrics-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<Lyrics> lyrics = CSVReader.read(file, Lyrics::new);
            for (Lyrics lyric : lyrics) {
                maxId = Integer.max(maxId, lyric.getId());
                data.put(lyric.getId(), lyric);
            }
            Lyrics.getIdGenerator().set(maxId + 1);
        }
    }

    public static LyricsStorage getInstance() throws IOException, CSVParseException {
        if (instance == null) {
            instance = new LyricsStorage();
        }
        return instance;
    }

    public List<Lyrics> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<Lyrics> findById(int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(Lyrics lyrics) {
        data.put(lyrics.getId(), lyrics);
    }

    public void delete(Lyrics lyrics) {
        data.remove(lyrics.getId());
    }
}
