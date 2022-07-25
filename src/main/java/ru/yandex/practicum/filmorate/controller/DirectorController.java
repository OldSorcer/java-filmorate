package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@RestController
@RequestMapping("/directors")
@Slf4j
public class DirectorController {
    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping
    public List<Director> getAllDirectors() {
        log.info("Поступил GET запрос к эндпоинту /directors");
        return directorService.getAll();
    }

    @GetMapping("/{id}")
    public Director getById(@PathVariable int id) {
        log.info("Поступил GET запрос к эндпоинту /directors/{}", id);
        return directorService.getById(id);
    }

    @PostMapping
    public Director add(@RequestBody Director director) {
        log.info("Поступил POST запрос к эндпоинту /directors");
        return directorService.add(director);
    }

    @PutMapping
    public Director update(@RequestBody Director director) {
        log.info("Поступил PUT запрос к эндпоинту /directors");
        return directorService.update(director);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        log.info("Поступил DELETE запрос к эндпоинту /derectors/{}", id);
        directorService.deleteById(id);
    }
}