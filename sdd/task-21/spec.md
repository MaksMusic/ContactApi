# task-21 — mock extract ai-core

| | |
|---|---|
| Цикл | 03 (`sdd/cycle-03.md`) |
| Релиз | 03 |
| Модуль | ai-core |
| Исполнитель | Андрей |
| Выдача | `releases/release-03/task-21.md` |
| Тип | feature |
| Статус | ready |
| Сложность | 2 / 10 |

## Зачем

rest-core потом будет звать extract. Сейчас нужен живой адрес с плоским JSON. Нейросеть не подключаем.

## Скоуп

- Контроллер в модуле `ai-core`
- Эндпоинт: `POST /internal/v1/extract`
- Тело запроса: `ExtractRequest` (одно поле `text`)
- Ответ: `200 OK` с `ExtractResponse` — плоский объект, без вложенного `address`
- `status = "NOT_FOUND"`, остальные поля `null` (phones — пустая строка)
- Использовать отдельные DTO `ExtractRequest` / `ExtractResponse` (не публичный `SearchResponse`)

## Вне скоупа

- Вызов ProxyAI
- Вызов нейросети
- Запись в базу данных
- Обработка пустого `text` (это task-24)
- Публичный `SearchResponse`

## Контракт

### Запрос

| Параметр | Значение |
|---|---|
| Метод | `POST` |
| Путь | `/internal/v1/extract` |
| Content-Type | `application/json` |
| Body | `{"text": "макдак на тверской москва"}` |

### Ответ

| Параметр | Значение |
|---|---|
| Статус | `200 OK` |
| Content-Type | `application/json` |
| Body | Плоский `ExtractResponse` (см. ниже) |

### Пример ответа

```json
{
  "status": "NOT_FOUND",
  "organizationName": null,
  "full": null,
  "country": null,
  "city": null,
  "street": null,
  "building": null,
  "postalCode": null,
  "phones": "",
  "confidence": null,
  "model": null,
  "rawText": null
}
```

## Граница модуля

Только `ai-core`. Не вызывать ProxyAI, не писать в базу, не использовать публичный `SearchResponse`.

## Реализация

### Файлы

- DTO запроса: `ai-core/src/main/java/org/example/aicore/dto/ExtractRequest.java`
- DTO ответа: `ai-core/src/main/java/org/example/aicore/dto/ExtractResponse.java`
- Контроллер: `ai-core/src/main/java/org/example/aicore/controller/ExtractController.java`

### Детали

- Контроллер: `@RestController`, `@RequestMapping("/internal/v1")`
- Метод: `@PostMapping("/extract")`
- Принимает `@RequestBody ExtractRequest request`
- Возвращает `ExtractResponse`
- Поля `ExtractResponse` (плоский объект, без вложенного `address`):
  - `status` = `"NOT_FOUND"`
  - `organizationName` = `null`
  - `full` = `null`
  - `country` = `null`
  - `city` = `null`
  - `street` = `null`
  - `building` = `null`
  - `postalCode` = `null`
  - `phones` = `""` (пустая строка)
  - `confidence` = `null`
  - `model` = `null`
  - `rawText` = `null`

### Пример контроллера

```java
package org.example.aicore.controller;

import org.example.aicore.dto.ExtractRequest;
import org.example.aicore.dto.ExtractResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/v1")
public class ExtractController {

    @PostMapping("/extract")
    public ExtractResponse extract(@RequestBody ExtractRequest request) {
        ExtractResponse response = new ExtractResponse();
        response.setStatus("NOT_FOUND");
        response.setOrganizationName(null);
        response.setFull(null);
        response.setCountry(null);
        response.setCity(null);
        response.setStreet(null);
        response.setBuilding(null);
        response.setPostalCode(null);
        response.setPhones("");
        response.setConfidence(null);
        response.setModel(null);
        response.setRawText(null);
        return response;
    }
}
```

## DoD

- [ ] Контроллер создан в правильном пакете `ai-core`
- [ ] `POST /internal/v1/extract` отвечает `200 OK`
- [ ] В ответе `status = "NOT_FOUND"`
- [ ] Все остальные поля `null`, `phones` — пустая строка
- [ ] Ответ плоский: нет вложенного `address`
- [ ] ProxyAI не вызывается
- [ ] В базу контроллер не пишет
- [ ] Используются отдельные DTO `ExtractRequest` / `ExtractResponse`
- [ ] Проект собирается без ошибок

## Как проверить

```bash
curl -X POST http://localhost:8081/internal/v1/extract \
  -H "Content-Type: application/json" \
  -d '{"text":"макдак на тверской москва"}'
```

Ожидаемый ответ:
```json
{
  "status": "NOT_FOUND",
  "organizationName": null,
  "full": null,
  "country": null,
  "city": null,
  "street": null,
  "building": null,
  "postalCode": null,
  "phones": "",
  "confidence": null,
  "model": null,
  "rawText": null
}
```
