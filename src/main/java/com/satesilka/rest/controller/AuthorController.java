package com.satesilka.rest.controller;

import com.satesilka.model.Author;
import com.satesilka.rest.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/author")
public final class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping(produces = "application/json")
    public List<Author> getAuthors() throws IOException {
        return authorService.getAuthors();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") final int id) throws IOException {
        Optional<Author> shoes = authorService.getAuthor(id);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Author> createAuthor(@RequestBody final Author newAuthor) throws IOException {
        return new ResponseEntity<>(authorService.createAuthor(newAuthor), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") final int id, @RequestBody final Author updatedAuthor) throws IOException {
        Optional<Author> shoes = authorService.updateAuthor(id, updatedAuthor);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") final int id) throws IOException {
        Optional<Author> author = authorService.deleteAuthor(id);
        return author.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
