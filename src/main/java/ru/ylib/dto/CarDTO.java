package ru.ylib.dto;

import lombok.Data;

@Data
public class CarDTO {
    private long id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String status;
}