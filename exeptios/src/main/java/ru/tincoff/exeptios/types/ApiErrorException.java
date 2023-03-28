package ru.tincoff.exeptios.types;

public class ApiErrorException extends RuntimeException
{
    public ApiErrorException(String message)
    {
        super(message);
    }
}