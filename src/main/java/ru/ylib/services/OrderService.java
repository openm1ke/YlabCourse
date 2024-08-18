    package ru.ylib.services;

    import ru.ylib.dto.OrderDTO;
    import ru.ylib.models.Order;
    import ru.ylib.models.OrderStatus;
    import ru.ylib.models.OrderType;
    import ru.ylib.utils.DatabaseConnection;
    import ru.ylib.utils.mappers.OrderMapper;

    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    import static ru.ylib.Main.logger;


    /**
     * Implementation of the CRUDService interface for managing orders.
     */
    public class OrderService implements CRUDService<OrderDTO> {

        private final DatabaseConnection dbConnection;
        private final OrderMapper orderMapper = OrderMapper.INSTANCE;

        private static final String INSERT_ORDER = "INSERT INTO app.order (status, car_id, user_id, type, order_date) VALUES (?, ?, ?, ?, ?) RETURNING id";
        private static final String UPDATE_ORDER = "UPDATE app.order SET status = ?, car_id = ?, user_id = ?, type = ?, order_date = ? WHERE id = ?";
        private static final String DELETE_ORDER = "DELETE FROM app.order WHERE id = ?";
        private static final String SELECT_ALL_ORDERS = "SELECT * FROM app.order";
        private static final String SELECT_ORDER_BY_ID = "SELECT * FROM app.order WHERE id = ?";
        private static final String SELECT_ORDER_BY_CAR_ID_AND_TYPE = "SELECT * FROM app.order WHERE car_id = ? AND type = 'BUY'";

        public OrderService(DatabaseConnection dbConnection) {
            this.dbConnection = dbConnection;
        }

        /**
         * Creates a new order in the DataStore.
         *
         * @param order The order to create.
         * @return The created order.
         */
        @Override
        public OrderDTO create(OrderDTO orderDTO) {
            Order order = orderMapper.orderDTOToOrder(orderDTO);
            try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(INSERT_ORDER)) {
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
                    return orderMapper.orderToOrderDTO(order);
                }
            } catch (SQLException e) {
                logger.error("Failed to create order", e);
            }
            return null;
        }

        @Override
        public OrderDTO read(long id) {
            try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(SELECT_ORDER_BY_ID)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Order order = mapToOrder(rs);
                    return orderMapper.orderToOrderDTO(order);
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
        public OrderDTO update(OrderDTO orderDTO) {
            Order order = orderMapper.orderDTOToOrder(orderDTO);
            try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(UPDATE_ORDER)) {
                stmt.setString(1, order.getStatus().name());
                stmt.setLong(2, order.getCarId());
                stmt.setLong(3, order.getUserId());
                stmt.setString(4, order.getType().name());
                stmt.setDate(5, java.sql.Date.valueOf(order.getOrderDate()));
                stmt.setLong(6, order.getId());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Order updated: {}", order);
                    return orderMapper.orderToOrderDTO(order);
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
            try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(DELETE_ORDER)) {
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
        public List<OrderDTO> readAll() {
            List<OrderDTO> orders = new ArrayList<>();
            try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(SELECT_ALL_ORDERS)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Order order = mapToOrder(rs);
                    orders.add(orderMapper.orderToOrderDTO(order));
                }
            } catch (SQLException e) {
                logger.error("Failed to read orders", e);
            }
            return orders;
        }

        /**
         * Reads an order from the DataStore by its car ID, if it is a buy order.
         *
         * @param carId The ID of the car.
         * @return The order with the given car ID and type "BUY", or null if not found.
         */
        public OrderDTO readByCarId(long carId) {
            logger.info("Reading order by car ID: {}", carId);
            try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(SELECT_ORDER_BY_CAR_ID_AND_TYPE)) {
                stmt.setLong(1, carId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Order order = mapToOrder(rs);
                    return orderMapper.orderToOrderDTO(order);
                }
            } catch (SQLException e) {
                logger.error("Failed to read order", e);
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