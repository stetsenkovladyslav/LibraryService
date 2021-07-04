package com.example.library.services.impl;

import com.example.library.dto.BookDto;
import com.example.library.entities.Book;
import com.example.library.mappers.BookMapper;
import com.example.library.repositories.BookRepository;
import com.example.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book addBook(BookDto bookDto) {
        Book newBook = BookMapper.INSTANCE.dtoToBook(bookDto);
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
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}