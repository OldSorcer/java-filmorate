package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.dao.FilmDao;
import ru.yandex.practicum.filmorate.storage.film.dao.LikesDao;
import ru.yandex.practicum.filmorate.storage.user.dao.UserDao;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmDao filmDao;
    private final UserDao userDao;
    private final LikesDao likesDao;
    private final GenreService genreService;

    @Autowired
    public FilmService(@Qualifier("filmDaoImpl") FilmDao filmDao,
                       @Qualifier("userDaoImpl") UserDao userDao,
                       LikesDao likesDao,
                       GenreService genreService) {
        this.filmDao = filmDao;
        this.userDao = userDao;
        this.likesDao = likesDao;
        this.genreService = genreService;
    }

    public Film add(Film film) {
        Validator.validateFilm(film);
        if (Objects.nonNull(film.getGenres()) || !film.getGenres().isEmpty()) {
            film.setGenres(genreService.deleteDuplicates(film.getGenres()));
        }
        return filmDao.add(film);
    }

    public Film update(Film film) {
        Validator.validateFilm(film);
        if (Objects.nonNull(film.getGenres())) {
            film.setGenres(genreService.deleteDuplicates(film.getGenres()));
        }
        return filmDao.update(film);
    }

    public void setLike(int userId, int filmId) {
        likesDao.addLike(userId, filmId);
    }

    public void deleteLike(int userId, int filmId) {
        likesDao.deleteLike(userId, filmId);
    }

    public List<Film> getPopularFilms(Integer count, Integer genreId, Integer year) {
        if (Objects.isNull(year) && Objects.isNull(genreId)) {
            return filmDao.getPopularFilmsNonGenresYear(count);
        }
        if (Objects.isNull(year)) {
            return filmDao.getPopularFilmsNonYear(count, genreId);
        }
        if (Objects.isNull(genreId)) {
            return filmDao.getPopularFilmsNonGenre(count, year);
        }
        return filmDao.getPopularFilms(count, genreId, year);
    }

    public Film getFilmById(int id) {
        return filmDao.getFilmById(id);
    }

    public List<Film> getAll() {
        return filmDao.getAll();
    }

    public List<Film> getFilmsByDirectorId(int directorId, String sortedBy) {
        return filmDao.getFilmsByDirectorId(directorId, sortedBy);
    }

    public void deleteFilmById(int id) {
        filmDao.deleteFilmById(id);
    }

    public List<Film> getCommonFilms(int userId, int friendId) {
        return filmDao.getCommonFilms(userDao.getUserById(userId).getId(),
                userDao.getUserById(friendId).getId());
    }

    public List<Film> searchFilms(String query, String by) {
        String[] split = by.split(",");
        List<Film> films;
        List<Film> filmsByDirectorsAndFilms = filmDao.searchFilms("%" + query + "%");
        if (split.length == 2) {
            films = new ArrayList<>(filmsByDirectorsAndFilms);
            return films;
        }
        if ("title".equals(split[0])) {
            films = filmsByDirectorsAndFilms.stream()
                    .filter(film -> film.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            films = filmsByDirectorsAndFilms.stream()
                    .filter(film -> isDirectorFind(film, query))
                    .collect(Collectors.toList());
        }
        return films;
    }

    private boolean isDirectorFind(Film film, String query) {
        for (Director director : film.getDirectors()) {
            if (director.getName().toLowerCase().contains(query.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}