package com.rasello.auth.validation.validator;

import com.rasello.auth.validation.annotation.Exists;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;


public class ExistsConstraintValidator implements ConstraintValidator<Exists, Object> {
    private final EntityManager entityManager;

    private Class<?> entityClass;
    private List<String> entityFields;
    private List<String> valueFields;

    public ExistsConstraintValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Exists constraintAnnotation) {
        entityClass = constraintAnnotation.entity();
        entityFields = Arrays.asList(constraintAnnotation.entityFields());
        valueFields = Arrays.asList(constraintAnnotation.valueFields());
        if (entityFields.size() <= valueFields.size())
            throw new IllegalArgumentException("Entity and value fields size do not match");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (entityFields.isEmpty())
            throw new IllegalArgumentException("No fields supplied in validation");
        var queryString = String.format("select count(d) from %s d", entityClass.getName());
        var whereClauseBuilder = new StringBuilder();
        for (int i = 0; i < entityFields.size(); i++) {

            if (i > 0)
                whereClauseBuilder.append(" and ");
            whereClauseBuilder.append("a.")
                    .append(entityFields.get(i))
                    .append(" = ")
                    .append(":field")
                    .append(i);
        }
        var query = entityManager.
                createQuery(String.format("%s where %s", queryString, whereClauseBuilder.toString()), entityClass);

        for (int i = 0; i < entityFields.size(); i++) {
            try {
                var field = value.getClass().getField(valueFields.get(i));
                query.setParameter("field" + i, field.get(value));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }
        var count = ((BigInteger) query.getSingleResult()).intValue();
        return count > 0;
    }
}
