package org.example.tgbot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotProperties botProperties;

    @Override
    public String getBotUsername() {
        return botProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Проверяем, что пришло сообщение с текстом
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        // Получаем данные из сообщения
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        // Логируем входящее сообщение
        log.info("Получено сообщение: {}", text);

        // Формируем ответ
        String answer;
        if (text.equals("/start")) {
            answer = "Бот работает";
        } else {
            answer = "Поиск пока не готов";
        }

        // Отправляем ответ
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText(answer);
            execute(message);
            log.info("Ответ отправлен: {}", answer);
        } catch (Exception e) {
            log.error("Ошибка при отправке: {}", e.getMessage());
        }
    }
}
