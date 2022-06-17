package com.satesilka.rest.service;

import com.satesilka.io.CSVParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.satesilka.model.Author;
import com.satesilka.rest.storage.AuthorStorage;

@Service
@ApplicationScope
public class AuthorService {
    private final AuthorStorage authorRepository;

    public AuthorService() throws IOException, CSVParseException {
        authorRepository = AuthorStorage.getInstance();
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthor(final int id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(final Author Author){
        authorRepository.save(Author);
        return Author;
    }

    public Optional<Author> updateAuthor(final int id, final Author updatedAuthor) {
        Optional<Author> Author = authorRepository.findById(id);

        if (Author.isPresent()) {
            updatedAuthor.setId(id);
            authorRepository.save(updatedAuthor);

            return Optional.of(updatedAuthor);
        }

        return Optional.empty();
    }

    public Optional<Author> deleteAuthor(final int id) {
        Optional<Author> Author = authorRepository.findById(id);

        if (Author.isPresent()) {
            authorRepository.delete(Author.get());
            return Author;
        }

        return Optional.empty();
    }
}
