Сложность: 4 / 10
Релиз: 03
Номер: task-20
Кто: Дмитрий
Модуль: rest-core
Спека: sdd/task-20/spec.md

Зачем.
Если человек не прислал название, нельзя делать вид, что искали. Нужна ошибка 400.

Что сделать.
1. На POST /api/v1/contacts/search: если query нет или пустой (пробелы не считаются) — ответ 400.
2. Тело ошибки — класс ErrorResponse. Поля:
code = QUERY_REQUIRED
message = Укажите название организации
3. Если класса ErrorResponse ещё нет — создай его в dto.
4. Поиск и Feign не писать. Заглушку Егора не ломай: когда query есть, по-прежнему 200 и NOT_FOUND.

Как проверить.
curl -X POST http://localhost:8080/api/v1/contacts/search -H "Content-Type: application/json" -d "{}"
Ожидание: 400, в JSON есть code и message.

curl с query «макдак» — по-прежнему 200 и NOT_FOUND.

Готово когда.
Пустой запрос даёт 400.
С query — как у Егора.
Есть коммит в rest-core.
