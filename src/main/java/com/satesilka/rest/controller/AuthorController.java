package com.satesilka.rest.controller;

import com.satesilka.model.Author;
import com.satesilka.rest.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping(produces = "application/json")
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") final int id) {
        Optional<Author> shoes = authorService.getAuthor(id);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Author> createAuthor(@RequestBody final Author newAuthor) {
        return new ResponseEntity<>(authorService.createAuthor(newAuthor), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") final int id, @RequestBody final Author updatedAuthor) {
        Optional<Author> shoes = authorService.updateAuthor(id, updatedAuthor);
        return shoes.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") final int id) {
        Optional<Author> author = authorService.deleteAuthor(id);
        return author.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                 .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
