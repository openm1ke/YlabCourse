package ru.ylib.services;

import ru.ylib.models.Car;
import ru.ylib.utils.DataStore;

import java.util.List;

public class CarService implements CRUDService<Car> {

    @Override
    public Car create(Car car) {
        DataStore.cars.add(car);
        return car;
    }

    @Override
    public Car read(long id) {
        for (Car car : DataStore.cars) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

    @Override
    public Car update(Car car) {
        for (Car c : DataStore.cars) {
            if (c.getId() == car.getId()) {
                c.setBrand(car.getBrand());
                c.setModel(car.getModel());
                c.setYear(car.getYear());
                c.setPrice(car.getPrice());
                c.setStatus(car.getStatus());
                return c;
            }
        }
        return null;
    }

    @Override
    public void delete(long id) {
        for (Car car : DataStore.cars) {
            if (car.getId() == id) {
                DataStore.cars.remove(car);
                break;
            }
        }
    }

    @Override
    public List<Car> readAll() {
        return DataStore.cars;
    }
}
