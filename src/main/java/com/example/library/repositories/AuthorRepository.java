package com.example.library.repositories;

import com.example.library.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> getAuthorByIdIn(List<Long> ids);

}