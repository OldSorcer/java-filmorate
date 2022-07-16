package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;
    private final User userOne = new User("LoginOne",
            "NameOne",
            "Email1@mail.ru",
            1,
            LocalDate.now());

    @Test
    @Order(1)
    public void addUser() {
        userDbStorage.add(userOne);
        Assertions.assertEquals(userOne, userDbStorage.getUserById(1));
    }

    @Test
    @Order(2)
    public void getUserById1() {
        Assertions.assertEquals(userOne, userDbStorage.getUserById(1));
    }

    @Test
    @Order(3)
    public void getAllUsers() {
        Assertions.assertEquals(List.of(userOne), userDbStorage.getAll());
    }

    @Test
    @Order(4)
    public void updateUser() {
        userOne.setName("New name");
        userDbStorage.update(userOne);
        Assertions.assertEquals(userOne, userDbStorage.getUserById(1));
    }

    @Test
    @Order(5)
    public void deleteUser() {
        userDbStorage.delete(userOne);
        Assertions.assertTrue(userDbStorage.getAll().isEmpty());
    }
}