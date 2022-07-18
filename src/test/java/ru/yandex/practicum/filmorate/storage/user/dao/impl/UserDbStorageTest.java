package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scriptTest.sql")
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;
    private final User userOne = new User("LoginOne",
            "NameOne",
            "Email1@mail.ru",
            1,
            LocalDate.now(),
            Set.of());

    @Test
    public void addUser() {
        userDbStorage.add(userOne);
        Assertions.assertEquals(userOne, userDbStorage.getUserById(1));
    }

    @Test
    public void getUserById1() {
        userDbStorage.add(userOne);
        Assertions.assertEquals(userOne, userDbStorage.getUserById(1));
    }

    @Test
    public void getAllUsers() {
        userDbStorage.add(userOne);
        Assertions.assertEquals(List.of(userOne), userDbStorage.getAll());
    }

    @Test
    public void updateUser() {
        userDbStorage.add(userOne);
        userOne.setName("New name");
        userDbStorage.update(userOne);
        Assertions.assertEquals(userOne, userDbStorage.getUserById(1));
    }

    @Test
    public void deleteUser() {
        userDbStorage.add(userOne);
        userDbStorage.delete(userOne);
        Assertions.assertTrue(userDbStorage.getAll().isEmpty());
    }
}