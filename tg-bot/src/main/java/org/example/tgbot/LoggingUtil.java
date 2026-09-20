package org.example.tgbot;

import lombok.extern.slf4j.Slf4j;

/**
 * Утилитарный класс для структурированного логирования в tg-bot.
 * Все методы префиксируются [tg-bot] для удобной фильтрации в логах.
 */
@Slf4j
public final class LoggingUtil {

    private static final String PREFIX = "[tg-bot]";

    private LoggingUtil() {
        // утилитарный класс
    }

    /**
     * Информационное сообщение.
     */
    public static void info(String message) {
        log.info("{} {}", PREFIX, message);
    }

    /**
     * Информационное сообщение с аргументами.
     */
    public static void info(String message, Object... args) {
        log.info("{} {}", PREFIX, String.format(message, args));
    }

    /**
     * Предупреждение.
     */
    public static void warn(String message) {
        log.warn("{} {}", PREFIX, message);
    }

    /**
     * Предупреждение с аргументами.
     */
    public static void warn(String message, Object... args) {
        log.warn("{} {}", PREFIX, String.format(message, args));
    }

    /**
     * Ошибка.
     */
    public static void error(String message) {
        log.error("{} {}", PREFIX, message);
    }

    /**
     * Ошибка с аргументами.
     */
    public static void error(String message, Object... args) {
        log.error("{} {}", PREFIX, String.format(message, args));
    }

    /**
     * Ошибка с throwable.
     */
    public static void error(String message, Throwable throwable) {
        log.error("{} {}", PREFIX, message, throwable);
    }
}
