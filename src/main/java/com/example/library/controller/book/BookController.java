
package com.example.library.controller.book;

import com.example.library.dto.book.BookCriteria;
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
    private final BookMapper bookMapper;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_ADMIN"})
    ResponseEntity<BookDto> createBook(@RequestBody @Valid BookDto bookDto) {
        Book newBook = bookService.addBook(bookDto);
        return ResponseEntity.ok(bookMapper.toDto(newBook));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    ResponseEntity<Page<BookDto>> getAllBooks(Pageable pageable, BookCriteria bookCriteria) {
        Page<Book> allBooks = bookService.getAllBooks(pageable, bookCriteria);
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
        return ResponseEntity.ok(bookMapper.toDto(bookById));
    }

    @PatchMapping(
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