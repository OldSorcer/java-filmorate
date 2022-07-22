DELETE FROM users;
ALTER TABLE users ALTER COLUMN user_id RESTART WITH 1;

DELETE FROM films;
ALTER TABLE films ALTER COLUMN film_id RESTART WITH 1;

DELETE FROM friendships;

DELETE FROM films_likes;

DELETE FROM films_genres;

DELETE FROM directors;
ALTER TABLE directors ALTER COLUMN director_id RESTART WITH 1;

DELETE FROM films_directors;