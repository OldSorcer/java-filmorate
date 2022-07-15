package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.film.dao.impl.MpaRateDao;

import java.util.List;

@Service
public class MpaService {
    private final MpaRateDao mpaRateDao;

    @Autowired
    public MpaService(MpaRateDao mpaRateDao) {
        this.mpaRateDao = mpaRateDao;
    }

    public List<MpaRating> getAll() {
        return mpaRateDao.getAll();
    }

    public MpaRating getById(int id) {
        return mpaRateDao.getById(id);
    }
}
