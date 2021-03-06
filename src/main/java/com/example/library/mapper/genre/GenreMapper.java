package com.example.library.mapper.genre;


import com.example.library.dto.genre.GenreDto;
import com.example.library.model.genre.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {


    GenreDto toDto(Genre genre);

    Genre dtoToGenre(GenreDto genreDto);
}