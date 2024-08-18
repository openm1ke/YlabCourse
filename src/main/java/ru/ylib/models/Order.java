package ru.ylib.models;

import lombok.*;

import java.time.LocalDate;

/**
 * Represents an order in the system.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Order {
    private long id;
    private OrderStatus status;
    private long carId;
    private long userId;
    private OrderType type;
    private LocalDate orderDate;

    @Builder
    public Order(long id, OrderStatus status, long carId, long userId, OrderType type, LocalDate orderDate) {
        this.id = id;
        this.status = status;
        this.carId = carId;
        this.userId = userId;
        this.type = type;
        this.orderDate = orderDate;
    }
    @Builder
    public Order(OrderStatus status, long carId, long userId, OrderType type, LocalDate orderDate) {
        this.status = status;
        this.carId = carId;
        this.userId = userId;
        this.type = type;
        this.orderDate = orderDate;
    }
}
