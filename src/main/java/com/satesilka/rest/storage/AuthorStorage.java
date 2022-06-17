package com.satesilka.rest.storage;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.satesilka.io.CSVParseException;
import com.satesilka.io.CSVReader;
import com.satesilka.model.Author;

public class AuthorStorage {
    private final Map<Integer, Author> data = new HashMap<>();
    private static AuthorStorage instance;

    private AuthorStorage() throws IOException, CSVParseException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd");
        File file = new File("author-" + dtf.format(LocalDateTime.now()) + ".csv");
        if (file.exists()) {
            int maxId = 0;
            List<Author> authors = CSVReader.read(file, Author::new);
            for (Author author : authors) {
                maxId = Integer.max(maxId, author.getId());
                data.put(author.getId(), author);
            }
            Author.getIdGenerator().set(maxId + 1);
        }
    }

    public static AuthorStorage getInstance() throws IOException, CSVParseException {
        if (instance == null) {
            instance = new AuthorStorage();
        }
        return instance;
    }

    public List<Author> findAll() {
        return new ArrayList<>(data.values());
    }

    public Optional<Author> findById(int id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    public void save(Author author) {
        data.put(author.getId(), author);
    }

    public void delete(Author author) {
        data.remove(author.getId());
    }
}
