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
class FriendsDaoImplTest {
    private final FriendsDaoImpl friendsDao;
    private final UserDbStorage userDbStorage;
    private final User userOne = new User("LoginOne",
            "NameOne",
            "Email1@mail.ru",
            1,
            LocalDate.now(),
            Set.of());
    private final User userTwo = new User("Login",
            "NameTwo",
            "Email2@mail.ru",
            2,
            LocalDate.now(),
            Set.of());
    private final User commonFriend = new User("Common",
            "Common",
            "Common@mail.ru",
            3,
            LocalDate.now(),
            Set.of());

    @Test
    public void addFriends() {
        userDbStorage.add(userOne);
        userDbStorage.add(userTwo);
        userDbStorage.add(commonFriend);
        friendsDao.addFriend(userOne.getId(), userTwo.getId());
        Assertions.assertEquals(List.of(userTwo), friendsDao.getFriends(userOne.getId()));
    }

    @Test
    public void getFriends() {
        userDbStorage.add(userOne);
        userDbStorage.add(userTwo);
        friendsDao.addFriend(userOne.getId(),userTwo.getId());
        Assertions.assertEquals(List.of(userTwo), friendsDao.getFriends(userOne.getId()));
    }

    @Test
    public void deleteFromFriends() {
        userDbStorage.add(userOne);
        userDbStorage.add(userTwo);
        friendsDao.addFriend(userOne.getId(), userTwo.getId());
        friendsDao.deleteFriend(userOne.getId(), userTwo.getId());
        Assertions.assertEquals(List.of(), friendsDao.getFriends(userOne.getId()));
    }

    @Test
    public void getCommonFriend() {
        userDbStorage.add(userOne);
        userDbStorage.add(userTwo);
        userDbStorage.add(commonFriend);
        friendsDao.addFriend(userOne.getId(), commonFriend.getId());
        friendsDao.addFriend(userTwo.getId(), commonFriend.getId());
        Assertions.assertEquals(List.of(commonFriend), friendsDao.getCommonFriends(userOne.getId(), userTwo.getId()));
    }
}