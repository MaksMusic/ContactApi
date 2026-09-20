# task-12 — enum SearchStatus

| | |
|---|---|
| Цикл | 02 |
| Релиз | 02 |
| Модуль | rest-core |
| Исполнитель | Влад |
| Выдача | `releases/release-02/task-12.md` |
| Тип | chore |
| Статус | ready |
| Сложность | 2 / 10 |

## Зачем

Один набор статусов без опечаток в строках.

## Скоуп

Enum `SearchStatus`: `FOUND`, `NOT_FOUND`, `ERROR`.

## Вне скоупа

`AMBIGUOUS`, контроллер поиска.

## Контракт

Без API.

## Граница модуля

Только rest-core.

## DoD

- [ ] три значения, не больше

## Как проверить

```text
mvn -f rest-core/pom.xml compile
```
