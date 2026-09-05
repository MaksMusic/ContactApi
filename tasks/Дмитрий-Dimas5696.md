task-02. Дмитрий Dimas5696.
Модуль: ai-core.

Зачем.
Чтобы проверить, что сервис ai-core запускается и отвечает. Это простой первый шаг, нейросеть пока не подключаем.

Что сделать.
Настроить логи: time, level, logger, message.
Класс HealthController.
Адрес: GET /internal/v1/health.
Класс ответа HealthResponse.
Поле HealthResponse: status.
Значение status: UP.
При вызове написать в лог одну строку.
Нейросеть, промпты и поиск контактов не делать.

Готово когда.
Класс HealthController и HealthResponse есть.
Адрес health открывается и отвечает 200.
В ответе поле status.
Приложение запускается.
Есть коммит.
