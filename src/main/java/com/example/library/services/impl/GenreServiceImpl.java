package com.example.library.services.impl;

import com.example.library.dao.GenreDao;
import com.example.library.dto.GenreDto;
import com.example.library.entities.Genre;
import com.example.library.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Genre addGenre(GenreDto genreDto) {
        return genreDao.save(new Genre(genreDto.getName()));
    }

    @Override
    public void deleteGenreById(long id) {
        genreDao.deleteById(id);
    }

    @Override
    public void updateGenre(long id, GenreDto genreDto) {
        genreDao.updateGenreById(id, genreDto.getName());
    }

    @Override
    public Genre getGenreById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public Page<Genre> getAllGenres(int page, int limit) {
        return genreDao.findAll(PageRequest.of(page, limit));
    }
}
