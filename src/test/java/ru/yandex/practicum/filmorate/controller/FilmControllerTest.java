package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {
    private FilmController filmController;
    private Film film;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
        film = new Film("Name of film",
                "Description of a very interesting movie with kittens, pink ponies and unicorns",
                1,
                LocalDate.now(),
                120);
    }

    @Test
    void shouldThrowValidationExceptionIfFilmNameIsEmpty() {
        film.setName("");
        Assertions.assertThrows(ValidationException.class, () -> filmController.add(film));
    }

    @Test
    void shouldThrowValidationExceptionIfFilmDescriptionLengthIs234() {
        film.setDescription(film.getDescription() + film.getDescription() + film.getDescription());
        Assertions.assertThrows(ValidationException.class, () -> filmController.add(film));
    }

    @Test
    void shouldThrowValidationExceptionIfFilmReleaseDateIf27December1895() {
        film.setReleaseDate(LocalDate.of(1895, 12, 26));
        Assertions.assertThrows(ValidationException.class, () -> filmController.add(film));
    }

    @Test
    void shouldReturnValidationExceptionIfFilmDurationIsBelow0() {
        film.setDuration(-1);
        Assertions.assertThrows(ValidationException.class, () -> filmController.add(film));
    }

    @Test
    void filmShouldBeAdded() {
        filmController.add(film);
        Assertions.assertAll(() -> Assertions.assertEquals(1, filmController.getAll().size()),
                () -> Assertions.assertEquals(film, filmController.getAll().get(0)));
    }
}