package ru.yandex.practicum.filmorate.storage.film.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.dao.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenresDao genresDao;
    private final MpaRateDao mpaRateDao;
    private final LikesDao likesDao;
    private final DirectorDao directorDao;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenresDao genresDao, MpaRateDao mpaRateDao, LikesDao likesDao, DirectorDao directorDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genresDao = genresDao;
        this.mpaRateDao = mpaRateDao;
        this.likesDao = likesDao;
        this.directorDao = directorDao;
    }

    @Override
    public Film add(Film film) {
        String sqlQuery = "INSERT INTO films (film_name, description, release_date, duration, mpa_rate_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        int filmId = keyHolder.getKey().intValue();
        film.setId(filmId);
        if (Objects.nonNull(film.getGenres())) {
            genresDao.addFilmGenres(film.getGenres(), filmId);
        }
        if (Objects.nonNull(film.getDirectors())) {
            directorDao.addFilmDirectors(film.getDirectors(), filmId);
        }
        return film;
    }

    @Override
    public void delete(Film film) {
        String sqlQuery = "DELETE FROM films WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    @Override
    public Film update(Film film) {
        String sqlQuery = "UPDATE films " +
                "SET film_name = ?, description = ?, release_date = ?, duration = ?, mpa_rate_id = ? " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), Date.valueOf(film.getReleaseDate()),
                film.getDuration(), film.getMpa().getId(), film.getId());
        if (Objects.isNull(film.getGenres()) || film.getGenres().isEmpty()) {
            genresDao.removeGenres(film.getId());
        } else {
            genresDao.removeGenres(film.getId());
            genresDao.addFilmGenres(film.getGenres(), film.getId());
        }
        if (Objects.isNull(film.getDirectors()) || film.getDirectors().isEmpty()) {
            directorDao.deleteFilmDirectors(film.getId());
        } else {
            directorDao.deleteFilmDirectors(film.getId());
            directorDao.addFilmDirectors(film.getDirectors(), film.getId());
        }
        return film;
    }

    @Override
    public List<Film> getAll() {
        String sqlQuery = "SELECT * FROM films";
        return jdbcTemplate.query(sqlQuery, this::makeFilm);
    }

    @Override
    public Film getFilmById(int id) {
        String sqlQuery = "SELECT * FROM films WHERE film_id = ?";
        List<Film> filmList = jdbcTemplate.query(sqlQuery, this::makeFilm, id);
        return filmList.stream().findFirst().orElseThrow(() ->
                new EntityNotFoundException(HttpStatus.NOT_FOUND,
                        String.format("Фильм с ID %d не найден", id)));
    }

    @Override
    public List<Film> getPopularFilms(int count, int genreId, int year) {
        String sqlQuery = "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration, f.mpa_rate_id " +
                "FROM films AS f " +
                "LEFT JOIN films_likes AS fl ON f.film_id = fl.film_id " +
                "LEFT JOIN films_genres AS fg ON f.film_id = fg.film_id " +
                "WHERE fg.genre_id = ? AND EXTRACT(YEAR FROM (f.release_date)) = ?" +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(DISTINCT fl.user_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::makeFilm, genreId, year, count);
    }

    @Override
    public List<Film> getPopularFilmsNonGenresYear(int count) {
        String sqlQuery = "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration, f.mpa_rate_id " +
                "FROM films AS f " +
                "LEFT JOIN films_likes AS fl ON f.film_id = fl.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(DISTINCT fl.user_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::makeFilm, count);
    }

    @Override
    public List<Film> getFilmsByDirectorId(int directorId, String sortedBy) {
        Director director = directorDao.getById(directorId);
        String sqlQuery = "SELECT f.film_id, film_name, description, release_date, duration, mpa_rate_id FROM films AS F " +
                "LEFT JOIN films_likes AS fl ON f.film_id = fl.film_id " +
                "LEFT JOIN films_directors as fd ON f.film_id = fd.film_id " +
                "WHERE fd.director_id = ? " +
                "GROUP BY f.film_id ";
        switch (sortedBy) {
            case "year":
                sqlQuery = sqlQuery +
                        "ORDER BY release_date";
                break;
            case "likes":
                sqlQuery = sqlQuery + "ORDER BY COUNT(fl.user_id) DESC";
        }
        return jdbcTemplate.query(sqlQuery, this::makeFilm, directorId);
    }

    public List<Film> getPopularFilmsNonYear(int count, int genreId) {
        String sqlQuery = "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration, f.mpa_rate_id " +
                "FROM films AS f " +
                "LEFT JOIN films_likes AS fl ON f.film_id = fl.film_id " +
                "LEFT JOIN films_genres AS fg ON f.film_id = fg.film_id " +
                "WHERE fg.genre_id = ?" +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(DISTINCT fl.user_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::makeFilm, genreId, count);
    }

    @Override
    public List<Film> getPopularFilmsNonGenre(int count, int year) {
        String sqlQuery = "SELECT f.film_id, f.film_name, f.description, f.release_date, f.duration, f.mpa_rate_id " +
                "FROM films AS f " +
                "LEFT JOIN films_likes AS fl ON f.film_id = fl.film_id " +
                "WHERE EXTRACT(YEAR FROM (f.release_date)) = ?" +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(DISTINCT fl.user_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::makeFilm, year, count);
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setName(rs.getString("film_name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration"));
        film.setId(rs.getInt("film_id"));
        film.setGenres(genresDao.getByFilmId(film.getId()));
        film.setMpa(mpaRateDao.getById(rs.getInt("mpa_rate_id")));
        film.setLikes(Set.copyOf(likesDao.getLikes(film.getId())));
        List<Director> directors = directorDao.getByFilmId(film.getId());
        film.setDirectors(directors);
        return film;
    }
}