package ru.ylib.services;

import ru.ylib.models.Order;
import ru.ylib.utils.DataStore;

import java.util.List;

public class OrderService implements CRUDService<Order> {

    @Override
    public Order create(Order order) {
        DataStore.orders.add(order);
        return order;
    }

    @Override
    public Order read(long id) {
        for (Order order : DataStore.orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

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

    @Override
    public void delete(long id) {
        for (Order order : DataStore.orders) {
            if (order.getId() == id) {
                DataStore.orders.remove(order);
                break;
            }
        }
    }

    @Override
    public List<Order> readAll() {
        return DataStore.orders;
    }

    public Order readByCarId(long carId) {
        for (Order order : DataStore.orders) {
            if (order.getCarId() == carId) {
                return order;
            }
        }
        return null;
    }
}
