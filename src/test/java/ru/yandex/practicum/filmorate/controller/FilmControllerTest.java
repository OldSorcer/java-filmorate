package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootTest
class FilmControllerTest {
    private FilmController filmController;
    private Film film;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
        film = new Film("Film name",
                "Description",
                1,
                LocalDate.now(),
                200,
                Set.of(),
                List.of(new Genre(1, "Комедия")),
                new MpaRating(1, "G"));
    }

    @Test
    void shouldReturnFilmIfAdded() {
        filmController.add(film);
        Assertions.assertAll(() -> Assertions.assertEquals(1, filmController.getAll().size()),
                () -> Assertions.assertEquals(film, filmController.getAll().get(0)));
    }

    @Test
    void shouldReturnFilmIfUpdated() {
        filmController.add(film);
        film.setName("UpdatedFilmName");
        Assertions.assertEquals(film, filmController.update(film));
    }

    @Test
    void shouldReturnFilmList() {
        List<Film> expectedList = List.of(film);

        filmController.add(film);
        Assertions.assertEquals(expectedList, filmController.getAll());
    }

    @Test
    void shouldThrowValidationExceptionIfFilmAlreadyExist() {
        filmController.add(film);
        Assertions.assertThrows(ValidationException.class, () -> filmController.add(film));
    }

    @Test
    void shouldThrowValidationExceptionIfFilmWithId2DoesNotExist() {
        filmController.add(film);
        film.setId(2);
        Assertions.assertThrows(ValidationException.class, () -> filmController.update(film));
    }
}