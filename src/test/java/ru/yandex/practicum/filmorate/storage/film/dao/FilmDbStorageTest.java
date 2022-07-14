package ru.yandex.practicum.filmorate.storage.film.dao;

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

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private Film film = new Film("Name", "Description", 1, LocalDate.now(), 200, Set.of(new Genre(1, "Комедия")), new MpaRating(1, "PG"));
    private Film filmForUpdate = new Film("New Name", "New Description", 1, LocalDate.now(), 200, Set.of(new Genre(1, "Комедия")), new MpaRating(1, "PG"));

    @Test
    public void addFilm() {
        filmDbStorage.add(film);
        Assertions.assertEquals(film, filmDbStorage.getFilmById(1));
    }

    @Test
    public void updateFilm() {
        Assertions.assertEquals(filmForUpdate, filmDbStorage.update(filmForUpdate));
    }

    @Test
    public void getFilmById1() {
        Assertions.assertEquals(film, filmDbStorage.getFilmById(1));
    }

    @Test
    public void getAllFilms() {
        Assertions.assertEquals(1, filmDbStorage.getAll().size());
    }

    @Test
    public void deleteFilm() {
        filmDbStorage.delete(filmForUpdate);
        Assertions.assertTrue(filmDbStorage.getAll().isEmpty());
    }
}