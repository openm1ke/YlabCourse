package ru.ylib.services;

import ru.ylib.models.Order;
import ru.ylib.models.OrderStatus;
import ru.ylib.models.OrderType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.ylib.Main.logger;


/**
 * Implementation of the CRUDService interface for managing orders.
 */
public class OrderService implements CRUDService<Order> {

    private final Connection connection;

    public OrderService(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Order create(Order order) {
        String sql = "INSERT INTO app.order (status, car_id, user_id, type, order_date) VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, order.getStatus().name());
            stmt.setLong(2, order.getCarId());
            stmt.setLong(3, order.getUserId());
            stmt.setString(4, order.getType().name());
            stmt.setDate(5, java.sql.Date.valueOf(order.getOrderDate()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                order.setId(id); // Set the generated ID
                logger.info("Order created: {}", order);
                return order;
            }
        } catch (SQLException e) {
            logger.error("Failed to create order", e);
        }
        return null;
    }

    @Override
    public Order read(long id) {
        String sql = "SELECT * FROM app.order WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapToOrder(rs);
            }
        } catch (SQLException e) {
            logger.error("Failed to read order", e);
        }
        return null;
    }

    /**
     * Updates an order in the DataStore.
     *
     * @param order The updated order.
     * @return The updated order, or null if not found.
     */
    @Override
    public Order update(Order order) {
        String sql = "UPDATE app.order SET status = ?, car_id = ?, user_id = ?, type = ?, order_date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, order.getStatus().name());
            stmt.setLong(2, order.getCarId());
            stmt.setLong(3, order.getUserId());
            stmt.setString(4, order.getType().name());
            stmt.setDate(5, java.sql.Date.valueOf(order.getOrderDate()));
            stmt.setLong(6, order.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Order updated: {}", order);
                return order;
            }
        } catch (SQLException e) {
            logger.error("Failed to update order", e);
        }
        return null;
    }

    /**
     * Deletes an order from the DataStore by its ID.
     *
     * @param id The ID of the order to delete.
     */
    @Override
    public void delete(long id) {
        String sql = "DELETE FROM app.order WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                logger.info("Order not found: {}", id);
            }
        } catch (SQLException e) {
            logger.error("Failed to delete order", e);
        }
    }

    /**
     * Reads all orders from the DataStore.
     *
     * @return A list of all orders.
     */
    @Override
    public List<Order> readAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM app.order";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(mapToOrder(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to read orders", e);
        }
        return orders;
    }

    private Order mapToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setCarId(rs.getLong("car_id"));
        order.setUserId(rs.getLong("user_id"));
        order.setType(OrderType.valueOf(rs.getString("type")));
        order.setOrderDate(rs.getDate("order_date").toLocalDate());
        return order;
    }

    /**
     * Reads an order from the DataStore by its car ID, if it is a buy order.
     *
     * @param carId The ID of the car.
     * @return The order with the given car ID and type "BUY", or null if not found.
     */
    public Order readByCarId(long carId) {
        logger.info("Reading order by car ID: {}", carId);
        String sql = "SELECT * FROM app.order WHERE car_id = ? AND type = 'BUY'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, carId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapToOrder(rs);
            }
        } catch (SQLException e) {
            logger.error("Failed to read order", e);
        }
        return null;
    }
}