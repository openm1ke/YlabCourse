package ru.ylib.models;

import ru.ylib.utils.IdGenerator;

public class Car {
    private final long id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String status;

    public Car(String brand, String model, int year, double price, String status) {
        this.id = IdGenerator.generateCarId();
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }
}
