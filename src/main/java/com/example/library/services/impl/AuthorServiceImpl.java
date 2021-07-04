package com.example.library.services.impl;

import com.example.library.dto.AuthorDto;
import com.example.library.entities.Author;
import com.example.library.mappers.AuthorMapper;
import com.example.library.repositories.AuthorRepository;
import com.example.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author addAuthor(AuthorDto authorDto) {
        return authorRepository.save(new Author(authorDto.getFirstName(), authorDto.getLastName()));
    }

    @Override
    public void deleteAuthorById(long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public void updateAuthorById(long id, AuthorDto authorDto) {
        authorRepository.findById(id).orElseThrow();
        Author updated = AuthorMapper.INSTANCE.dtoToAuthor(authorDto);
        authorRepository.save(updated);
    }

    @Override
    public Author getAuthorById(long id) {
        return authorRepository.getById(id);
    }

    @Override
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }
}
