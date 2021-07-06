package com.example.library.controller.book;

import com.example.library.dto.book.BookDto;
import com.example.library.model.book.Book;
import com.example.library.mapper.book.BookMapper;
import com.example.library.service.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto) {
        Book newBook = bookService.addBook(bookDto);
        return ResponseEntity.ok(BookMapper.INSTANCE.toDto(newBook));
    }

    @GetMapping(
            params = {"page", "limit"},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<Page<BookDto>> getAllBooks(@RequestParam Pageable pageable) {
        Page<Book> allBooks = bookService.getAllBooks(pageable);
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
    ResponseEntity<BookDto> getBook(
            @PathVariable @Valid @Positive(message = "Value must be higher than 0") Long id
    ) {
        Book bookById = bookService.getBookById(id);
        return ResponseEntity.ok(BookMapper.INSTANCE.toDto(bookById));
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> updateBook(
            @PathVariable @Valid @Positive(message = "Value must be higher than 0") Long id,
            @RequestBody @Valid BookDto bookDto
    ) {
        bookService.updateBookById(id, bookDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(
            value = "/{id}"
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<?> deleteBook(
            @PathVariable @Valid @Positive(message = "Value must be higher than 0") Long id
    ) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }
}