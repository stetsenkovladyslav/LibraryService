package com.example.library.services;

import com.example.library.dto.BookDto;
import com.example.library.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Book addBook(BookDto bookDto);

    void deleteBookById(long id);

    void updateBookById(long id, BookDto bookDto);

    Book getBookById(long id);

    Page<Book> getAllBooks(Pageable pageable);
}
