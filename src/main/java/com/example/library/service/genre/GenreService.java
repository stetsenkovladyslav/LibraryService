package com.example.library.service.genre;

import com.example.library.dto.genre.GenreDto;
import com.example.library.model.genre.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreService {

    Genre addGenre(GenreDto genreDto);

    void deleteGenreById(long id);

    void updateGenre(long id, GenreDto genreDto);

    Genre getGenreById(long id);

    Page<Genre> getAllGenres(Pageable pageable);

}