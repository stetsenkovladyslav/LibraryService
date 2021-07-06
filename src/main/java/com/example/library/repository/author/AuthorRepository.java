package com.example.library.repository.author;

import com.example.library.model.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> getAuthorByIdIn(List<Long> ids);

}