# task-11 — поля Extract как в контракте

| | |
|---|---|
| Цикл | 02 |
| Релиз | 02 |
| Модуль | ai-core |
| Исполнитель | Андрей |
| Выдача | `releases/release-02/task-11.md` |
| Тип | chore |
| Статус | ready |
| Сложность | 3 / 10 |

## Зачем

Убрать лишний `id` и сделать `confidence` числом.

## Скоуп

`ExtractRequest`: только `text`.
`ExtractResponse`: `status`, `organizationName`, `full`, `country`, `city`, `street`, `building`, `postalCode`, `phones`, `confidence` (Double), `model`, `rawText`.

## Вне скоупа

Эндпоинт extract, ProxyAI.

## Контракт

Только форма данных, эндпоинт не поднимать.

## Граница модуля

Только ai-core. Публичные DTO rest-core не копировать сюда.

## DoD

- [ ] нет поля `id`
- [ ] `confidence` — Double
- [ ] модуль собирается

## Как проверить

```text
mvn -f ai-core/pom.xml compile
```
