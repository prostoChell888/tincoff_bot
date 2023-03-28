package ru.tinkoff.scrapper.util.validation.anotations;

import jakarta.validation.Payload;

public @interface Id {
    String message() default "Id in List of Ids should not be null and bigger than 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
