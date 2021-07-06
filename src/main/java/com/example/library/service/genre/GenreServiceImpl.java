package com.example.library.service.genre;

import com.example.library.dto.genre.GenreDto;
import com.example.library.model.genre.Genre;
import com.example.library.mapper.genre.GenreMapper;
import com.example.library.repository.genre.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Genre> getAllGenres(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }
}