package ru.ylib.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarDTO {
    private long id;
    @NotNull
    private String brand;
    @NotNull
    private String model;
    @NotNull
    @Min(1900)
    private int year;
    @NotNull
    private double price;
    @NotNull
    private String status;
}