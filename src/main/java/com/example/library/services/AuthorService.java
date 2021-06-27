package com.example.library.services;

import com.example.library.dto.AuthorDto;
import com.example.library.entities.Author;
import org.springframework.data.domain.Page;

public interface AuthorService {

    Author addAuthor(AuthorDto authorDto);

    void deleteAuthorById(long id);

    void updateAuthorById(long id, AuthorDto authorDto);

    Author getAuthorById(long id);

    Page<Author> getAllAuthors(int page, int limit);

}