package com.example.library.controllers;

import com.example.library.dto.GenreDto;
import com.example.library.entities.Genre;
import com.example.library.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class GenreController {

    private final GenreService genreService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<Genre> createGenre(@RequestBody GenreDto genreDto) {
        Genre newGenre = genreService.addGenre(genreDto);
        return ResponseEntity.ok(newGenre);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            params = {"page", "limit"}
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<List<Genre>> getAllGenres(@RequestParam int page, @RequestParam int limit) {
        Page<Genre> allGenres = genreService.getAllGenres(page, limit);
        if (allGenres.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allGenres.toList());
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<Genre> getGenre(@PathVariable Long id) {
        Genre genreById = genreService.getGenreById(id);
        return ResponseEntity.ok(genreById);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> updateGenre(@PathVariable Long id, @RequestBody GenreDto genreDto) {
        genreService.updateGenre(id, genreDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenreById(id);
        return ResponseEntity.ok().build();
    }
}