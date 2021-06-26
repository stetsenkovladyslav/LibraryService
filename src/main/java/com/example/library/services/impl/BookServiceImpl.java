package com.example.library.services.impl;


import com.example.library.mappers.BookMapper;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.GenreRepository;
import com.example.library.dto.BookDto;
import com.example.library.entities.Author;
import com.example.library.entities.Book;
import com.example.library.entities.Genre;
import com.example.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Book addBook(BookDto bookDto) {
        return bookRepository.save(buildBook(bookDto));
    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBookById(long id, BookDto bookDto) {
        bookRepository.findById(id).orElseThrow();
        Book updated = BookMapper.INSTANCE.dtoToBook(bookDto);
        bookRepository.save(updated);
    }

    @Override
    public Book getBookById(long id) {
        return bookRepository.getById(id);
    }

    @Override
    public Page<Book> getAllBooks(int page, int limit) {
        return bookRepository.findAll(PageRequest.of(page, limit));
    }

    private Book buildBook(BookDto bookDto) {
        List<Author> authors = authorRepository.getAuthorByIdIn(bookDto.getAuthorsId());
        List<Genre> genres = genreRepository.getGenreByIdIn(bookDto.getGenresId());
        Book newBook = new Book();
        newBook.setBookName(bookDto.getName());
        newBook.setBookDescription(bookDto.getDescription());
        newBook.setAuthors(authors);
        newBook.setGenres(genres);
        return newBook;
    }
}
