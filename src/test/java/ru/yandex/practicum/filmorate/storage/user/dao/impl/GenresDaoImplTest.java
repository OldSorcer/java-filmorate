package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.film.dao.impl.FilmDbStorage;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenresDaoImplTest {
    private final GenresDaoImpl genresDao;
    private final FilmDbStorage filmDbStorage;
    private final Film film = new Film("Name", "Description", 1, LocalDate.now(), 200, Set.of(new Genre(1, "Комедия")), new MpaRating(1, "PG"));

    @Test
    public void getAllGenres() {
        Assertions.assertEquals(4, genresDao.getAll().size());
    }

    @Test
    public void getGenreById1() {
        Genre expected = new Genre(1, "Комедия");
        Assertions.assertEquals(expected, genresDao.getById(1));
    }

    @Test
    public void getGenreByFilmId() {
        Set<Genre> expected = Set.of(new Genre(1, "Комедия"));
        filmDbStorage.add(film);
        Assertions.assertEquals(expected, genresDao.getFilmGenres(film.getId()));
    }
}