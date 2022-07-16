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
class FriendsDaoImplTest {
    private final FriendsDaoImpl friendsDao;
    private final UserDbStorage userDbStorage;
    private final User userOne = new User("LoginOne",
            "NameOne",
            "Email1@mail.ru",
            1,
            LocalDate.now());
    private final User userTwo = new User("Login",
            "NameTwo",
            "Email2@mail.ru",
            2,
            LocalDate.now());
    private final User commonFriend = new User("Common",
            "Common",
            "Common@mail.ru",
            3,
            LocalDate.now());

    @Test
    @Order(1)
    public void addFriends() {
        userDbStorage.add(userOne);
        userDbStorage.add(userTwo);
        userDbStorage.add(commonFriend);
        friendsDao.addFriend(userOne.getId(), userTwo.getId());
        Assertions.assertEquals(List.of(userTwo), friendsDao.getFriends(userOne.getId()));
    }

    @Test
    @Order(2)
    public void getFriends() {
        Assertions.assertEquals(List.of(userTwo), friendsDao.getFriends(userOne.getId()));
    }

    @Test
    @Order(3)
    public void deleteFromFriends() {
        friendsDao.deleteFriend(userOne.getId(), userTwo.getId());
        Assertions.assertEquals(List.of(), friendsDao.getFriends(userOne.getId()));
    }

    @Test
    @Order(4)
    public void getCommonFriend() {
        friendsDao.addFriend(userOne.getId(), commonFriend.getId());
        friendsDao.addFriend(userTwo.getId(), commonFriend.getId());
        Assertions.assertEquals(List.of(commonFriend), friendsDao.getCommonFriends(userOne.getId(), userTwo.getId()));
    }
}