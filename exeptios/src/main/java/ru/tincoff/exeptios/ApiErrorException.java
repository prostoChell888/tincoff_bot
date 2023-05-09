package ru.tincoff.exeptios;


/**
 * Ошибка во время исполния
 */
public class ApiErrorException extends RuntimeException {
    public ApiErrorException(final String message) {
        super(message);
    }
}