package ru.yandex.practicum.filmorate.storage.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {
        Validator.isValidUser(user);
        String query = "INSERT INTO users (login, user_name, email, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] {"user_id"});
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
            }, keyHolder);
        int userId = keyHolder.getKey().intValue();
        String findUserQuery = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(findUserQuery, this::makeUser, userId);
    }

    @Override
    public void delete(User user) {
        String query = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(query, user.getId());
    }

    @Override
    public User update(User user) {
        String queryForSearch = "SELECT * FROM users WHERE user_id = ?";
        User findedUser = jdbcTemplate.queryForObject(queryForSearch, this::makeUser, user.getId());
        if (Objects.isNull(findedUser)) {
            throw new EntityNotFoundException(HttpStatus.NOT_FOUND,
                    String.format("Пользователь с ID %d не существует", user.getId()));
        }
        String queryForUpdate = "UPDATE users SET login = ?, user_name = ?, email = ?, birthday = ? WHERE user_id = ?";
        jdbcTemplate.update(queryForUpdate,
                user.getLogin(),
                user.getName(),
                user.getEmail(),
                Date.valueOf(user.getBirthday()),
                user.getId());
        String queryForSearchUpdatedUser = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(queryForSearchUpdatedUser,this::makeUser, user.getId());
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users";
        return jdbcTemplate.query(query, this::makeUser);
    }

    @Override
    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(query, this::makeUser, id);
    }

    private User makeUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("user_Id"));
        user.setName(resultSet.getString("user_name"));
        user.setEmail(resultSet.getString("email"));
        user.setLogin(resultSet.getString("login"));
        user.setBirthday(resultSet.getDate("birthday").toLocalDate());
        return user;
    }
}
