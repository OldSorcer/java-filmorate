package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scriptTest.sql")
class DirectorDaoImplTest {
    private final DirectorDaoImpl directorDao;
    private final FilmDbStorage filmDbStorage;
    private final Film film = new Film("Film name",
            "Description",
            1,
            LocalDate.now(),
            200,
            Set.of(),
            List.of(new Genre(1, "Комедия")),
            new MpaRating(1, "G"),
            List.of(new Director(1, "Name"))
            );
    private final Director director = new Director(1, "name");

    @Test
    public void addDirector() {
        Assertions.assertEquals(director, directorDao.add(director));
    }

    @Test
    public void deleteDirector() {
        directorDao.add(director);
        directorDao.deleteById(1);
        Assertions.assertTrue(directorDao.getAll().isEmpty());
    }

    @Test
    public void getAllDirectors() {
        List<Director> expected = List.of(director);
        directorDao.add(director);
        Assertions.assertEquals(expected, directorDao.getAll());
    }

    @Test
    public void updateDirector() {
        Director expected = new Director(1, "Updated director");
        directorDao.add(director);
        director.setName("Updated director");
        directorDao.update(director);
        Assertions.assertEquals(expected, directorDao.getById(1));
    }

    @Test
    public void getDirectodById() {
        directorDao.add(director);
        Assertions.assertEquals(director, directorDao.getById(1));
    }

    @Test
    public void addFilmDirectorsAndGetDirectorByFilmId() {
        List<Director> expected = List.of(director);
        directorDao.add(director);
        filmDbStorage.add(film);
        directorDao.addFilmDirectors(film.getDirectors(), film.getId());
        Assertions.assertEquals(expected, directorDao.getByFilmId(1));
    }
}