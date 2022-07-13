# java-filmorate
## ER диаграмма базы данных проекта
![Database schema](/doc/ERD.png)

## Ссылка на ER - диаграмму:
### https://app.quickdatabasediagrams.com/#/d/cZYQCA

### Примеры запросов:
* Получение списка всех пользователей:
<code> SELECT * FROM users;</code>
* Получение пользователя по ID: 
<code> SELECT * FROM users WHERE user_id = ?; </code>
* Получение жанра по ID:
<code> SELECT genre_name FROM genres WHERE genre_id = ?; </code>
* Получение списка друзей пользователя:
<code> SELECT * FROM users WHERE user_id IN (SELECT friend_id FROM friendships WHERE user_id = ?);  </code>