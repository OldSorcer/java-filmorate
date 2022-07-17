package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaRateDaoImplTest {
    private final MpaRateDaoImpl mpaRateDao;

    @Test
    public void getById1() {
        MpaRating expected = new MpaRating(1, "G");
        Assertions.assertEquals(expected, mpaRateDao.getById(1));
    }

    @Test
    public void getAll() {
        List<MpaRating> expected = List.of(new MpaRating(1, "G"),
                new MpaRating(2, "PG"),
                new MpaRating(3, "PG-13"),
                new MpaRating(4, "R"),
                new MpaRating(5, "NC-17"));
        Assertions.assertEquals(expected, mpaRateDao.getAll());
    }
}