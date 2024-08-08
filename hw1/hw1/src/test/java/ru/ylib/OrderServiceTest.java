package ru.ylib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ylib.models.Order;
import ru.ylib.models.OrderStatus;
import ru.ylib.models.OrderType;
import ru.ylib.services.OrderService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;
    @BeforeEach
    void setUp() {
        orderService = new OrderService();
    }

    @Test
    void createOrderTests() {
        Order order = orderService.create(new Order(OrderStatus.CREATED, 1, 1, OrderType.BUY, LocalDate.now()));
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals(1, order.getCarId());
        assertEquals(1, order.getUserId());
        assertEquals(OrderType.BUY, order.getType());
        assertEquals(LocalDate.now(), order.getOrderDate());

        Order order1 = orderService.read(order.getId());
        order1.setStatus(OrderStatus.CANCELED);
        order1.setCarId(2);
        order1.setUserId(2);
        order1.setOrderDate(LocalDate.now());
        orderService.update(order1);

        List<Order> orders = orderService.readAll();
        assertEquals(1, orders.size());

        Order order2 = orderService.readByCarId(2);
        assertEquals(OrderStatus.CANCELED, order2.getStatus());
        assertEquals(2, order2.getCarId());
        assertEquals(2, order2.getUserId());
        assertEquals(LocalDate.now(), order2.getOrderDate());

        orderService.delete(order2.getId());
        orders = orderService.readAll();
        assertEquals(0, orders.size());

    }
}