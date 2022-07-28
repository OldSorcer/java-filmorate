package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scriptTest.sql")
class GenreDaoImplTest {
    private final GenreDaoImpl genreDao;
    private final FilmDaoImpl filmDbStorage;
    private final Film film = new Film("Film name",
            "Description",
            1,
            LocalDate.now(),
            200,
            Set.of(),
            List.of(new Genre(1, "Комедия")),
            new MpaRating(1, "G"),
            new ArrayList<>());

    @Test
    public void addFilmGenres() {
        filmDbStorage.add(film);
        Assertions.assertEquals(film.getGenres(), genreDao.getByFilmId(1));
    }

    @Test
    public void getGenreById1() {
        Genre expected = new Genre(1, "Комедия");
        Assertions.assertEquals(expected, genreDao.getById(1));
    }

    @Test
    public void getAll() {
        List<Genre> expected = List.of(new Genre(1, "Комедия"),
                new Genre(2, "Драма"),
                new Genre(3, "Мультфильм"),
                new Genre(4, "Триллер"),
                new Genre(5, "Документальный"),
                new Genre(6, "Боевик"));
        Assertions.assertEquals(expected, genreDao.getAll());
    }

    @Test
    public void getFilmGenres() {
        filmDbStorage.add(film);
        Assertions.assertEquals(film.getGenres(), genreDao.getByFilmId(1));
    }

    @Test
    public void removeGenres() {
        filmDbStorage.add(film);
        genreDao.removeGenres(1);
        Assertions.assertTrue(genreDao.getByFilmId(1).isEmpty());
    }
}