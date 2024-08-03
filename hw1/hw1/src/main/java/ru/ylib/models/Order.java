package ru.ylib.models;

import java.time.LocalDate;

public class Order {
    private long id;
    private OrderStatus status;
    private long carId;
    private long userId;
    private ServiceEnum service;
    private LocalDate orderDate;
}
