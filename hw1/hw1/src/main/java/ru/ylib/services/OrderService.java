package ru.ylib.services;

import ru.ylib.models.Order;
import ru.ylib.models.OrderType;
import ru.ylib.utils.DataStore;

import java.util.List;

/**
 * Implementation of the CRUDService interface for managing orders.
 */
public class OrderService implements CRUDService<Order> {

    /**
     * Creates a new order and adds it to the DataStore.
     *
     * @param order The order to create.
     * @return The created order.
     */
    @Override
    public Order create(Order order) {
        DataStore.orders.add(order);
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
        for (Order order : DataStore.orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    /**
     * Updates an order in the DataStore.
     *
     * @param order The updated order.
     * @return The updated order, or null if not found.
     */
    @Override
    public Order update(Order order) {
        for(Order o : DataStore.orders) {
            if(o.getId() == order.getId()) {
                o.setCarId(order.getCarId());
                o.setUserId(order.getUserId());
                o.setOrderDate(order.getOrderDate());
                o.setType(order.getType());
                o.setStatus(order.getStatus());
                return o;
            }
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
        for (Order order : DataStore.orders) {
            if (order.getId() == id) {
                DataStore.orders.remove(order);
                break;
            }
        }
    }

    /**
     * Reads all orders from the DataStore.
     *
     * @return A list of all orders.
     */
    @Override
    public List<Order> readAll() {
        return DataStore.orders;
    }

    /**
     * Reads an order from the DataStore by its car ID, if it is a buy order.
     *
     * @param carId The ID of the car.
     * @return The order with the given car ID and type "BUY", or null if not found.
     */
    public Order readByCarId(long carId) {
        for (Order order : DataStore.orders) {
            if (order.getCarId() == carId && order.getType() == OrderType.BUY) {
                return order;
            }
        }
        return null;
    }
}