package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.user.dao.GenreDao;

import java.util.Collection;
import java.util.Set;

@Service
public class GenreService {
    GenreDao genreDao;

    @Autowired
    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Collection<Genre> getAll() {
        return genreDao.getAll();
    }

    public Genre getById(int id) {
        return genreDao.getById(id);
    }

    public Set<Genre> getByFilmId(int filmId) {
        return genreDao.getFilmGenres(filmId);
    }
}
