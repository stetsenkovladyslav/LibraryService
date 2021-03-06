package com.example.library.service.genre;

import com.example.library.dto.genre.GenreDto;
import com.example.library.mapper.genre.GenreMapper;
import com.example.library.model.genre.Genre;
import com.example.library.repository.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;


    @Override
    public Genre addGenre(GenreDto genreDto) {
        return genreRepository.save(new Genre(genreDto.getGenreName()));
    }

    @Override
    public void deleteGenreById(long id) {
        genreRepository.deleteById(id);
    }

    @Override
    public void updateGenre(long id, GenreDto genreDto) {
        genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Genre with id:{" + id + "} does not exist"));
        Genre updated = genreMapper.dtoToGenre(genreDto);
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