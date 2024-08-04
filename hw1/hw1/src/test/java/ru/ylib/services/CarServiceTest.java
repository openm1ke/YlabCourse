package ru.ylib.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ylib.models.Car;
import ru.ylib.models.CarStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    private CarService carService;

    @BeforeEach
    public void setUp() {
        carService = new CarService();
    }

    @Test
    public void testCreate() {
        Car car = new Car("BMW", "X5", 2022, 100000, CarStatus.AVAILABLE);
        carService.create(car);
        List<Car> cars = carService.readAll();
        Car createdCar = carService.read(cars.get(cars.size() - 1).getId());
        assertEquals(car.getBrand(), createdCar.getBrand());
        assertEquals(car.getModel(), createdCar.getModel());
        assertEquals(car.getYear(), createdCar.getYear());
        assertEquals(car.getPrice(), createdCar.getPrice());
        assertEquals(car.getStatus(), createdCar.getStatus());

        car.setBrand("Audi");
        car.setModel("Q7");
        car.setYear(2023);
        car.setPrice(150000);
        car.setStatus(CarStatus.SOLD);
        carService.update(car);
        createdCar = carService.read(cars.get(cars.size() - 1).getId());
        assertEquals(car.getBrand(), createdCar.getBrand());
        assertEquals(car.getModel(), createdCar.getModel());
        assertEquals(car.getYear(), createdCar.getYear());
        assertEquals(car.getPrice(), createdCar.getPrice());
        assertEquals(car.getStatus(), createdCar.getStatus());
        carService.delete(createdCar.getId());
        createdCar = carService.read(1);
        assertNull(createdCar);
    }
}