package com.example.library.service.book;

import com.example.library.dto.book.BookCriteria;
import com.example.library.dto.book.BookDto;
import com.example.library.model.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Book addBook(BookDto bookDto);

    void deleteBookById(long id);

    void updateBookById(long id, BookDto bookDto);

    Book getBookById(long id);

    Page<Book> getAllBooks(Pageable pageable, BookCriteria bookCriteria);

}