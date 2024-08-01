package ru.ylib.services;

import java.util.List;

public interface CRUDService<T> {

    T create(T t);
    T read(long id);
    T update(T t);
    void delete(long id);
    List<T> readAll();
}
