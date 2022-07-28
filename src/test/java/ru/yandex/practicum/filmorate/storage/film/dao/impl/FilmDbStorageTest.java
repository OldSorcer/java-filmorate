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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scriptTest.sql")
class FilmDbStorageTest {
    private final FilmDaoImpl filmDbStorage;
    private final Film film = new Film("Film name",
            "Description",
            1,
            LocalDate.now(),
            200,
            Set.of(),
            List.of(new Genre(1, "Комедия")),
            new MpaRating(1, "G"),
            new ArrayList<>() {
            });

    @Test
    public void addFilm() {
        filmDbStorage.add(film);
        assertEquals(film, filmDbStorage.getFilmById(1));
    }

    @Test
    public void getFilmById() {
        filmDbStorage.add(film);
        assertEquals(film, filmDbStorage.getFilmById(1));
    }

    @Test
    public void getAllFilms() {
        filmDbStorage.add(film);
        assertEquals(List.of(film), filmDbStorage.getAll());
    }

    @Test
    public void updateFilm() {
        filmDbStorage.add(film);
        film.setName("New name");
        filmDbStorage.update(film);
        assertEquals(film, filmDbStorage.getFilmById(1));
    }

    @Test
    public void deleteFilm() {
        filmDbStorage.add(film);
        filmDbStorage.delete(film);
        assertTrue(filmDbStorage.getAll().isEmpty());
    }
}