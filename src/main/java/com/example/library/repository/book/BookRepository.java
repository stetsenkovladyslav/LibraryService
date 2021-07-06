package com.example.library.repository.book;

import com.example.library.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {

}