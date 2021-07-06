package com.example.library.repository.genre;

import com.example.library.model.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> getGenreByIdIn(List<Long> ids);

}