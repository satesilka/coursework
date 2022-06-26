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
import com.satesilka.model.Lyrics;

@Component
public final class LyricsStorage {
    private final Map<Integer, Lyrics> data = new HashMap<>();

    public LyricsStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("lyrics-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<Lyrics> lyrics = CSVReader.read(file, Lyrics::new);
            for (Lyrics lyric : lyrics) {
                maxId = Integer.max(maxId, lyric.getId());
                data.put(lyric.getId(), lyric);
            }
            Lyrics.setIdGenerator(maxId + 1);
        }
    }

    public void saveToCSV() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        CSVWriter.write("lyrics-" + dtf.format(LocalDateTime.now()) + ".csv", new ArrayList<>(data.values()));
    }

    public void clear() {
        data.clear();
    }

    public List<Lyrics> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<Lyrics> findById(final int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(final Lyrics lyrics) {
        lyrics.regenerateId();
        data.put(lyrics.getId(), lyrics);
    }

    public void update(final Lyrics lyrics) {
        data.put(lyrics.getId(), lyrics);
    }

    public void delete(final Lyrics lyrics) {
        data.remove(lyrics.getId());
    }
}
