Сложность: 4 / 10
Релиз: 03
Номер: task-19
Кто: Егор
Модуль: rest-core
Спека: sdd/task-19/spec.md

Зачем.
Чтобы клиент мог дернуть поиск и получить понятный JSON. Настоящего поиска ещё нет — всегда «не нашли».

Что сделать.
1. Контроллер в rest-core.
2. Адрес: POST /api/v1/contacts/search
3. Тело: SearchRequest (поля query, city).
4. Если query есть — ответ 200. Поля как у SearchResponse:
status = NOT_FOUND
organizationName = null
normalizedQuery = то, что пришло в query
address = null
phones = пустой список
confidence = null
message = Поиск пока не подключен
5. В ai-core не ходить. В базу не писать. ProxyAI не вызывать.
6. Пустой query не обрабатывай — это задача Дмитрия (task-20).

Как проверить.
Запусти rest-core.
curl -X POST http://localhost:8080/api/v1/contacts/search -H "Content-Type: application/json" -d "{\"query\":\"макдак\",\"city\":\"Москва\"}"
Должно быть 200 и status NOT_FOUND.
В ответе нет rawText. Нет вызова другого сервиса.

Готово когда.
Адрес отвечает.
status = NOT_FOUND.
В ai-core и БД контроллер не ходит.
Есть коммит в rest-core.
