package com.satesilka.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.Author;
import com.satesilka.rest.storage.AuthorStorage;

@Service
@ApplicationScope
public class AuthorService {
    @Autowired
    private AuthorStorage authorStorage;
    private LocalDateTime lastSaveDate = LocalDateTime.now();

    private void checkMidnight() throws IOException {
        authorStorage.saveToCSV();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
        if (!dtf.format(lastSaveDate).equals(dtf.format(LocalDateTime.now()))) {
            authorStorage.clear();
            lastSaveDate = LocalDateTime.now();
        }
    }

    public List<Author> getAuthors() throws IOException {
        checkMidnight();
        return authorStorage.findAll();
    }

    public Optional<Author> getAuthor(final int id) throws IOException {
        checkMidnight();
        return authorStorage.findById(id);
    }

    public Author createAuthor(final Author author) throws IOException {
        authorStorage.save(author);
        checkMidnight();
        return author;
    }

    public Optional<Author> updateAuthor(final int id, final Author updatedAuthor) throws IOException {
        Optional<Author> author = authorStorage.findById(id);

        if (author.isPresent()) {
            updatedAuthor.setId(id);
            authorStorage.update(updatedAuthor);

            checkMidnight();
            return Optional.of(updatedAuthor);
        }

        return Optional.empty();
    }

    public Optional<Author> deleteAuthor(final int id) throws IOException {
        Optional<Author> author = authorStorage.findById(id);

        if (author.isPresent()) {
            authorStorage.delete(author.get());
            checkMidnight();
            return author;
        }

        return Optional.empty();
    }
}
