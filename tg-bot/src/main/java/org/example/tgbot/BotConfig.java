package org.example.tgbot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class BotConfig {

    private final TelegramBot telegramBot;

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        try {
            log.info("Регистрация Telegram бота: @{}", telegramBot.getBotUsername());
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
            log.info("✅ Telegram bot успешно зарегистрирован: @{}", telegramBot.getBotUsername());
        } catch (TelegramApiException e) {
            log.error("❌ Ошибка регистрации Telegram бота: {}", e.getMessage(), e);
        }
    }
}