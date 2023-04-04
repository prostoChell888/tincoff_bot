package ru.tinkoff.scrapper.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tincoff.exeptios.BadRequestException;
import ru.tincoff.exeptios.NotFoundException;
import ru.tinkoff.scrapper.dto.responce.ApiErrorResponse;


import java.util.Arrays;

@RestControllerAdvice
public class ExceptionApiHandler {



    @ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse onMethodArgumentNotValidException(RuntimeException e) {
        return new ApiErrorResponse(
                "Not valid value request",
                "400",
                e.getClass().getSimpleName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString)
                        .toArray(String[]::new)
        );
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse apiErrorExceptionHandler(NumberFormatException ex) {
        return new ApiErrorResponse(
                "Not found",
                "404",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toArray(String[]::new));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse badRequestExceptionHandler(NumberFormatException ex) {
        return new ApiErrorResponse(
                "Bad Request",
                "400",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toArray(String[]::new));

    }
}
