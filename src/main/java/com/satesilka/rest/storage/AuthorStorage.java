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
import com.satesilka.model.Author;

@Component
public final class AuthorStorage {
    private final Map<Integer, Author> data = new HashMap<>();

    public AuthorStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("author-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<Author> authors = CSVReader.read(file, Author::new);
            for (Author author : authors) {
                maxId = Integer.max(maxId, author.getId());
                data.put(author.getId(), author);
            }
            Author.setIdGenerator(maxId + 1);
        }
    }

    public void saveToCSV() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        CSVWriter.write("author-" + dtf.format(LocalDateTime.now()) + ".csv", new ArrayList<>(data.values()));
    }

    public void clear() {
        data.clear();
    }

    public List<Author> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<Author> findById(final int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(final Author author) {
        author.regenerateId();
        data.put(author.getId(), author);
    }

    public void update(final Author author) {
        data.put(author.getId(), author);
    }

    public void delete(final Author author) {
        data.remove(author.getId());
    }
}
