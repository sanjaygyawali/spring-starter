package com.rasello.auth.validation.validator;

import com.rasello.auth.validation.annotation.Unique;
import com.thoughtworks.xstream.InitializationException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Component
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
            throw new InitializationException("Fields cannot be empty");
    }

    @NotBlank
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (fields.isEmpty())
            throw new IllegalArgumentException("No fields supplied in validation");
        var queryString = String.format("select count(d) from %s d", entityClass.getName());
        var whereClauseBuilder = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {

            if (i > 0)
                whereClauseBuilder.append(" and ");
            whereClauseBuilder.append("a.")
                    .append(fields.get(i))
                    .append(" = ")
                    .append(":field")
                    .append(i);
        }
        var query = entityManager.
                createQuery(String.format("%s where %s", queryString, whereClauseBuilder.toString()), entityClass);

        for (int i = 0; i < fields.size(); i++) {
            try {
                var field = value.getClass().getField(fields.get(i));
                query.setParameter("field" + i, field.get(value));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalStateException(e.getMessage());
            }
        }
        var count = ((BigInteger) query.getSingleResult()).intValue();
        return count <= 0;
    }
}