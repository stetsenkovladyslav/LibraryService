package com.example.library.mappers;


import com.example.library.dto.BookDto;
import com.example.library.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface BookMapper {


    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto toDto(Book book);

    Book dtoToBook(BookDto bookDto);
}