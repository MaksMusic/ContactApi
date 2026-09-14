# Контракты (черновик для задач)

Статус: **черновик**. Меняем здесь, потом задачами синхронизируем код. Пока эндпоинтов в коде нет.

Общие правила:

- JSON, UTF-8, `application/json`;
- имена полей — camelCase (удобно для Jackson POJO);
- телефоны — E.164, если удалось нормализовать (`+7...`);
- пустой результат — не 500, а 200 с `status=NOT_FOUND` **или** 404 — решение зафиксировать в первой задаче на rest-core (пока в черновике: 200 + статус).

---

## Публичный API: клиент → REST-CORE

Предлагаемый эндпоинт: `POST /api/v1/contacts/search`

### Request

```json
{
  "query": "макдак на тверской",
  "city": "Москва"
}
```

| Поле | Обязательное | Смысл |
|---|---|---|
| query | да | Сырое название / фраза пользователя |
| city | нет | Подсказка географии |

### Response 200

```json
{
  "status": "FOUND",
  "organizationName": "Макдоналдс",
  "normalizedQuery": "макдак на тверской",
  "address": {
    "full": "Россия, Москва, ул. Тверская, д. 1",
    "country": "RU",
    "city": "Москва",
    "street": "ул. Тверская",
    "building": "1",
    "postalCode": null
  },
  "phones": ["+74951234567"],
  "confidence": 0.86,
  "message": null
}
```

| status | Когда |
|---|---|
| FOUND | Есть адрес и/или телефоны |
| NOT_FOUND | Модель/поиск не нашли |
| AMBIGUOUS | Несколько кандидатов — уточнить (фаза 2) |
| ERROR | Сбой зависимости, клиенту — безопасное сообщение |

Ошибки валидации: `400` с тем же error-wrapper (отдельная задача rest-core).

---

## Внутренний API: REST-CORE → AI-CORE

Предлагаемый эндпоинт: `POST /internal/v1/extract`

Клиенты снаружи этот путь не видят.

### Request

Один параметр. Название, город и любые уточнения — всё в `text`.

```json
{
  "text": "макдак на тверской москва"
}
```

### Response

Один плоский объект, без вложенного `address`.

```json
{
  "status": "FOUND",
  "organizationName": "Макдоналдс",
  "full": "Россия, Москва, ул. Тверская, д. 1",
  "country": "RU",
  "city": "Москва",
  "street": "ул. Тверская",
  "building": "1",
  "postalCode": null,
  "phones": "+74951234567",
  "confidence": 0.86,
  "model": "proxyai-default",
  "rawText": null
}
```

`rawText` — только внутреннее, в публичный JSON rest-core не кладёт.

---

## Клиент TG-BOT → REST-CORE

Бот использует **публичный** контракт `POST /api/v1/contacts/search`. Отдельного bot-only API нет, пока не появится причина (auth, rate-limit, correlation id).

Сообщение пользователю собирается из `organizationName`, `address.full`, `phones`.

---

## AI-CORE → ProxyAI

Протокол: OpenAI-compatible Chat Completions (Spring AI OpenAI).

Конфиг (имена ориентир, не коммитить секреты):

```properties
spring.ai.openai.base-url=${PROXYAI_BASE_URL}
spring.ai.openai.api-key=${PROXYAI_API_KEY}
spring.ai.openai.chat.options.model=${PROXYAI_MODEL}
```

Задача ai-core: structured JSON из модели, парсинг, валидация схемы, fallback если модель вернула прозу.

---

## Корреляция

Каждый запрос: `X-Request-Id` (генерит rest-core, если клиент не прислал). Прокидывать в ai-core и логи. Нужно для QA и разбора инцидентов.

## Что сознательно не в контракте v1

- ИНН/ОГРН;
- сайт, соцсети, часы работы;
- список всех филиалов;
- биллинг и API-ключи внешних клиентов.

Это отдельные эпики, не смешивать с первым срезом «название → адрес + телефоны».
