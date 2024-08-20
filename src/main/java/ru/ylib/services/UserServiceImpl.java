package ru.ylib.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ylib.dto.UserDTO;
import ru.ylib.models.User;
import ru.ylib.models.UserRole;
import ru.ylib.utils.DatabaseConnection;
import ru.ylib.utils.mappers.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final DatabaseConnection databaseConnection;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    public UserServiceImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    private static final String INSERT_USER = "INSERT INTO app.user (login, password, role) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_USER = "UPDATE app.user SET login = ?, password = ?, role = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM app.user WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM app.user";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM app.user WHERE id = ?";
    private static final String SELECT_USER_BY_LOGIN_PASSWORD = "SELECT * FROM app.user WHERE login = ? AND password = ?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM app.user WHERE login = ?";

    @Override
    public UserDTO create(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
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

    @Override
    public UserDTO read(long id) {
        log.info("Reading user with id: {}", id);
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

    @Override
    public UserDTO update(UserDTO userDTO) {
        log.info("Updating user: {}", userDTO);
        User user = userMapper.userDTOToUser(userDTO);
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
        log.info("Deleting user with id: {}", id);
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
        log.info("Reading all users");
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
                User user = mapRowToUser(rs);
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
    public Optional<UserDTO> findByLogin(String login) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_LOGIN)) {

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                log.info("Found user by login: {}", login);
                return Optional.of(userMapper.userToUserDTO(mapRowToUser(rs)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User(rs.getString("login"), rs.getString("password"), UserRole.valueOf(rs.getString("role")));
        user.setId(rs.getLong("id"));
        return user;
    }
}
