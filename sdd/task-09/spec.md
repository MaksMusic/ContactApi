# task-09 — health rest-core

| | |
|---|---|
| Цикл | 02 (`sdd/cycle-02.md`) |
| Релиз | 02 |
| Модуль | rest-core |
| Исполнитель | Егор |
| Выдача | `releases/release-02/task-09.md` |
| Тип | feature |
| Статус | ready |
| Сложность | 2 / 10 |

## Зачем

Чтобы быстро понять, что rest-core запустился. У ai-core такая проверка уже есть. У rest-core её нет.

## Скоуп

- Создать контроллер в модуле `rest-core`
- Эндпоинт: `GET /api/v1/health`
- Ответ: `200 OK`, JSON `{"status":"UP"}`

## Вне скоупа

- Поиск организаций
- База данных
- ai-core
- Любые другие эндпоинты

## Контракт

| Параметр | Значение |
|---|---|
| Метод | `GET` |
| Путь | `/api/v1/health` |
| Статус | `200 OK` |
| Content-Type | `application/json` |
| Body | `{"status":"UP"}` |

## Граница модуля

Только `rest-core`. Не трогать `ai-core`, `tg-bot`, базу данных, поиск.

## Реализация

### Файлы

- Новый контроллер: `rest-core/src/main/java/org/example/restcore/controller/HealthController.java`
- Новый DTO: `rest-core/src/main/java/org/example/restcore/dto/HealthResponse.java`

### Детали

- Контроллер: `@RestController`, `@RequestMapping("/api/v1")`
- Метод: `@GetMapping("/health")`
- Возвращает `HealthResponse` с полем `String status = "UP"`
- Lombok: `@RequiredArgsConstructor` не нужен, простой POJO

### Пример DTO

```java
package org.example.restcore.dto;

public class HealthResponse {
    private String status;

    public HealthResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
```

### Пример контроллера

```java
package org.example.restcore.controller;

import org.example.restcore.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse("UP");
    }
}
```

## DoD

- [ ] Контроллер создан в правильном пакете
- [ ] `GET /api/v1/health` отвечает `200 OK`
- [ ] В JSON есть поле `status` со значением `UP`
- [ ] Формат: `{"status":"UP"}`
- [ ] Не затронуты `ai-core`, `tg-bot`, база данных, поиск
- [ ] Проект собирается без ошибок

## Как проверить

```bash
curl http://localhost:8080/api/v1/health
```

Ожидаемый ответ:
```json
{"status":"UP"}
```
