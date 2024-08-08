package ru.ylib.models;

import ru.ylib.utils.IdGenerator;

/**
 * Represents a car with its brand, model, year, price, and status.
 */
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
    public Car(String brand, String model, int year, double price, CarStatus status) {
        this.id = IdGenerator.generateCarId();
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.status = status;
    }

    /**
     * Gets the brand of the car.
     *
     * @return the brand of the car
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the car.
     *
     * @param brand the new brand of the car
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the model of the car.
     *
     * @return the model of the car
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car.
     *
     * @param model the new model of the car
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the year of the car.
     *
     * @return the year of the car
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the car.
     *
     * @param year the new year of the car
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets the price of the car.
     *
     * @return the price of the car
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the car.
     *
     * @param price the new price of the car
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the status of the car.
     *
     * @return the status of the car
     */
    public CarStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the car.
     *
     * @param status the new status of the car
     */
    public void setStatus(CarStatus status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the car.
     *
     * @return a string representation of the car
     */
    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", status=" + status +
                '}';
    }

    /**
     * Gets the ID of the car.
     *
     * @return the ID of the car
     */
    public long getId() {
        return id;
    }
}