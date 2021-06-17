package com.example.library.services.impl;

import com.example.library.dao.AuthorDao;
import com.example.library.dto.AuthorDto;
import com.example.library.entities.Author;
import com.example.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Author addAuthor(AuthorDto authorDto) {
        return authorDao.save(new Author(authorDto.getFirstName(), authorDto.getLastName()));
    }

    @Override
    public void deleteAuthorById(long id) {
        authorDao.deleteById(id);
    }

    @Override
    public void updateAuthorById(long id, AuthorDto authorDto) {
        authorDao.updateAuthorById(id, authorDto.getFirstName(), authorDto.getLastName());
    }

    @Override
    public Author getAuthorById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public Page<Author> getAllAuthors(int page, int limit) {
        return authorDao.findAll(PageRequest.of(page, limit));
    }
}
