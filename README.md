# java-filmorate
## ER диаграмма базы данных проекта
![Database schema](/doc/ERD.png)

## Ссылка на ER - диаграмму:
### https://app.quickdatabasediagrams.com/#/d/cZYQCA

### Примеры запросов:
* Получение списка всех пользователей:

  <code> SELECT * FROM users;</code>
---
* Получение пользователя по ID:

  <code> SELECT * FROM users WHERE user_id = ?; </code>
---
* Получение жанра по ID:

  <code> SELECT genre_name FROM genres WHERE genre_id = ?; </code>
---
* Получение списка друзей пользователя:
<code>SELECT * FROM users WHERE user_id IN (SELECT friend_id FROM friendships WHERE user_id = ?);</code>
---
* Получение списка из наиболее популярных фильмов:

  <code> SELECT f.FILM_ID, film_name, description, release_date, duration, mpa_rate_id FROM films AS f
  LEFT JOIN films_likes AS fl ON f.film_id = fl.film_id
  GROUP BY f.FILM_ID
  ORDER BY COUNT(fl.USER_ID) DESC
  LIMIT ? </code>