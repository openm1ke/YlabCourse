package ru.ylib.utils.mappers;

import ru.ylib.models.Order;
import ru.ylib.models.OrderStatus;
import ru.ylib.models.OrderType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper {
    public static Order mapToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setCarId(rs.getLong("car_id"));
        order.setUserId(rs.getLong("user_id"));
        order.setType(OrderType.valueOf(rs.getString("type")));
        order.setOrderDate(rs.getDate("order_date").toLocalDate());
        return order;
    }
}
