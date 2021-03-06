package com.example.library.mapper.book;


import com.example.library.dto.book.BookDto;
import com.example.library.model.book.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book dtoToBook(BookDto bookDto);
}