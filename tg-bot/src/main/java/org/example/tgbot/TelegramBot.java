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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            log.info("Получено сообщение от {}: {}", chatId, text);

            String answer;
            if ("/start".equals(text)) {
                answer = "Бот работает! 🚀";
            } else {
                answer = "Поиск пока не готов";
            }

            sendMessage(chatId.toString(), answer);
        }
    }

    private void sendMessage(String chatId, String text) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            execute(message);
            log.info("Ответ отправлен: {}", text);
        } catch (Exception e) {
            log.error("Ошибка при отправке сообщения: {}", e.getMessage());
        }
    }
}