package ru.ylib.services;

import ru.ylib.models.Car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ylib.Main.logger;

/**
 * This class implements the CRUDService interface for cars.
 */
public class CarService implements CRUDService<Car> {

    private final Map<Long, Car> carMap = new HashMap<>();
    /**
     * Creates a new car and adds it to the DataStore.
     *
     * @param car The car to create.
     * @return The created car.
     */
    @Override
    public Car create(Car car) {
        carMap.put(car.getId(), car);
        logger.info("Car created: {}", car);
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
        Car car = carMap.get(id);
        logger.info("Car read: {}", id);
        return car;
    }

    /**
     * Updates a car in the DataStore by its ID.
     *
     * @param car The updated car.
     * @return The updated car, or null if not found.
     */
    @Override
    public Car update(Car car) {
        if(carMap.containsKey(car.getId())) {
            carMap.put(car.getId(), car);
            logger.info("Car updated: {}", car);
            return car;
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
        Car car = carMap.remove(id);
        if(car == null) {
            logger.info("Car not found: {}", id);
        }
    }

    /**
     * Reads all cars from the DataStore.
     *
     * @return A list of all cars in the DataStore.
     */
    @Override
    public List<Car> readAll() {
        logger.info("View all cars");
        return List.copyOf(carMap.values());
    }
}