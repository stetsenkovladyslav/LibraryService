package com.example.library.repositories;

import com.example.library.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> getGenreByIdIn(List<Long> ids);

    @Query("UPDATE Genre SET genreName = :name WHERE id = :id")
    void updateGenreById(long id, String name);
}