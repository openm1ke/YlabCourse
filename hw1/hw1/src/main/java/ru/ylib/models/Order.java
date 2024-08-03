package ru.ylib.models;

import ru.ylib.utils.IdGenerator;

import java.time.LocalDate;

public class Order {
    private long id;
    private OrderStatus status;
    private long carId;
    private long userId;
    private OrderType type;
    private LocalDate orderDate;

    public Order(OrderStatus status, long carId, long userId, OrderType type, LocalDate orderDate) {
        this.status = status;
        this.carId = carId;
        this.userId = userId;
        this.type = type;
        this.orderDate = orderDate;
        this.id = IdGenerator.generateUserId();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getCarId() {
        return carId;
    }

    public void setType(OrderType service) {
        this.type = service;
    }

    public OrderType getType() {
        return type;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
