package ru.tincoff.exeptios;


/**
 * Ошибка при некоректном запросе
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(final String message) {
        super(message);
    }
}