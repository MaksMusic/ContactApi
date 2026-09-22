Сложность: 4 / 10
Релиз: 03
Номер: task-22
Кто: Влад
Модуль: rest-core
Спека: sdd/task-22/spec.md

Зачем.
Потом rest-core получит плоский ответ из ai-core и должен отдать вложенный JSON клиенту. HTTP и Feign не пишем — только класс-переводчик.

Что сделать.
1. Класс SearchResponseMapper в rest-core (пакет на своё усмотрение, лучше рядом с dto или service).
2. Метод: на вход ExtractResponse (те же поля, что в контракте ai-core), на выход SearchResponse.
3. Правила:
status копируешь
organizationName копируешь
normalizedQuery — параметр метода (строка), не из extract
address — новый AddressDto из full, country, city, street, building, postalCode
phones — если в extract одна строка и она не пустая, положи её в список из одного элемента; если пусто — пустой список
confidence копируешь
message можно null
rawText и model в SearchResponse не класть
4. Контроллер и Feign не делать. Если Егору удобно — он может вызвать твой маппер позже, не обязательно в этом коммите.

Как проверить.
Напиши простой тест или main-проверку в голове: extract с full=«Москва, Тверская» даёт address.full такое же.
mvn -f rest-core/pom.xml compile

Готово когда.
Класс есть, правила выше соблюдены.
rawText наружу не попадает.
Есть коммит в rest-core.
