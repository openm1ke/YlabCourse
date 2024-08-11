package ru.ylib.services;

import ru.ylib.models.User;
import ru.ylib.models.UserRole;

import java.sql.*;

import ru.ylib.utils.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.ylib.Main.logger;

/**
 * This class implements the CRUDService interface for User objects.
 */
public class UserService implements CRUDService<User> {

    private final Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
    }


    /**
     * Creates a new User object in the DataStore.
     *
     * @param user The User object to create.
     * @return The created User object.
     */
    @Override
    public User create(User user) {
        String sql = "INSERT INTO app.user (login, password, role) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                logger.info("User created: {}", user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error creating user", e);
        }
        return user;
    }

    /**
     * Retrieves a User object from the DataStore by its ID.
     *
     * @param id The ID of the User object to retrieve.
     * @return The User object with the specified ID, or null if not found.
     */
    @Override
    public User read(long id) {
        String sql = "SELECT * FROM app.user WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapRowToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates an existing User object in the DataStore.
     *
     * @param user The updated User object.
     * @return The updated User object, or null if not found.
     */
    @Override
    public User update(User user) {
        String sql = "UPDATE app.user SET login = ?, password = ?, role = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole().name());
            pstmt.setLong(4, user.getId());

            pstmt.executeUpdate();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes a User object from the DataStore by its ID.
     *
     * @param id The ID of the User object to delete.
     */
    @Override
    public void delete(long id) {
        String sql = "DELETE FROM app.user WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all User objects from the DataStore.
     *
     * @return A list of all User objects.
     */
    @Override
    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM app.user";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Registers a new User object in the DataStore.
     *
     * @param login The login of the User object.
     * @param password The password of the User object.
     * @param role The role of the User object.
     * @return True if the registration was successful, false if the login is already taken.
     */
    public Optional<User> register(String login, String password, UserRole role) {
        String sql = "SELECT * FROM app.user WHERE login = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Authenticates a User object in the DataStore.
     *
     * @param login The login of the User object.
     * @param password The password of the User object.
     * @return The authenticated User object, or null if authentication failed.
     */
    public Optional<User> authenticate(String login, String password) {
        String sql = "SELECT * FROM app.user WHERE login = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Retrieves a User object from the DataStore by its login.
     *
     * @param login The login of the User object to retrieve.
     * @return The User object with the specified login, or null if not found.
     */
    public Optional<User> findByLogin(String login) {
        String sql = "SELECT * FROM app.user WHERE login = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Maps a ResultSet to a User object.
     *
     * @param rs The ResultSet to map.
     * @return The mapped User object.
     */
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getString("login"), rs.getString("password"), UserRole.valueOf(rs.getString("role")));
        user.setId(rs.getLong("id"));
        return user;
    }
}
