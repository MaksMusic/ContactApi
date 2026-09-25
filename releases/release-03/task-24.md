Сложность: 3 / 10
Релиз: 03
Номер: task-24
Кто: Дмитрий Dimas5696
Модуль: ai-core
Спека: sdd/task-24/spec.md

Зачем.
Пустой text в extract — это ошибка входа, не «не нашли».

Что сделать.
1. На POST /internal/v1/extract: если text нет или пустой — ответ 400.
2. В теле можно простой JSON: поле message. Либо тот же смысл, что ErrorResponse, но не копируй публичный API rest-core без нужды. Достаточно: {"message":"Укажите text"}
3. ProxyAI не вызывать.
4. Когда text есть — не ломай заглушку Андрея (200 и NOT_FOUND).

Как проверить.
curl -X POST http://localhost:8081/internal/v1/extract -H "Content-Type: application/json" -d "{}"
Ожидание: 400.

curl с text — 200 и NOT_FOUND.

Готово когда.
Пустой text даёт 400.
Нейросети нет.
Есть коммит в ai-core.
