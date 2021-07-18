package com.example.library.mapper.author;

import com.example.library.dto.author.AuthorDto;
import com.example.library.model.author.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toDto(Author author);

    Author dtoToAuthor(AuthorDto authorDto);

}