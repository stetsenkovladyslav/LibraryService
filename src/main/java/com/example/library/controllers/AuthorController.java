package com.example.library.controllers;

import com.example.library.dto.AuthorDto;
import com.example.library.entities.Author;
import com.example.library.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<Author> createAuthor(@RequestBody AuthorDto authorDto) {
        Author newAuthor = authorService.addAuthor(authorDto);
        return ResponseEntity.ok(newAuthor);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"page", "limit"}
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<List<Author>> getAllAuthors(@RequestParam int page, @RequestParam int limit) {
        Page<Author> allAuthors = authorService.getAllAuthors(page, limit);
        if (allAuthors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allAuthors.toList());
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<Author> getAuthor(@PathVariable Long id) {
        Author authorById = authorService.getAuthorById(id);
        return ResponseEntity.ok(authorById);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        authorService.updateAuthorById(id, authorDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok().build();
    }
}