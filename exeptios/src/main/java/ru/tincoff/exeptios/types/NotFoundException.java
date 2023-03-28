package ru.tincoff.exeptios.types;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}