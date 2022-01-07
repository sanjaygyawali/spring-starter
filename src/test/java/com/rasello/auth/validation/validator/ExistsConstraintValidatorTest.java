package com.rasello.auth.validation.validator;

import com.rasello.auth.validation.annotation.Exists;
import org.hibernate.engine.query.spi.HQLQueryPlan;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.internal.QueryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExistsConstraintValidatorTest {
    @Mock
    private EntityManager entityManager;

    @Mock
    private Exists exists;

    private ExistsConstraintValidator underTest;

    @Exists(entity = User.class, entityFields = {"name", "email"}, valueFields = {"name", "email"})
    class User {
        String name;
        String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    @BeforeEach
    public void setup(){
        underTest = new ExistsConstraintValidator(entityManager);
    }

    @Test
    public void initializeSetsEntityFieldsValueFieldsAndEntityClassCorrectly(){
        Class<User> userClass = User.class;

        when(exists.entityFields()).thenReturn(new String[]{"name", "email"});
        when(exists.valueFields()).thenReturn(new String[]{});
        doReturn(User.class).when(exists).entity();
        underTest.initialize(exists);

        assertThat(underTest.valueFields.size()).isEqualTo(2);
        assertThat(underTest.entityClass).isSameAs(User.class);
        assertThat(underTest.valueFields).isEqualTo(underTest.entityFields);

        when(exists.valueFields()).thenReturn(new String[]{"name1", "email1"});
        underTest.initialize(exists);
        assertThat(underTest.valueFields).isEqualTo(Arrays.asList("name1", "email1"));

        when(exists.entityFields()).thenReturn(new String[]{});
        assertThatThrownBy(() -> underTest.initialize(exists)).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void initializeShouldThrowErrorWhenEntityFieldsAndValueFieldsAreDifferentInSize(){
        when(exists.entityFields()).thenReturn(new String[]{"name", "email"});
        when(exists.valueFields()).thenReturn(new String[]{"name"});
        assertThatThrownBy(() -> underTest.initialize(exists)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void buildWhereClauseGeneratesCorrectWhereClause(){
        assertThat(underTest.buildWhereClause(Arrays.asList("name"))).isEqualTo("d.name = :field0");
        assertThat(underTest.buildWhereClause(Arrays.asList("name", "email"))).isEqualTo("d.name = :field0 and d.email = :field1");
    }

    @Test
    public void createQueryGeneratesProperQuery(){
        when(exists.entityFields()).thenReturn(new String[]{"name", "email"});
        when(exists.valueFields()).thenReturn(new String[]{"name", "email"});
        when(entityManager.createQuery(anyString())).thenReturn(new QueryImpl<User>(any(SharedSessionContractImplementor.class), any(HQLQueryPlan.class), ""));
        underTest.valueFields = Arrays.asList("name", "email");

        var user = new User("John", "john@email.com");
        var query = underTest.createQuery(user,"select d from User d", "d.name = :field0 and d.email = :field1");
        assertThat(query.getParameter("field0")).isEqualTo(user.name);
        assertThat(query.getParameter("field1")).isEqualTo(user.email);
    }

}