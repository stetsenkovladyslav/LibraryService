package com.example.library.mappers;

import com.example.library.dto.AuthorDto;
import com.example.library.entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDto toDto(Author author);

    Author dtoToAuthor(AuthorDto authorDto);

}
