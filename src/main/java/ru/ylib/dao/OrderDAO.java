package ru.ylib.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ylib.models.Order;
import ru.ylib.models.OrderStatus;
import ru.ylib.models.OrderType;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OrderDAO {

    private final DataSource dataSource;

    private static final String INSERT_ORDER = "INSERT INTO app.order (status, car_id, user_id, type, order_date) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_ORDER = "UPDATE app.order SET status = ?, car_id = ?, user_id = ?, type = ?, order_date = ? WHERE id = ?";
    private static final String DELETE_ORDER = "DELETE FROM app.order WHERE id = ?";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM app.order";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM app.order WHERE id = ?";
    private static final String SELECT_ORDER_BY_CAR_ID_AND_TYPE = "SELECT * FROM app.order WHERE car_id = ? AND type = 'BUY'";

    @Autowired
    public OrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Order create(Order order) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(INSERT_ORDER)) {
            stmt.setString(1, order.getStatus().name());
            stmt.setLong(2, order.getCarId());
            stmt.setLong(3, order.getUserId());
            stmt.setString(4, order.getType().name());
            stmt.setDate(5, java.sql.Date.valueOf(order.getOrderDate()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                order.setId(id);
                return order;
            }
        } catch (SQLException e) {
            log.error("Failed to create order", e);
        }
        return null;
    }

    public Order read(long id) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(SELECT_ORDER_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapToOrder(rs);
            }
        } catch (SQLException e) {
            log.error("Failed to read order", e);
        }
        return null;
    }

    public Order update(Order order) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(UPDATE_ORDER)) {
            stmt.setString(1, order.getStatus().name());
            stmt.setLong(2, order.getCarId());
            stmt.setLong(3, order.getUserId());
            stmt.setString(4, order.getType().name());
            stmt.setDate(5, java.sql.Date.valueOf(order.getOrderDate()));
            stmt.setLong(6, order.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("Order updated: {}", order);
                return order;
            }
        } catch (SQLException e) {
            log.error("Failed to update order", e);
        }
        return null;
    }

    public void delete(long id) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(DELETE_ORDER)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                log.info("Order not found: {}", id);
            }
        } catch (SQLException e) {
            log.error("Failed to delete order", e);
        }
    }

    public List<Order> readAll() {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(SELECT_ALL_ORDERS)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = mapToOrder(rs);
                orders.add(order);
            }
        } catch (SQLException e) {
            log.error("Failed to read orders", e);
        }
        return orders;
    }

    public Order readByCarId(long carId) {
        log.info("Reading order by car ID: {}", carId);
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(SELECT_ORDER_BY_CAR_ID_AND_TYPE)) {
            stmt.setLong(1, carId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapToOrder(rs);
            }
        } catch (SQLException e) {
            log.error("Failed to read order", e);
        }
        return null;
    }

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