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
class UserDaoImplTest {
    private final UserDaoImpl userDaoImpl;
    private final User userOne = new User("LoginOne",
            "NameOne",
            "Email1@mail.ru",
            1,
            LocalDate.now(),
            Set.of());

    @Test
    public void addUser() {
        userDaoImpl.add(userOne);
        Assertions.assertEquals(userOne, userDaoImpl.getUserById(1));
    }

    @Test
    public void getUserById1() {
        userDaoImpl.add(userOne);
        Assertions.assertEquals(userOne, userDaoImpl.getUserById(1));
    }

    @Test
    public void getAllUsers() {
        userDaoImpl.add(userOne);
        Assertions.assertEquals(List.of(userOne), userDaoImpl.getAll());
    }

    @Test
    public void updateUser() {
        userDaoImpl.add(userOne);
        userOne.setName("New name");
        userDaoImpl.update(userOne);
        Assertions.assertEquals(userOne, userDaoImpl.getUserById(1));
    }

    @Test
    public void deleteUser() {
        userDaoImpl.add(userOne);
        userDaoImpl.delete(userOne);
        Assertions.assertTrue(userDaoImpl.getAll().isEmpty());
    }
}