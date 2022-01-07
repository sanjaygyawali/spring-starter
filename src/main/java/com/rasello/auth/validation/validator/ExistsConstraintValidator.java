package com.rasello.auth.validation.validator;

import com.rasello.auth.validation.annotation.Exists;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ExistsConstraintValidator implements ConstraintValidator<Exists, Object> {
    protected List<String> entityFields;
    protected List<String> valueFields;
    protected Class<?> entityClass;
    private final EntityManager entityManager;

    public ExistsConstraintValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Exists constraintAnnotation) {
        entityFields = Arrays.asList(constraintAnnotation.entityFields());
        valueFields = Arrays.asList(constraintAnnotation.valueFields());
        entityClass = constraintAnnotation.entity();
        if (entityFields.size() <= 0)
            throw new IllegalArgumentException("Fields cannot be empty");
        if (valueFields.size() == 0) {
            valueFields = entityFields;
        }
        if (entityFields.size() != valueFields.size())
            throw new IllegalArgumentException("Entity fields and value fields should be of same length");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (valueFields.isEmpty())
            throw new IllegalArgumentException("No fields supplied in validation");
        var queryString = String.format("select count(d) from %s d", entityClass.getSimpleName());
        var whereClause = this.buildWhereClause(entityFields);
        var query = createQuery(value, queryString, whereClause);
        var count = ((Long) query.getSingleResult()).intValue();
        return count > 0;
    }

    Query createQuery(Object value, String queryString, String whereClause) {
        var query = entityManager.
                createQuery(String.format("%s where %s", queryString, whereClause));

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
        return query;
    }

    String buildWhereClause(List<String> entityFields) {
        var whereClauseBuilder = new StringBuilder();
        for (int i = 0; i < entityFields.size(); i++) {
            if (i > 0)
                whereClauseBuilder.append(" and ");
            whereClauseBuilder.append("d.")
                    .append(entityFields.get(i))
                    .append(" = ")
                    .append(":field")
                    .append(i);
        }
        return whereClauseBuilder.toString();
    }
}