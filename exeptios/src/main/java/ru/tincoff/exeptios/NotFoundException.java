package ru.tincoff.exeptios;

    public class NotFoundException extends RuntimeException{
        public NotFoundException(String message) {
            super(message);
        }
    }