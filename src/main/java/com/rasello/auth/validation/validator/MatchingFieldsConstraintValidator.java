package com.rasello.auth.validation.validator;

import com.rasello.auth.validation.annotation.MatchingFields;
import com.thoughtworks.xstream.InitializationException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class MatchingFieldsConstraintValidator implements ConstraintValidator<MatchingFields, Object> {
    private List<String> fields;

    @Override
    public void initialize(MatchingFields constraintAnnotation) {
        fields = Arrays.asList(constraintAnnotation.fields());
        if (fields.size() < 2)
            throw new InitializationException("Fields size cannot less than 2");
    }

    @NotBlank
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Field firstField = value.getClass().getField(fields.get(0));
            var value1 = firstField.get(value);
            for (int i = 1; i < fields.size(); i++) {
                var field = value.getClass().getField(fields.get(i));
                var value2 = field.get(value);
                if (value1 == null && value2 == null)
                    continue;
                if (value1 == null || value2 == null)
                    return false;

                return value1.equals(value2);

            }
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
