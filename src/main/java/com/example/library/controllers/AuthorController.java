package com.example.library.controllers;

import com.example.library.dto.AuthorDto;
import com.example.library.entities.Author;
import com.example.library.mappers.AuthorMapper;
import com.example.library.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<AuthorDto> createAuthor(@RequestBody @Valid AuthorDto authorDto) {
        Author newAuthor = authorService.addAuthor(authorDto);
        return ResponseEntity.ok(AuthorMapper.INSTANCE.toDto(newAuthor));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"page", "limit"}
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<Page<AuthorDto>> getAllAuthors(@RequestParam Pageable pageable) {
        Page<Author> allAuthors = authorService.getAllAuthors(pageable);
        if (allAuthors.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AuthorMapper authorMapper = AuthorMapper.INSTANCE;
        return ResponseEntity.ok(allAuthors.map(authorMapper::toDto));
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<AuthorDto> getAuthor(
            @PathVariable @Valid @Positive(message = "Value must be higher than 0") Long id
    ) {
        Author authorById = authorService.getAuthorById(id);
        return ResponseEntity.ok(AuthorMapper.INSTANCE.toDto(authorById));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> updateAuthor(
            @PathVariable @Valid @Positive(message = "Value must be higher than 0") Long id,
            @RequestBody @Valid AuthorDto authorDto
    ) {
        authorService.updateAuthorById(id, authorDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> deleteAuthor(
            @PathVariable @Valid @Positive(message = "Value must be higher than 0") Long id
    ) {
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok().build();
    }
}