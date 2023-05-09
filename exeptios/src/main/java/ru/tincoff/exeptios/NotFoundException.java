package ru.tincoff.exeptios;


/**
 * Ошибка при не нахождении нужного элемента
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }
}