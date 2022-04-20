package com.rasello.auth.services;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T> {
    /**
     * Save a country.
     *
     * @param country the entity to save.
     * @return the persisted entity.
     */
    T save(T entity);

    /**
     * Partially updates a country.
     *
     * @param country the entity to update partially.
     * @return the persisted entity.
     */
    Optional<T> partialUpdate(T entity);

    /**
     * Get all the countries.
     *
     * @return the list of entities.
     */
    List<T> findAll();

    /**
     * Get the "id" country.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<T> findOne(Long id);

    /**
     * Delete the "id" country.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

}
