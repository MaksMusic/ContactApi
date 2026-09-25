Сложность: 4 / 10
Релиз: 03
Номер: task-21
Кто: Андрей
Модуль: ai-core
Спека: sdd/task-21/spec.md

Зачем.
rest-core потом будет звать extract. Сейчас нужен живой адрес с плоским JSON. Нейросеть не подключаем.

Что сделать.
1. Контроллер в ai-core.
2. Адрес: POST /internal/v1/extract
3. Тело: ExtractRequest, одно поле text.
4. Если text есть — ответ 200. ExtractResponse:
status = NOT_FOUND
остальные поля null (phones можно пустую строку)
5. ProxyAI не вызывать. Базы нет. Публичный SearchResponse сюда не класть.
6. Пустой text не обрабатывай — это task-24.

Как проверить.
Запусти ai-core.
curl -X POST http://localhost:8081/internal/v1/extract -H "Content-Type: application/json" -d "{\"text\":\"макдак на тверской москва\"}"
200 и status NOT_FOUND.
Объект плоский: нет вложенного address.

Готово когда.
Адрес отвечает.
Нет вызова ProxyAI.
Есть коммит в ai-core.
