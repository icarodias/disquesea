package com.disquesea.disqueseaapi.domain.validations;

import com.disquesea.disqueseaapi.domain.validations.annotations.DateConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateConstraint, String> {
    private final static String DATE_PATTERN = "^(" +
            "((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)\\d{2}))|" +
            "((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))|" +
            "((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|" +
            "(29\\/02\\/((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";

    @Override
    public void initialize(DateConstraint dateConstraint) {
    }

    @Override
    public boolean isValid(String dateField, ConstraintValidatorContext cxt) {
        return (dateField == null) || (dateField != null && dateField.matches(DATE_PATTERN));
    }
}
