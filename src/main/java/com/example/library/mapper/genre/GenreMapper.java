package com.example.library.mapper.genre;


import com.example.library.dto.genre.GenreDto;
import com.example.library.model.genre.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreDto toDto(Genre genre);

    Genre dtoToGenre(GenreDto genreDto);
}