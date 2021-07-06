package com.example.library.service.author;

import com.example.library.dto.author.AuthorDto;
import com.example.library.model.author.Author;
import com.example.library.mapper.author.AuthorMapper;
import com.example.library.repository.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
