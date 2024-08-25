package ru.ylib.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ylib.dto.OrderDTO;
import ru.ylib.mappers.OrderMapper;
import ru.ylib.models.Order;
import ru.ylib.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Order order = orderService.read(id);
        if (order != null) {
            OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
            return ResponseEntity.ok(orderDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.orderDTOToOrder(orderDTO);
        Order createdOrder = orderService.create(order);
        OrderDTO createdOrderDTO = orderMapper.orderToOrderDTO(createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") long id, @RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.orderDTOToOrder(orderDTO);
        order.setId(id);
        Order updatedOrder = orderService.update(order);
        if (updatedOrder != null) {
            OrderDTO updatedOrderDTO = orderMapper.orderToOrderDTO(updatedOrder);
            return ResponseEntity.ok(updatedOrderDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") long id) {
        try {
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.readAll();
        List<OrderDTO> orderDTOs = orderMapper.ordersToOrderDTOs(orders);
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<OrderDTO> getOrderByCarId(@PathVariable("carId") long carId) {
        Order order = orderService.readByCarId(carId);
        if (order != null) {
            OrderDTO orderDTO = orderMapper.orderToOrderDTO(order);
            return ResponseEntity.ok(orderDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
