package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.dao.DirectorDao;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.List;

@Service
public class DirectorService {
    private final DirectorDao directorDao;

    @Autowired
    public DirectorService(DirectorDao directorDao) {
        this.directorDao = directorDao;
    }

    public List<Director> getAll() {
        return directorDao.getAll();
    }

    public Director getById(int id) {
        return directorDao.getById(id);
    }

    public void deleteById(int id) {
        directorDao.deleteById(id);
    }

    public Director update(Director director) {
        Validator.isValidDirector(director);
        return directorDao.update(director);
    }

    public Director add(Director director) {
        Validator.isValidDirector(director);
        return directorDao.add(director);
    }
}