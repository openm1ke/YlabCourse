package ru.ylib.services;

import ru.ylib.models.Car;

import java.util.List;

public interface CarService {

    Car create(Car car);
    Car read(long id);
    Car update(Car car);
    void delete(long id);

    List<Car> readAll();
}
