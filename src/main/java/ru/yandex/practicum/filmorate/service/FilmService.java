package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.dao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.dao.LikesDao;
import ru.yandex.practicum.filmorate.storage.user.dao.UserStorage;

import java.util.List;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikesDao likesDao;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       LikesDao likesDao) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesDao = likesDao;
    }

    public Film add(Film film) {
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void setLike(int userId, int filmId) {
        likesDao.addLike(userId, filmId);
    }

    public void deleteLike(int userId, int filmId) {
        likesDao.deleteLike(userId, filmId);
    }

    public List<Film> getPopularFilms(int count, int genreId, int year) {
        if ((year == 0) && (genreId == 0)) {
            return filmStorage.getPopularFilmsNonGenresYear(count);
        }
        if (year == 0) {
            return filmStorage.getPopularFilmsNonYear(count, genreId);
        }
        if (genreId == 0) {
            return filmStorage.getPopularFilmsNonGenre(count, year);
        }
        return filmStorage.getPopularFilms(count, genreId, year);
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }
}