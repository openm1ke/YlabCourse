package ru.ylib.models;

import ru.ylib.utils.IdGenerator;

import java.time.LocalDate;

/**
 * Represents an order in the system.
 */
public class Order {
    private final long id; // Unique identifier for the order
    private OrderStatus status; // Current status of the order
    private long carId; // ID of the car associated with the order
    private long userId; // ID of the user who placed the order
    private OrderType type; // Type of the order
    private LocalDate orderDate; // Date the order was placed

    /**
     * Constructs a new Order.
     *
     * @param status    The current status of the order.
     * @param carId     The ID of the car associated with the order.
     * @param userId    The ID of the user who placed the order.
     * @param type      The type of the order.
     * @param orderDate The date the order was placed.
     */
    public Order(OrderStatus status, long carId, long userId, OrderType type, LocalDate orderDate) {
        this.status = status;
        this.carId = carId;
        this.userId = userId;
        this.type = type;
        this.orderDate = orderDate;
        this.id = IdGenerator.generateOrderId();
    }

    /**
     * Sets the ID of the user who placed the order.
     *
     * @param userId The ID of the user.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Returns the ID of the user who placed the order.
     *
     * @return The ID of the user.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the car associated with the order.
     *
     * @param carId The ID of the car.
     */
    public void setCarId(long carId) {
        this.carId = carId;
    }

    /**
     * Returns the ID of the car associated with the order.
     *
     * @return The ID of the car.
     */
    public long getCarId() {
        return carId;
    }

    /**
     * Sets the type of the order.
     *
     * @param type The type of the order.
     */
    public void setType(OrderType type) {
        this.type = type;
    }

    /**
     * Returns the type of the order.
     *
     * @return The type of the order.
     */
    public OrderType getType() {
        return type;
    }

    /**
     * Sets the date the order was placed.
     *
     * @param orderDate The date the order was placed.
     */
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Returns the date the order was placed.
     *
     * @return The date the order was placed.
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the current status of the order.
     *
     * @param status The current status of the order.
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Returns the current status of the order.
     *
     * @return The current status of the order.
     */
    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", carId=" + carId +
                ", userId=" + userId +
                ", type=" + type +
                ", orderDate=" + orderDate +
                '}';
    }

    /**
     * Returns the unique identifier for the order.
     *
     * @return The ID of the order.
     */
    public long getId() {
        return id;
    }
}
