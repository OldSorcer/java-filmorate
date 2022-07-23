package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.dao.GenresDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenresDao genresDao;

    @Autowired
    public GenreService(GenresDao genresDao) {
        this.genresDao = genresDao;
    }

    public List<Genre> getAll() {
        return genresDao.getAll();
    }

    public Genre getById(int id) {
        return genresDao.getById(id);
    }

    public List<Genre> deleteDuplicates(List<Genre> genres) {
        return genres.stream().distinct().collect(Collectors.toList());
    }
}
