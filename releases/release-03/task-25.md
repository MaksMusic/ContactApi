Сложность: 3 / 10
Релиз: 03
Номер: task-25
Кто: Наталья
Модуль: qa
Спека: sdd/task-25/spec.md

Зачем.
Чтобы любой мог ткнуть search и extract из IDEA без угадывания URL.

Что сделать.
1. Создай файл qa/search.http (папку qa создай, если её нет).
2. Три запроса:
- POST http://localhost:8080/api/v1/contacts/search с телом query и city
- POST http://localhost:8080/api/v1/contacts/search с пустым телом {}
- POST http://localhost:8081/internal/v1/extract с телом text
3. Напиши комментарием: сейчас заглушки, status должен быть NOT_FOUND; пустой query/text — 400.
4. Автотесты Java не писать. Бота не проверять на поиск.

Как проверить.
Файл открывается в IDEA как HTTP Client.
URL и поля query, city, text видны.

Готово когда.
Файл на месте.
Есть коммит.
