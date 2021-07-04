package com.example.library.services;

import com.example.library.dto.GenreDto;
import com.example.library.entities.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {

    Genre addGenre(GenreDto genreDto);

    void deleteGenreById(long id);

    void updateGenre(long id, GenreDto genreDto);

    Genre getGenreById(long id);

    Page<Genre> getAllGenres(Pageable pageable);

}