package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRateService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
public class MpaController {
    MpaRateService mpaRateService;

    @Autowired
    public MpaController(MpaRateService mpaRateService) {
        this.mpaRateService = mpaRateService;
    }

    @GetMapping("/{id}")
    public MpaRating getById(@PathVariable int id) {
        return mpaRateService.getById(id);
    }

    @GetMapping
    public Collection<MpaRating> getAll() {
        return mpaRateService.getAll();
    }
}
