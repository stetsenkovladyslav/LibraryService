package com.example.library.service.author;

import com.example.library.dto.author.AuthorDto;
import com.example.library.mapper.author.AuthorMapper;
import com.example.library.model.author.Author;
import com.example.library.repository.author.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;


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
        authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author with id:{" + id + "} does not exist"));
        Author updated = authorMapper.dtoToAuthor(authorDto);
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