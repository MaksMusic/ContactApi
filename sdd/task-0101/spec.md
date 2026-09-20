# task-0101 — LoggingUtil + фикс logging level

| | |
|---|---|
| Цикл | 02 |
| Релиз | 02 |
| Модуль | tg-bot |
| Исполнитель | Олег |
| Выдача | `tasks/Олег.md` |
| Тип | chore |
| Сложность | 2 / 10 |

## Зачем

Единый класс для структурированного логирования в tg-bot. Сейчас `@Slf4j` размазан по каждому классу — нет общего формата, нет контекста бота в логах.

## Скоуп

- Новый класс `LoggingUtil` в пакете `org.example.tgbot`.
- Фикс `logging.level` в `application.properties` (`com.example.tgbot` → `org.example.tgbot`).

## Вне скоупа

- Вынос `@Slf4j` из существующих классов (делается позже, отдельной задачей).
- Logback XML, ELK, распределённые трейсы.

## Контракт

Без API. Только внутренний утилитарный класс.

## Граница модуля

Только `tg-bot/src/main/java/org/example/tgbot/LoggingUtil.java` и `tg-bot/src/main/resources/application.properties`.

## DoD

- [ ] `LoggingUtil` есть в пакете `org.example.tgbot`
- [ ] `application.properties` указывает `logging.level.org.example.tgbot=DEBUG`
- [ ] `mvn clean compile` проходит без ошибок

## Как проверить

Открыть `LoggingUtil.java` — класс публичный, содержит статические методы `info`, `error`, `warn` с префиксом `[tg-bot]`. Открыть `application.properties` — уровень для `org.example.tgbot`.
