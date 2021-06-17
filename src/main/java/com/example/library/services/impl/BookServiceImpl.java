package com.example.library.services.impl;


import com.example.library.dao.AuthorDao;
import com.example.library.dao.BookDao;
import com.example.library.dao.GenreDao;
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
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Book addBook(BookDto bookDto) {
        return bookDao.save(buildBook(bookDto));
    }

    @Override
    public void deleteBookById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public void updateBookById(long id, BookDto bookDto) {
        Book book = buildBook(bookDto);
        bookDao.updateById(id, book.getBookName(), book.getBookDescription(), book.getAuthors(), book.getGenres());
    }

    @Override
    public Book getBookById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public Page<Book> getAllBooks(int page, int limit) {
        return bookDao.findAll(PageRequest.of(page, limit));
    }

    private Book buildBook(BookDto bookDto) {
        List<Author> authors = authorDao.getAuthorByIdIn(bookDto.getAuthorsId());
        List<Genre> genres = genreDao.getGenreByIdIn(bookDto.getGenresId());
        Book newBook = new Book();
        newBook.setBookName(bookDto.getName());
        newBook.setBookDescription(bookDto.getDescription());
        newBook.setAuthors(authors);
        newBook.setGenres(genres);
        return newBook;
    }
}
