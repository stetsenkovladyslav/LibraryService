package com.example.library.controller.book;

import com.example.library.dto.book.BookCriteria;
import com.example.library.dto.book.BookDto;
import com.example.library.mapper.book.BookMapper;
import com.example.library.model.book.Book;
import com.example.library.service.book.BookService;
import com.example.library.service.file.AwsFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final AwsFileService awsFileService;

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

    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/save/{id}")
    public void save(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
        awsFileService.upload(id, file.getInputStream());
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/download/{id}", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> download(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok()
                .header("Content-disposition", "attachment; fileName=" + id + ".jpg")
                .body(awsFileService.download(id));
    }
}