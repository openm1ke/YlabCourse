package ru.ylib.mappers;

import org.mapstruct.Mapper;
import ru.ylib.dto.OrderDTO;
import ru.ylib.models.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order orderDTOToOrder(OrderDTO orderDTO);
    OrderDTO orderToOrderDTO(Order order);

    List<OrderDTO> ordersToOrderDTOs(List<Order> orders);

    List<Order> orderDTOsToOrders(List<OrderDTO> orderDTOs);
}
