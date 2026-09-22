Релиз: 01
Номер: task-08
Кто: Дмитрий Заварин
Модуль: rest-core

Зачем.
Потом мы будем помнить, что человек искал. Сейчас таблицы в базе ещё нет. Нужен только класс в коде, как будет выглядеть одна запись. Поиск и контроллер не пишем.

Что сделать.

1. В модуле rest-core создай класс ContactSearch.
Пакет: org.example.restcore.entity
Это сущность JPA. Пометь класс @Entity.

2. В классе ровно четыре поля. Имена не менять.
id — номер записи, Long, это первичный ключ (@Id).
query — что человек написал в поиск, String.
city — город, String, может быть null.
createdAt — когда запись создали, Instant.

3. Рядом интерфейс ContactSearchRepository.
Пакет: org.example.restcore.repository
Он extends JpaRepository<ContactSearch, Long>.
Методы сам не пиши.

4. Не делать.
Контроллер.
Сервис.
Поиск организаций.
Пустые папки без классов.
Фильтр и логи — это задача другого Дмитрия.

Как проверить.
В IDEA открой ContactSearch и проверь четыре поля.
Собери модуль rest-core: mvn -pl rest-core compile
Базу поднимать не нужно.

Готово когда.
Класс ContactSearch есть, поля id, query, city, createdAt на месте.
Интерфейс ContactSearchRepository есть.
Проект собирается.
Есть коммит в rest-core.
