task-01. Дмитрий Mipowka.
Модуль: rest-core.

Зачем.
Чтобы в rest-core сразу был порядок в папках и нормальные логи. Потом по логам можно будет понять, какой запрос сломался.

Что сделать.
Создать пустые пакеты: controller, service, repository, dto, entity, config.
Класс RequestIdFilter.
Поле в логе: requestId.
Заголовок запроса: X-Request-Id.
Если заголовка нет, фильтр сам создаёт requestId и пишет его в лог.
В логе каждая строка: time, level, requestId, logger, message.
Контроллеры и поиск организаций не писать.

Готово когда.
Папки есть.
Класс RequestIdFilter есть.
Приложение запускается.
В логах виден requestId.
Есть первый коммит.
