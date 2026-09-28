# Task 22 — SearchResponseMapper

| Поле | Значение |
|---|---|
| Релиз | 03 |
| Модуль | rest-core |
| Кто | Влад |
| Сложность | 4 / 10 |

## Зачем

rest-core получит плоский ответ от ai-core и должен преобразовать его в вложенный JSON для клиента. HTTP и Feign не пишем — только класс-переводчик.

## Что сделать

1. Создать класс `SearchResponseMapper` в пакете `org.example.restcore.mapper` (или рядом с dto).
2. Создать DTO `ExtractResponse` в `org.example.restcore.dto` — плоская копия контракта ai-core (без `model` и `rawText`).
3. Метод маппера:
   - **Вход:** `ExtractResponse` + `String normalizedQuery`
   - **Выход:** `SearchResponse`

## Правила маппинга

| Поле SearchResponse | Источник |
|---|---|
| `status` | копируется из `extract.status` |
| `organizationName` | копируется из `extract.organizationName` |
| `normalizedQuery` | параметр метода (строка), не из extract |
| `address` | новый `AddressDto` из `extract.full`, `extract.country`, `extract.city`, `extract.street`, `extract.building`, `extract.postalCode` |
| `phones` | если `extract.phones` — одна строка и не пустая → список из одного элемента; если пусто или null → пустой список |
| `confidence` | копируется из `extract.confidence` |
| `message` | можно `null` |

**Не класть в SearchResponse:** `rawText`, `model`.

## Как проверить

- Написать unit-тест: `extract` с `full="Москва, Тверская"` → `address.getFull()` возвращает `"Москва, Тверская"`.
- `mvn -f rest-core/pom.xml compile` проходит без ошибок.

## Готово когда

- Класс `SearchResponseMapper` есть.
- Все правила маппинга соблюдены.
- `rawText` и `model` наружу не попадают.
- Есть коммит в `rest-core`.
- Есть unit-тест.
