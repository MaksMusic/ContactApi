Сложность: 3 / 10
Релиз: 02
Номер: task-11
Кто: Андрей
Модуль: ai-core
Спека: sdd/task-11/spec.md

Зачем.
rest-core и ai-core должны говорить одними полями. Сейчас в Extract-классах лишнее поле id, а confidence написан как текст. Так нельзя стыковать JSON.

Что сделать.
1. Класс ExtractRequest. Оставь только поле text. Поле id удали.
2. Класс ExtractResponse. Поля только такие:
status, organizationName, full, country, city, street, building, postalCode, phones, confidence, model, rawText
3. Поле id удали.
4. confidence сделай числом (Double), не String.
5. Контроллер extract и нейросеть не делать.

Как проверить.
Открой два класса в IDEA.
У ExtractRequest одно поле: text.
У ExtractResponse нет id.
confidence — Double.
Собери модуль: mvn -f ai-core/pom.xml compile

Готово когда.
Поля как в списке выше.
Проект собирается.
Есть коммит в ai-core.
