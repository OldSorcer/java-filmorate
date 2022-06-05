package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {
    private FilmController filmController;
    private UserController userController;
    private Film film;
    private User user;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController();
        userController = new UserController();
        film = new Film("Name of film",
                "Description of a very interesting movie with kittens, pink ponies and unicorns",
                1,
                LocalDate.now(),
                120);
        user = new User("Login", "Name", "Email@mail.ru", 1, LocalDate.now());
    }
}