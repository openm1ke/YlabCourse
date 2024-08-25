package ru.ylib.models;

import lombok.*;

@Getter
@Setter
@ToString
public class Car {
    private long id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private CarStatus status;
}