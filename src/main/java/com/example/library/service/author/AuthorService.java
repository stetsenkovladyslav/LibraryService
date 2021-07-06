package com.example.library.service.author;

import com.example.library.dto.author.AuthorDto;
import com.example.library.model.author.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {


    Author addAuthor(AuthorDto authorDto);

    void deleteAuthorById(long id);

    void updateAuthorById(long id, AuthorDto authorDto);

    Author getAuthorById(long id);

    Page<Author> getAllAuthors(Pageable pageable);

}