package ru.ylib.models;

import lombok.*;
import ru.ylib.utils.IdGenerator;

import java.time.LocalDate;

/**
 * Represents an order in the system.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Order {
    private final long id;
    private OrderStatus status;
    private long carId;
    private long userId;
    private OrderType type;
    private LocalDate orderDate;

    /**
     * Constructs a new Order.
     *
     * @param status    The current status of the order.
     * @param carId     The ID of the car associated with the order.
     * @param userId    The ID of the user who placed the order.
     * @param type      The type of the order.
     * @param orderDate The date the order was placed.
     */
    @Builder
    public Order(OrderStatus status, long carId, long userId, OrderType type, LocalDate orderDate) {
        this.status = status;
        this.carId = carId;
        this.userId = userId;
        this.type = type;
        this.orderDate = orderDate;
        this.id = IdGenerator.generateOrderId();
    }
}
