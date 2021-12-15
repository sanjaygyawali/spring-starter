package com.rasello.auth.validation.validator;

import com.rasello.auth.validation.annotation.Unique;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UniqueConstraintValidator implements ConstraintValidator<Unique, Object> {
    private List<String> entityFields;
    private List<String> valueFields;
    private Class<?> entityClass;
    private final EntityManager entityManager;

    public UniqueConstraintValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        entityFields = Arrays.asList(constraintAnnotation.entityFields());
        valueFields = Arrays.asList(constraintAnnotation.valueFields());
        entityClass = constraintAnnotation.entity();
        if (entityFields.size() <= 0)
            throw new IllegalArgumentException("Fields cannot be empty");
        if (entityFields.size() != valueFields.size())
            throw new IllegalArgumentException("Entity fields and value fields should be of same length");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (valueFields.isEmpty())
            throw new IllegalArgumentException("No fields supplied in validation");
        var queryString = String.format("select count(d) from %s d", entityClass.getSimpleName());
        var whereClauseBuilder = new StringBuilder();
        for (int i = 0; i < valueFields.size(); i++) {

            if (i > 0)
                whereClauseBuilder.append(" and ");
            whereClauseBuilder.append("d.")
                    .append(entityFields.get(i))
                    .append(" = ")
                    .append(":field")
                    .append(i);
        }
        var query = entityManager.
                createQuery(String.format("%s where %s", queryString, whereClauseBuilder.toString()));

        for (int i = 0; i < valueFields.size(); i++) {
            try {
                var field = value.getClass().getDeclaredField(valueFields.get(i));
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