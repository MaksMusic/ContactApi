Сложность: 3 / 10
Релиз: 02
Номер: task-14
Кто: Дмитрий Dimas5696
Модуль: ai-core
Спека: sdd/task-14/spec.md

Зачем.
ai-core должен слушаться на 8081. В модуле лежат чужие классы SearchRequest / AddressDto / SearchResponse — это rest-core, им здесь не место.

Что сделать.
1. В настройках ai-core поставь server.port=8081
2. Удали пакет restcore.dto из ai-core (файлы SearchRequest, AddressDto, SearchResponse в src/main/java/restcore).
3. Extract-классы и health не ломай. Нейросеть не подключай.

Как проверить.
Папки restcore в ai-core больше нет.
Запусти ai-core — порт 8081.
curl http://localhost:8081/internal/v1/health
Ожидание: 200 и status.

Готово когда.
Порт 8081.
Чужих DTO нет.
Health работает.
Есть коммит в ai-core.
