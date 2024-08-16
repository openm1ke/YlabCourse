package ru.ylib.services;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import ru.ylib.dto.UserDTO;
import ru.ylib.utils.mappers.UserMapper;
import ru.ylib.models.User;
import ru.ylib.models.UserRole;

import java.sql.*;

import ru.ylib.utils.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class implements the CRUDService interface for User objects.
 */
@Slf4j
public class UserService implements CRUDService<UserDTO, User> {

    private final DatabaseConnection databaseConnection;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    private static final String INSERT_USER = "INSERT INTO app.user (login, password, role) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_USER = "UPDATE app.user SET login = ?, password = ?, role = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM app.user WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM app.user";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM app.user WHERE id = ?";
    private static final String SELECT_USER_BY_LOGIN_PASSWORD = "SELECT * FROM app.user WHERE login = ? AND password = ?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM app.user WHERE login = ?";

    /**
     * Creates a new User object in the DataStore.
     *
     * @param user The User object to create.
     * @return The created User object.
     */
    @Override
    public UserDTO create(User user) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getLong("id"));
                log.info("User created: {}", user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Error creating user", e);
        }
        return userMapper.userToUserDTO(user);
    }

    /**
     * Retrieves a User object from the DataStore by its ID.
     *
     * @param id The ID of the User object to retrieve.
     * @return The User object with the specified ID, or null if not found.
     */
    @Override
    public UserDTO read(long id) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_ID)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = mapRowToUser(rs);
                return userMapper.userToUserDTO(user);
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
    public UserDTO update(User user) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_USER)) {

            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().name());
            stmt.setLong(4, user.getId());

            stmt.executeUpdate();
            return userMapper.userToUserDTO(user);
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
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_USER)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
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
    public List<UserDTO> readAll() {
        List<UserDTO> users = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_USERS)) {
             ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = mapRowToUser(rs);
                users.add(userMapper.userToUserDTO(user));
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
    public Optional<UserDTO> register(String login, String password, UserRole role) {
        if (findByLogin(login).isPresent()) {
            return Optional.empty();
        }
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_USER)) {

            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, role.name());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                long id = rs.getLong("id");
                User user = new User();
                user.setId(id);
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(role);
                log.info("User created: {}", user);
                return Optional.of(userMapper.userToUserDTO(user));
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
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_LOGIN_PASSWORD)) {

            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

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
        UserMapper userMapper1 = Mappers.getMapper(UserMapper.class);

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_LOGIN)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

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
