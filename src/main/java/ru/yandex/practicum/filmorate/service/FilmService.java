package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.dao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.dao.LikesDao;
import ru.yandex.practicum.filmorate.storage.user.dao.UserStorage;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikesDao likesDao;
    private final GenreService genreService;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,
                       LikesDao likesDao,
                       GenreService genreService) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesDao = likesDao;
        this.genreService = genreService;
    }

    public Film add(Film film) {
        Validator.isValidFilm(film);
        if (Objects.nonNull(film.getGenres()) || film.getGenres().isEmpty()) { //Если список жанров не пустой и не равен
            film.setGenres(genreService.deleteDuplicates(film.getGenres()));   //null удаляем дубликаты жанров
        }
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        Validator.isValidFilm(film);
        Film foundedFilm = filmStorage.getFilmById(film.getId());
        if (film.getGenres().isEmpty() || Objects.nonNull(film.getGenres())) {
            film.setGenres(genreService.deleteDuplicates(film.getGenres()));
        }
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

    public List<Film> getFilmsByDirectorId(int directorId, String sortedBy) {
        return filmStorage.getFilmsByDirectorId(directorId, sortedBy);
    }
}