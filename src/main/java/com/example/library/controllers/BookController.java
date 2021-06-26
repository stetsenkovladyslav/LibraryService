package com.example.library.controllers;

import com.example.library.dto.BookDto;
import com.example.library.entities.Book;
import com.example.library.mappers.BookMapper;
import com.example.library.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class BookController {

    private final BookService bookService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        Book newBook = bookService.addBook(bookDto);
        return ResponseEntity.ok(BookMapper.INSTANCE.toDto(newBook));
    }

    @GetMapping(
            params = {"page", "limit"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<Page<BookDto>> getAllBooks(@RequestParam int page, @RequestParam int limit) {
        Page<Book> allBooks = bookService.getAllBooks(page, limit);
        if (allBooks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BookMapper bookMapper = BookMapper.INSTANCE;
        return ResponseEntity.ok(allBooks.map(bookMapper::toDto));
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<Book> getBook(@PathVariable Long id) {
        Book bookById = bookService.getBookById(id);
        return ResponseEntity.ok(BookMapper.INSTANCE.toDto(bookById));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        bookService.updateBookById(id, bookDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }
}