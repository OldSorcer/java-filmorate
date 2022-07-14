package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.user.dao.MpaDao;

import java.util.Collection;

@Service
public class MpaRateService {
    MpaDao mpaDao;

    @Autowired
    public MpaRateService(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    public Collection<MpaRating> getAll() {
        return mpaDao.getAll();
    }

    public MpaRating getById(int id) {
        return mpaDao.getById(id);
    }
}
