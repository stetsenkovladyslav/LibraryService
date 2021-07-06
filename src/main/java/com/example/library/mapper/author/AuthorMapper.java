package com.example.library.mapper.author;

import com.example.library.controller.author.AuthorController;
import com.example.library.dto.author.AuthorDto;
import com.example.library.model.author.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {


    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDto toDto(Author author);

    Author dtoToAuthor(AuthorDto authorDto);

}