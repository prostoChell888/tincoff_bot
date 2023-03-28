package ru.tinkoff.scrapper.util.validation.anotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.tinkoff.scrapper.util.validation.validators.ListOfIntValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ListOfIntValidator.class)
@Documented
public @interface ListOfIds {
    String message() default "Elements in List of Ids should not be empty ad bigger than zero";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
