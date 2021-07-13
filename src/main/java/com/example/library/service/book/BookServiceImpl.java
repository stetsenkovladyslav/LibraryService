package com.example.library.service.book;

import com.example.library.dto.book.BookCriteria;
import com.example.library.dto.book.BookDto;
import com.example.library.mapper.book.BookMapper;
import com.example.library.model.author.Author;
import com.example.library.model.book.Book;
import com.example.library.model.genre.Genre;
import com.example.library.repository.author.AuthorRepository;
import com.example.library.repository.book.BookRepository;
import com.example.library.repository.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;


    @Override
    public Book addBook(BookDto bookDto) {
        Book newBook = BookMapper.INSTANCE.dtoToBook(bookDto);
        List<Genre> genreList = genreRepository.getGenreByIdIn(bookDto.getGenresId());
        List<Author> authorList = authorRepository.getAuthorByIdIn(bookDto.getAuthorsId());
        newBook.setAuthors(authorList);
        newBook.setGenres(genreList);
        return bookRepository.save(newBook);
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
    public Page<Book> getAllBooks(Pageable pageable, BookCriteria bookCriteria) {
        return bookRepository.findAll(bookCriteria.buildCriteria(), pageable);
    }
}