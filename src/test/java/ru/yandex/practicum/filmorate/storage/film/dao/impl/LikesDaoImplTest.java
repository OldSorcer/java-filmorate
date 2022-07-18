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
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.dao.impl.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scriptTest.sql")

class LikesDaoImplTest {
    private final LikesDaoImpl likesDao;
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;
    private final User userOne = new User("LoginOne",
            "NameOne",
            "Email1@mail.ru",
            1,
            LocalDate.now(),
            Set.of());
    private final Film film = new Film("Film name",
            "Description",
            1,
            LocalDate.now(),
            200,
            Set.of(),
            List.of(new Genre(1, "Комедия")),
            new MpaRating(1, "G"));


    @Test
    public void addAndGetLike() {
        userDbStorage.add(userOne);
        filmDbStorage.add(film);
        likesDao.addLike(1, 1);
        Assertions.assertEquals(List.of(1), likesDao.getLikes(1));
    }

    @Test
    public void deleteLike() {
        userDbStorage.add(userOne);
        filmDbStorage.add(film);
        likesDao.addLike(1, 1);
        likesDao.deleteLike(1, 1);
        Assertions.assertTrue(likesDao.getLikes(1).isEmpty());
    }
}