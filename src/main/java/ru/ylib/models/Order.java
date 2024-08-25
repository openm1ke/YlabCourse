package ru.ylib.models;

import lombok.*;

import java.time.LocalDate;

/**
 * Represents an order in the system.
 */
@Getter
@Setter
@ToString
public class Order {
    private long id;
    private OrderStatus status;
    private long carId;
    private long userId;
    private OrderType type;
    private LocalDate orderDate;
}