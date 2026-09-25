# Релиз 01 — каркас (закрыт)

Старые задачи из `tasks/` лежат **здесь**. Новые — в [релизе 02](../release-02/README.md).

| Номер | Кто | Файл |
|---|---|---|
| task-01 | @Mipowka | [task-01.md](task-01.md) — снята |
| task-02 | @Dimas5696 | [task-02.md](task-02.md) — health ai-core |
| task-03 | Дмитрий | [task-03.md](task-03.md) — ContactSearch |
| task-04 | Андрей | [task-04.md](task-04.md) — Extract DTO |
| task-05 | Влад | [task-05.md](task-05.md) — публичные DTO |
| task-06 | Олег | [task-06.md](task-06.md) — BotProperties |
| task-07 | Наталья | [task-07.md](task-07.md) — qa чек-лист |
| task-08 | Дмитрий Заварин | [task-08.md](task-08.md) — ContactSearch + repository |
| task-0001 | Максим | [task-0001.md](task-0001.md) — MessageValidator |

## Что уже есть в коде

| Модуль | Есть |
|---|---|
| rest-core | `RequestIdFilter`, `ContactSearch` + repository, `SearchRequest`, `AddressDto`, `SearchResponse` |
| ai-core | `GET /internal/v1/health`, `ExtractRequest`, `ExtractResponse` |
| tg-bot | `BotProperties` из env, бот отвечает на `/start`, `MessageValidator` |
| qa | папки ещё нет — уходит в релиз 02 |
