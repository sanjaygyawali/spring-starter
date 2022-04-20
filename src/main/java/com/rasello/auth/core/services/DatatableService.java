package com.rasello.auth.core.services;

import com.rasello.auth.base.TableField;
import com.rasello.auth.base.TableRequest;
import com.rasello.auth.base.TableResponse;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class DatatableService {

    private final WebApplicationContext context;
    private final ListableBeanFactory beanFactory;
    private final GenericRepository genericRepository;

    public DatatableService(WebApplicationContext context, ListableBeanFactory beanFactory, GenericRepository genericRepository) {
        this.context = context;
        this.beanFactory = beanFactory;
        this.genericRepository = genericRepository;
    }


    public <E, I> TableResponse<E> getDatatable(Class<E> entityClass, TableRequest request) {
        var spec = this.createSpecification(entityClass, request);
        var repositories = new Repositories(context);
        var repository = genericRepository.getRepository(entityClass);
//        var repository = (JpaRepository) repositories.getRepositoryFor(entityClass).orElseThrow(() -> new RuntimeException("No repository found for entity class " + entityClass.getName()));
        var executor = (JpaSpecificationExecutor<E>) repository;
        int currentPage = request.getPage() > 0 ? request.getPage() - 1 : 0;
        int count = request.getCount() > 0 ? request.getCount() : 1;
        var pageRequest = PageRequest.of(currentPage, count);
        var page = executor.findAll(spec, pageRequest);

        var response = new TableResponse<E>();
        response.setRequest(request);
        response.setData(page.getContent());
        response.setPage(request.getPage());
        response.setLimit(request.getCount());
        response.setTotalPage(page.getTotalPages());
        response.setTotalRecords(page.getTotalElements());
        return response;
    }

    public <E> Specification<E> createSpecification(Class<E> entityClass, TableRequest request) {
        return (root, query, builder) -> {
            var predicates = new ArrayList<Predicate>();
            var joins = new HashMap<String, From<?, ?>>();
            for (var field : request.getTableFields()) {
                From<?, ?> join = root;
                var name = field.getName();
                var path = name;
                if (name.contains(".")) {
                    var fieldTokens = Arrays.asList(name.split("\\."));
                    for (int i = 0; i < fieldTokens.size() - 1; i++) {
                        path = fieldTokens.get(i);
                        var subTokenList = fieldTokens.subList(0, i + 1);
                        var joinPath = String.join(".", subTokenList);
                        if (!joins.containsKey(joinPath)) {
                            join = join.join(path);
                            joins.put(joinPath, join);
                        } else {
                            join = joins.get(joinPath);
                        }
                    }
                    path = fieldTokens.get(fieldTokens.size() - 1);
                } else {
                    join = root;
                }
                predicates.add(fieldToPredicate(field, path, join, builder));

            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Predicate fieldToPredicate(TableField field, String name, From<?, ?> root, CriteriaBuilder builder) {
        var value = field.getValue();
        switch (field.getSearchType()) {
            case EQUAL:
                return builder.equal(root.get(name), value);

            case EQUALS_IGNORE:
                return builder.equal(builder.lower(root.get(name)), value.toLowerCase());

            case LIKE:
                return builder.like(builder.lower(root.get(name)), value.toLowerCase());

            case GREATER_THAN:
                return builder.greaterThan(root.get(name), value);
            case LESS_THAN:
                return builder.lessThan(root.get(name), value);

            case IN:
                return root.get(name).in(Arrays.asList(value.split(",")));
            default:
                throw new IllegalArgumentException("Invalid search type");
        }
    }

}
