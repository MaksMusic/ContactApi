# Бэклог

Статусы: `idea` → `ready` → `in_progress` → `done`.

Семена на релизы 04+. Выданное сейчас — [releases/release-03](../releases/release-03/README.md).

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
| 2026-09-04 | task-01 | @Mipowka | [releases/release-01/task-01.md](../releases/release-01/task-01.md) — снята |
| 2026-09-04 | task-02 | @Dimas5696 | [releases/release-01/task-02.md](../releases/release-01/task-02.md) — логи + health |
| 2026-09-04 | task-03 | Дмитрий | [releases/release-01/task-03.md](../releases/release-01/task-03.md) — entity ContactSearch |
| 2026-09-04 | task-04 | Андрей | [releases/release-01/task-04.md](../releases/release-01/task-04.md) — DTO extract |
| 2026-09-04 | task-05 | Влад | [releases/release-01/task-05.md](../releases/release-01/task-05.md) — публичные DTO |
| 2026-09-04 | task-06 | Олег | [releases/release-01/task-06.md](../releases/release-01/task-06.md) — конфиг бота + логи |
| 2026-09-04 | task-07 | Наталья | [releases/release-01/task-07.md](../releases/release-01/task-07.md) — qa/ чек-лист |
| 2026-09-04 | task-08 | Дмитрий Заварин | [releases/release-01/task-08.md](../releases/release-01/task-08.md) — ContactSearch |
| 2026-09-18 | task-09 | Егор | [releases/release-02/task-09.md](../releases/release-02/task-09.md) — health rest-core |
| 2026-09-18 | task-10 | Дмитрий | порт 8080 |
| 2026-09-18 | task-11 | Андрей | поля Extract |
| 2026-09-18 | task-12 | Влад | SearchStatus |
| 2026-09-18 | task-13 | Олег | текст /start |
| 2026-09-18 | task-14 | @Dimas5696 | порт 8081, убрать чужие DTO |
| 2026-09-18 | task-15 | Наталья | папка qa |
| 2026-09-18 | task-16 | Дмитрий Заварин | ErrorResponse |
| 2026-09-18 | task-17 | Максим | env.example |
| 2026-09-18 | task-18 | @Mipowka | заморозка контракта |
| 2026-09-22 | task-19 | Егор | [releases/release-03/task-19.md](../releases/release-03/task-19.md) — search-заглушка |
| 2026-09-22 | task-20 | Дмитрий | 400 без query |
| 2026-09-22 | task-21 | Андрей | extract-заглушка |
| 2026-09-22 | task-22 | Влад | маппер extract → search |
| 2026-09-22 | task-23 | Олег | порт 8082 |
| 2026-09-22 | task-24 | @Dimas5696 | 400 без text |
| 2026-09-22 | task-25 | Наталья | qa/search.http |
| 2026-09-22 | task-26 | Дмитрий Заварин | не писать в БД |
| 2026-09-22 | task-27 | Максим | RUN.md |
| 2026-09-22 | task-28 | @Mipowka | ревью заглушек |
