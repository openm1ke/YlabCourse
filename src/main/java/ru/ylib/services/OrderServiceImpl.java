package ru.ylib.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ylib.dao.OrderDAO;
import ru.ylib.models.Order;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public Order create(Order order) {
        return orderDAO.create(order);
    }

    public Order read(long id) {
        return orderDAO.read(id);
    }

    public Order update(Order order) {
        return orderDAO.update(order);
    }

    public void delete(long id) {
        orderDAO.delete(id);
    }

    public List<Order> readAll() {
        return orderDAO.readAll();
    }

    public Order readByCarId(long carId) {
        return orderDAO.readByCarId(carId);
    }
}
