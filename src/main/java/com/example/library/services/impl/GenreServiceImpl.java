package com.example.library.services.impl;

import com.example.library.entities.Book;
import com.example.library.mappers.BookMapper;
import com.example.library.mappers.GenreMapper;
import com.example.library.repositories.GenreRepository;
import com.example.library.dto.GenreDto;
import com.example.library.entities.Genre;
import com.example.library.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre addGenre(GenreDto genreDto) {
        return genreRepository.save(new Genre(genreDto.getName()));
    }

    @Override
    public void deleteGenreById(long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public void updateGenre(long id, GenreDto genreDto) {
        genreRepository.findById(id).orElseThrow();
        Genre updated = GenreMapper.INSTANCE.dtoToGenre(genreDto);
        genreRepository.save(updated);
    }

    @Override
    public Genre getGenreById(long id) {
        return genreRepository.getById(id);
    }

    @Override
    public Page<Genre> getAllGenres(int page, int limit) {
        return genreRepository.findAll(PageRequest.of(page, limit));
    }
}
