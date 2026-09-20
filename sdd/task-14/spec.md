# task-14 — порт 8081 и убрать чужие DTO

| | |
|---|---|
| Цикл | 02 |
| Релиз | 02 |
| Модуль | ai-core |
| Исполнитель | Дмитрий @Dimas5696 |
| Выдача | `releases/release-02/task-14.md` |
| Тип | chore |
| Статус | ready |
| Сложность | 3 / 10 |

## Зачем

ai-core на своём порту. Публичные DTO rest-core не должны лежать в ai-core.

## Скоуп

`server.port=8081`. Удалить `src/main/java/restcore/dto` из ai-core.

## Вне скоупа

ProxyAI, extract.

## Контракт

Health без изменений: `GET /internal/v1/health`.

## Граница модуля

Только ai-core.

## DoD

- [ ] порт 8081
- [ ] пакета restcore нет
- [ ] health 200

## Как проверить

```text
curl http://localhost:8081/internal/v1/health
```
