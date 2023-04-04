package ru.tinkoff.scrapper.util.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.tinkoff.scrapper.util.validation.anotations.Id;


public class IdValidator implements ConstraintValidator<Id, Long> {
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return  id != null && id > 0;
    }
}
