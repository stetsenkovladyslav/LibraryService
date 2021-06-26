package com.example.library.repositories;

import com.example.library.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> getGenreByIdIn(List<Long> ids);

    @Query("UPDATE Genre SET genreName = :name WHERE id = :id")
    void updateGenreById(long id, String name);
}