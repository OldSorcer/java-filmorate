package ru.yandex.practicum.filmorate.storage.user.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.MpaRating;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDaoImplTest {

    private final MpaDaoImpl mpaDao;
    @Test
    public void getAllMpaRates() {
        Assertions.assertEquals(3, mpaDao.getAll().size());
    }

    @Test
    public void getMpaById1() {
        MpaRating expected = new MpaRating(1, "PG");
        Assertions.assertEquals(expected, mpaDao.getById(1));
    }

}