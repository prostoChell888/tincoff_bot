package ru.tincoff.exeptios;

public class ApiErrorException extends RuntimeException
{
    public ApiErrorException(String message)
    {
        super(message);
    }
}