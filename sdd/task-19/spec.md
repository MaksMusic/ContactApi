# task-19 — mock search rest-core

| | |
|---|---|
| Цикл | 02 (`sdd/cycle-02.md`) |
| Релиз | 02 |
| Модуль | rest-core |
| Исполнитель | — |
| Выдача | `releases/release-02/task-19.md` |
| Тип | feature |
| Статус | ready |
| Сложность | 3 / 10 |

## Зачем

Чтобы клиент мог дернуть поиск и получить понятный JSON. Настоящего поиска ещё нет — всегда «не нашли».

## Скоуп

- Контроллер в модуле `rest-core`
- Эндпоинт: `POST /api/v1/contacts/search`
- Тело запроса: `SearchRequest` (поля `query`, `city`)
- Ответ: `200 OK` с полем `status = NOT_FOUND` и заглушками
- Использовать существующие DTO `SearchRequest` и `SearchResponse`

## Вне скоупа

- Вызов ai-core
- Запись в базу данных
- Вызов ProxyAI
- Обработка пустого `query` (это задача Дмитаса — task-20)
- Реальный поиск

## Контракт

### Запрос

| Параметр | Значение |
|---|---|
| Метод | `POST` |
| Путь | `/api/v1/contacts/search` |
| Content-Type | `application/json` |
| Body | `{"query": "строка", "city": "строка или null"}` |

### Ответ

| Параметр | Значение |
|---|---|
| Статус | `200 OK` |
| Content-Type | `application/json` |
| Body | `{"status":"NOT_FOUND","organizationName":null,"normalizedQuery":"<query из запроса>","address":null,"phones":[],"confidence":null,"message":"Поиск пока не подключен"}` |

### Пример ответа

```json
{
  "status": "NOT_FOUND",
  "organizationName": null,
  "normalizedQuery": "Яндекс",
  "address": null,
  "phones": [],
  "confidence": null,
  "message": "Поиск пока не подключен"
}
```

## Граница модуля

Только `rest-core`. Не вызывать ai-core, не писать в базу, не использовать ProxyAI.

## Реализация

### Файлы

- Контроллер: `rest-core/src/main/java/org/example/restcore/controller/SearchController.java`

### Детали

- Контроллер: `@RestController`, `@RequestMapping("/api/v1/contacts")`
- Метод: `@PostMapping("/search")`
- Принимает `@RequestBody SearchRequest request`
- Возвращает `SearchResponse`
- Заполнить поля:
  - `status` = `"NOT_FOUND"`
  - `organizationName` = `null`
  - `normalizedQuery` = `request.getQuery()`
  - `address` = `null`
  - `phones` = `List.of()` (пустой список)
  - `confidence` = `null`
  - `message` = `"Поиск пока не подключен"`

### Пример контроллера

```java
package org.example.restcore.controller;

import org.example.restcore.dto.SearchRequest;
import org.example.restcore.dto.SearchResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class SearchController {

    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest request) {
        SearchResponse response = new SearchResponse();
        response.setStatus("NOT_FOUND");
        response.setOrganizationName(null);
        response.setNormalizedQuery(request.getQuery());
        response.setAddress(null);
        response.setPhones(List.of());
        response.setConfidence(null);
        response.setMessage("Поиск пока не подключен");
        return response;
    }
}
```

## DoD

- [ ] Контроллер создан в правильном пакете
- [ ] `POST /api/v1/contacts/search` отвечает `200 OK`
- [ ] В ответе `status = "NOT_FOUND"`
- [ ] `normalizedQuery` = то, что пришло в `query` из запроса
- [ ] `phones` — пустой список
- [ ] `message = "Поиск пока не подключен"`
- [ ] В ai-core контроллер не ходит
- [ ] В базу контроллер не пишет
- [ ] ProxyAI не вызывается
- [ ] Проект собирается без ошибок

## Как проверить

```bash
curl -X POST http://localhost:8080/api/v1/contacts/search \
  -H "Content-Type: application/json" \
  -d '{"query":"Яндекс","city":"Москва"}'
```

Ожидаемый ответ:
```json
{
  "status": "NOT_FOUND",
  "organizationName": null,
  "normalizedQuery": "Яндекс",
  "address": null,
  "phones": [],
  "confidence": null,
  "message": "Поиск пока не подключен"
}
```
