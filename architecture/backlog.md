# Бэклог

Статусы: `idea` → `ready` → `in_progress` → `done`.

Пока это **семена** для постановки, не выданные задачи. Когда пишем задачу разработчику — заполняем шаблон из `task-guide.md` и меняем статус на `ready`.

## EPIC-1. Название → адрес + телефоны

Сквозной сценарий схемы: user request → rest-core → ai-core → ProxyAI → ответ клиенту.

| ID | Модуль | Кому | Статус | Суть |
|---|---|---|---|---|
| CA-DEVOPS-001 | devops | Максим | idea | Docker/compose или run-скрипты трёх сервисов, порты 8080/8081/8082, env ProxyAI и Postgres |
| CA-ARCH-001 | — | @Mipowka + Максим | idea | Зафиксировать v0 контрактов из `contracts.md` (статус NOT_FOUND vs 404) |
| CA-AI-001 | ai-core | @Dimas5696 / Андрей | idea | Конфиг Spring AI на ProxyAI через env, без секретов в git |
| CA-AI-002 | ai-core | @Dimas5696 / Андрей | idea | Internal `POST /internal/v1/extract`, prompt, structured JSON, таймаут/ошибка ProxyAI |
| CA-REST-001 | rest-core | Дмитрий / Влад / Андрей | idea | Public `POST /api/v1/contacts/search`, валидация `query`, OpenAPI |
| CA-REST-002 | rest-core | Дмитрий / Влад / Андрей | idea | Feign-клиент на ai-core, прокидка `X-Request-Id`, маппинг во внешний JSON без `rawText` |
| CA-REST-003 | rest-core | Дмитрий / Влад / Андрей | idea | PostgreSQL: минимальная модель (запрос/ответ или кэш) — уточнить с архитектором |
| CA-TG-001 | tg-bot | @Dimas5696 / Олег | idea | Подключить Telegram-библиотеку, токен из env |
| CA-TG-002 | tg-bot | Олег | idea | Принять текст названия → rest-core → ответить адресом и телефонами |
| CA-QA-001 | qa | Наталья | idea | Контрактные тесты public API + mock ai-core |
| CA-QA-002 | qa | Наталья | idea | e2e: query с опечаткой → FOUND с телефоном/адресом |

## Вне EPIC-1 (не брать, пока нет ответа клиенту)

- AMBIGUOUS / выбор филиала;
- ИНН, сайт, часы работы;
- API-ключи для внешних web/mobile;
- биллинг;
- корневой модуль `ContactApi` как runtime (скорее удалить или превратить в parent POM — отдельный chore после согласования с @Mipowka).

## Журнал выдачи

Когда задача уходит разработчику, дописываем строку:

| Дата | Номер | Кому | Ссылка / формулировка |
|---|---|---|---|
| 2026-09-04 | task-01 | @Mipowka | [tasks/Дмитрий-Mipowka.md](../tasks/Дмитрий-Mipowka.md) — пакеты + логи + requestId |
| 2026-09-04 | task-02 | @Dimas5696 | [tasks/Дмитрий-Dimas5696.md](../tasks/Дмитрий-Dimas5696.md) — логи + health |
| 2026-09-04 | task-03 | Дмитрий | [tasks/Дмитрий.md](../tasks/Дмитрий.md) — entity ContactSearch |
| 2026-09-04 | task-04 | Андрей | [tasks/Андрей.md](../tasks/Андрей.md) — DTO extract |
| 2026-09-04 | task-05 | Влад | [tasks/Влад.md](../tasks/Влад.md) — публичные DTO |
| 2026-09-04 | task-06 | Олег | [tasks/Олег.md](../tasks/Олег.md) — конфиг бота + логи |
| 2026-09-04 | task-07 | Наталья | [tasks/Наталья.md](../tasks/Наталья.md) — qa/ чек-лист + .http |
