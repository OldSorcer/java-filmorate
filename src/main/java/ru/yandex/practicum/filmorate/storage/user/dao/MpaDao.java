package ru.yandex.practicum.filmorate.storage.user.dao;


import ru.yandex.practicum.filmorate.model.MpaRating;

import java.util.Collection;

public interface MpaDao {
    MpaRating getById(int id);
    Collection<MpaRating> getAll();
}
