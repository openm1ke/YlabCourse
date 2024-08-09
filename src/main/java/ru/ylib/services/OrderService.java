package ru.ylib.services;

import ru.ylib.models.Order;
import ru.ylib.models.OrderType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ylib.Main.logger;


/**
 * Implementation of the CRUDService interface for managing orders.
 */
public class OrderService implements CRUDService<Order> {

    private final Map<Long, Order> orderMap = new HashMap<>();
    /**
     * Creates a new order and adds it to the DataStore.
     *
     * @param order The order to create.
     * @return The created order.
     */
    @Override
    public Order create(Order order) {
        orderMap.put(order.getId(), order);
        logger.info("Order created: {}", order);
        return order;
    }

    /**
     * Reads an order from the DataStore by its ID.
     *
     * @param id The ID of the order to read.
     * @return The order with the given ID, or null if not found.
     */
    @Override
    public Order read(long id) {
        Order order = orderMap.get(id);
        logger.info("Order {} read: {}", id, order);
        return order;
    }

    /**
     * Updates an order in the DataStore.
     *
     * @param order The updated order.
     * @return The updated order, or null if not found.
     */
    @Override
    public Order update(Order order) {
        if(orderMap.containsKey(order.getId())) {
            orderMap.put(order.getId(), order);
            logger.info("Order updated: {}", order);
            return order;
        }
        return null;
    }

    /**
     * Deletes an order from the DataStore by its ID.
     *
     * @param id The ID of the order to delete.
     */
    @Override
    public void delete(long id) {
        Order order = orderMap.remove(id);
        if(order == null) {
            logger.info("Order not found: {}", id);
        }
    }

    /**
     * Reads all orders from the DataStore.
     *
     * @return A list of all orders.
     */
    @Override
    public List<Order> readAll() {
        logger.info("View all orders");
        return List.copyOf(orderMap.values());
    }

    /**
     * Reads an order from the DataStore by its car ID, if it is a buy order.
     *
     * @param carId The ID of the car.
     * @return The order with the given car ID and type "BUY", or null if not found.
     */
    public Order readByCarId(long carId) {
        logger.info("View orders by car ID: {}", carId);
        return orderMap
                .values()
                .stream()
                .filter(order -> order.getCarId() == carId && order.getType() == OrderType.BUY)
                .findFirst()
                .orElse(null);
    }
}