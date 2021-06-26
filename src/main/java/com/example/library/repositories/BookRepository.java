package com.example.library.repositories;

import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}