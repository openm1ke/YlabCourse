package ru.ylib.models;

import lombok.*;

/**
 * Represents a car with its brand, model, year, price, and status.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Car {
    private long id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private CarStatus status;

    @Builder
    public Car(long id, String brand, String model, int year, double price, CarStatus status) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
    }
    @Builder
    public Car(String brand, String model, int year, double price, CarStatus status) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
    }
}