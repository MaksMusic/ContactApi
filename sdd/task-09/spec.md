# task-09 — health rest-core

| | |
|---|---|
| Цикл | 02 (`sdd/cycle-02.md`) |
| Релиз | 02 |
| Модуль | rest-core |
| Исполнитель | Егор |
| Выдача | `releases/release-02/task-09.md` |
| Тип | feature |
| Статус | ready |
| Сложность | 2 / 10 |

## Зачем

Проверить, что rest-core запустился, без поиска.

## Скоуп

`GET /api/v1/health` → 200, JSON `{"status":"UP"}`.

## Вне скоупа

Поиск, Feign, БД, ai-core.

## Контракт

`GET /api/v1/health`. Поле `status`, значение `UP`.

## Граница модуля

Только rest-core.

## DoD

- [ ] адрес отвечает 200
- [ ] поле `status`
- [ ] контроллера search нет

## Как проверить

```text
curl http://localhost:8080/api/v1/health
```
