package ru.tinkoff.scrapper.util.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.tinkoff.scrapper.util.validation.anotations.ListOfIds;

import java.util.List;

public class ListOfIntValidator implements ConstraintValidator<ListOfIds, List<Integer>> {
    @Override
    public boolean isValid(List<Integer> list, ConstraintValidatorContext constraintValidatorContext) {
        return  !list.contains(null)
                && list.stream().allMatch(el -> el > 0);
    }
}
