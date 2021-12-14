package com.rasello.auth.validation.validator;

import com.rasello.auth.validation.annotation.Unique;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class UniqueConstraintValidator implements ConstraintValidator<Unique, Object> {
    private List<String> fields;
    private Class<?> entityClass;
    private final EntityManager entityManager;

    public UniqueConstraintValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        fields = Arrays.asList(constraintAnnotation.fields());
        entityClass = constraintAnnotation.entity();
        if (fields.size() <= 0)
            throw new IllegalArgumentException("Fields cannot be empty");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (fields.isEmpty())
            throw new IllegalArgumentException("No fields supplied in validation");
        var queryString = String.format("select count(d) from %s d", entityClass.getSimpleName());
        var whereClauseBuilder = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {

            if (i > 0)
                whereClauseBuilder.append(" and ");
            whereClauseBuilder.append("d.")
                    .append(fields.get(i))
                    .append(" = ")
                    .append(":field")
                    .append(i);
        }
        var query = entityManager.
                createQuery(String.format("%s where %s", queryString, whereClauseBuilder.toString()));

        for (int i = 0; i < fields.size(); i++) {
            try {
                var field = value.getClass().getDeclaredField(fields.get(i));
                field.setAccessible(true);
                query.setParameter("field" + i, field.get(value));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalStateException(e.getMessage());
            }
        }
        var count = ((Long) query.getSingleResult()).intValue();
        return count <= 0;
    }
}