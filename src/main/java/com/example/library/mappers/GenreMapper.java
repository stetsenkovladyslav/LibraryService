package com.example.library.mappers;


import com.example.library.dto.GenreDto;
import com.example.library.entities.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreDto toDto(Genre genre);

    Genre dtoToGenre(GenreDto genreDto);
}
