package ru.ylib.services;

import ru.ylib.models.Order;

import java.util.List;

public class OrderService implements CRUDService<Order> {

    @Override
    public Order create(Order order) {
        return null;
    }

    @Override
    public Order read(long id) {
        return null;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Order> readAll() {
        return List.of();
    }
}
