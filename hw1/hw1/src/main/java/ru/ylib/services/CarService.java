package ru.ylib.services;

import ru.ylib.models.Car;
import ru.ylib.utils.DataStore;

import java.util.List;

/**
 * This class implements the CRUDService interface for cars.
 */
public class CarService implements CRUDService<Car> {

    /**
     * Creates a new car and adds it to the DataStore.
     *
     * @param car The car to create.
     * @return The created car.
     */
    @Override
    public Car create(Car car) {
        DataStore.cars.add(car);
        return car;
    }

    /**
     * Reads a car from the DataStore by its ID.
     *
     * @param id The ID of the car to read.
     * @return The car with the given ID, or null if not found.
     */
    @Override
    public Car read(long id) {
        for (Car car : DataStore.cars) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

    /**
     * Updates a car in the DataStore by its ID.
     *
     * @param car The updated car.
     * @return The updated car, or null if not found.
     */
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

    /**
     * Deletes a car from the DataStore by its ID.
     *
     * @param id The ID of the car to delete.
     */
    @Override
    public void delete(long id) {
        for (Car car : DataStore.cars) {
            if (car.getId() == id) {
                DataStore.cars.remove(car);
                break;
            }
        }
    }

    /**
     * Reads all cars from the DataStore.
     *
     * @return A list of all cars in the DataStore.
     */
    @Override
    public List<Car> readAll() {
        return DataStore.cars;
    }
}