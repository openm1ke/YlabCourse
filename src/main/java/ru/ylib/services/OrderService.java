package ru.ylib.services;

import ru.ylib.models.Order;

import java.util.List;

public interface OrderService {
    Order create(Order order);
    Order read(long id);
    Order update(Order order);
    void delete(long id);

    List<Order> readAll();

    Order readByCarId(long carId);
}
