package ru.tinkoff.bot.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ListOfIntValidator implements ConstraintValidator<ListOfIds, List<Long>> {
    @Override
    public boolean isValid(List<Long> list, ConstraintValidatorContext constraintValidatorContext) {
        return  !list.contains(null)
                && list.stream().allMatch(el -> el > 0);
    }
}
