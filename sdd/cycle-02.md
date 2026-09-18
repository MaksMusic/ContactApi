# Цикл 02 / Релиз 02 — сервисы живые, контракт чистый

| | |
|---|---|
| Статус | `in_progress` |
| Релиз | [02](../releases/release-02/README.md) |
| Старт | 2026-09-18 |
| Владелец | Максим |
| Архитектура | Дмитрий @Mipowka |

Цикл 01 (релиз 01) в коде закрыт по каркасу. Поиска для пользователя всё ещё нет.

## Зачем

Три сервиса уже поднимаются. В данных есть лишние поля, нет health у rest-core, нет папки `qa/`, порты не зафиксированы. Релиз 02 это чинит маленькими задачами. Search и extract **не открываем**.

## Рабочая правда контракта (пока @Mipowka не переписал `contracts.md`)

- пустой поиск позже: `200` + `status=NOT_FOUND`
- телефоны наружу: массив
- телефоны в ai-core: одна строка
- порты: 8080 rest-core, 8081 ai-core, 8082 tg-bot

## В скоупе

- health rest-core
- порты 8080 и 8081
- починить `ExtractRequest` / `ExtractResponse` под контракт
- enum статусов, простой error-класс
- понятный `/start` у бота
- убрать чужие DTO из ai-core
- папка `qa/`
- пример env без секретов
- заморозка четырёх решений (не код)

## Вне скоупа

- `POST /api/v1/contacts/search`
- `POST /internal/v1/extract`
- Feign, ProxyAI, поиск в боте, Docker, ИНН, филиалы

## Доска

| Задача | Кто | Модуль | Сложность | Файл |
|---|---|---|---|---|
| [task-09](../releases/release-02/task-09.md) | Егор | rest-core | 2 | health |
| [task-10](../releases/release-02/task-10.md) | Дмитрий | rest-core | 3 | порт 8080 |
| [task-11](../releases/release-02/task-11.md) | Андрей | ai-core | 3 | поля extract |
| [task-12](../releases/release-02/task-12.md) | Влад | rest-core | 2 | enum статусов |
| [task-13](../releases/release-02/task-13.md) | Олег | tg-bot | 2 | текст `/start` |
| [task-14](../releases/release-02/task-14.md) | @Dimas5696 | ai-core | 3 | порт 8081 + убрать лишние DTO |
| [task-15](../releases/release-02/task-15.md) | Наталья | qa | 2 | папка qa |
| [task-16](../releases/release-02/task-16.md) | Дмитрий Заварин | rest-core | 3 | класс ошибки |
| [task-17](../releases/release-02/task-17.md) | Максим | devops | 3 | env.example |
| [task-18](../releases/release-02/task-18.md) | @Mipowka | архитектура | 2 | заморозка контракта |

## DoD релиза 02

- [ ] `GET /api/v1/health` в rest-core → 200, поле `status`
- [ ] rest-core слушает 8080, ai-core 8081
- [ ] у Extract-классов нет поля `id`, `confidence` — число
- [ ] в ai-core нет пакета `restcore.dto`
- [ ] `/start` пишет, что поиск ещё не готов
- [ ] есть `qa/` и `env.example`
- [ ] четыре решения записаны в `contracts.md`
- [ ] search и extract эндпоинтов нет
