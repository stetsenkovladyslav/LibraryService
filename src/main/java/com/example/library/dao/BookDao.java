package com.example.library.dao;

import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

    @Query("UPDATE Book SET bookName = :name, bookDescription = :description, authors = :authors, genres = :genres WHERE id = :id")
    void updateById(long id, String name, String description, List<Author> authors, List<Genre> genres);

}