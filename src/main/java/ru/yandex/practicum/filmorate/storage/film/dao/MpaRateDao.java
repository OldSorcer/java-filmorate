package ru.yandex.practicum.filmorate.storage.film.dao;

import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.List;

public interface MpaRateDao {
    MpaRating getById(int id);
    List<MpaRating> getAll();
}
