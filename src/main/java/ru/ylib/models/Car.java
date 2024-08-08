package ru.ylib.models;

import lombok.*;
import ru.ylib.utils.IdGenerator;

/**
 * Represents a car with its brand, model, year, price, and status.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Car {
    private final long id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private CarStatus status;

    /**
     * Constructs a new Car with the given brand, model, year, price, and status.
     *
     * @param brand the brand of the car
     * @param model the model of the car
     * @param year the year of the car
     * @param price the price of the car
     * @param status the status of the car
     */
    @Builder
    public Car(String brand, String model, int year, double price, CarStatus status) {
        this.id = IdGenerator.generateCarId();
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
    }
}