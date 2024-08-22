package ru.ylib.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylib.dto.OrderDTO;
import ru.ylib.models.Order;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO orderToOrderDTO(Order order);

    Order orderDTOToOrder(OrderDTO orderDTO);
}
