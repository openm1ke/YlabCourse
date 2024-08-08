package ru.ylib.services;

import java.util.List;

/**
 * This interface represents a CRUD (Create, Read, Update, Delete) service for a specific type T.
 *
 * @param <T> The type of entity this CRUD service operates on.
 */
public interface CRUDService<T> {

    /**
     * Creates a new entity of type T.
     *
     * @param t The entity to create.
     * @return The created entity.
     */
    T create(T t);

    /**
     * Reads an entity of type T by its ID.
     *
     * @param id The ID of the entity to read.
     * @return The entity with the given ID, or null if not found.
     */
    T read(long id);

    /**
     * Updates an existing entity of type T.
     *
     * @param t The updated entity.
     * @return The updated entity, or null if not found.
     */
    T update(T t);

    /**
     * Deletes an entity of type T by its ID.
     *
     * @param id The ID of the entity to delete.
     */
    void delete(long id);

    /**
     * Reads all entities of type T.
     *
     * @return A list of all entities of type T.
     */
    List<T> readAll();
}