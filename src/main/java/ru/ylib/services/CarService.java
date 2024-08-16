package ru.ylib.services;

import ru.ylib.dto.CarDTO;
import ru.ylib.models.Car;
import ru.ylib.models.CarStatus;
import ru.ylib.utils.DatabaseConnection;
import ru.ylib.utils.mappers.CarMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.ylib.Main.logger;

/**
 * This class implements the CRUDService interface for cars.
 */
public class CarService implements CRUDService<CarDTO, Car> {

    private final DatabaseConnection dbConnection;
    private final CarMapper carMapper = CarMapper.INSTANCE;

    private static final String INSERT_CAR = "INSERT INTO app.car (brand, model, year, price, status) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_CAR = "UPDATE app.car SET brand = ?, model = ?, year = ?, price = ?, status = ? WHERE id = ?";
    private static final String DELETE_CAR = "DELETE FROM app.car WHERE id = ?";
    private static final String SELECT_ALL_CARS = "SELECT * FROM app.car";
    private static final String SELECT_CAR_BY_ID = "SELECT * FROM app.car WHERE id = ?";


    public CarService(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    @Override
    public CarDTO create(Car car) {
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(INSERT_CAR)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPrice());
            stmt.setString(5, car.getStatus().name());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                car.setId(id); // Set the generated ID
                logger.info("Car created: {}", car);
                return carMapper.carToCarDTO(car);
            }
        } catch (SQLException e) {
            logger.error("Failed to create car", e);
        }
        return null;
    }

    /**
     * Reads a car from the DataStore by its ID.
     *
     * @param id The ID of the car to read.
     * @return The car with the given ID, or null if not found.
     */
    @Override
    public CarDTO read(long id) {
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(SELECT_CAR_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Car car = new Car();
                car.setId(rs.getLong("id"));
                car.setBrand(rs.getString("brand"));
                car.setModel(rs.getString("model"));
                car.setYear(rs.getInt("year"));
                car.setPrice(rs.getDouble("price"));
                car.setStatus(CarStatus.valueOf(rs.getString("status")));
                logger.info("Car read: {}", car);
                return carMapper.carToCarDTO(car);
            }
        } catch (SQLException e) {
            logger.error("Failed to read car", e);
        }
        return null;
    }

    /**
     * Updates a car in the DataStore by its ID.
     *
     * @param car The updated car.
     * @return The updated car, or null if not found.
     */
    @Override
    public CarDTO update(Car car) {
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(UPDATE_CAR)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPrice());
            stmt.setString(5, car.getStatus().name());
            stmt.setLong(6, car.getId());
            stmt.executeUpdate();
            logger.info("Car updated: {}", car);
            return carMapper.carToCarDTO(car);
        } catch (SQLException e) {
            logger.error("Failed to update car", e);
        }
        return null;
    }

    /**
     * Deletes a car from the DataStore by its ID.
     *
     * @param id The ID of the car to delete.
     */
    @Override
    public void delete(long id) {
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(DELETE_CAR)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            logger.info("Car deleted: {}", id);
        } catch (SQLException e) {
            logger.error("Failed to delete car", e);
        }
    }

    /**
     * Reads all cars from the DataStore.
     *
     * @return A list of all cars in the DataStore.
     */
    @Override
    public List<CarDTO> readAll() {
        List<CarDTO> cars = new ArrayList<>();
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(SELECT_ALL_CARS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Car car = mapToCar(rs);
                cars.add(carMapper.carToCarDTO(car));
            }
            logger.info("View all cars");
        } catch (SQLException e) {
            logger.error("Failed to read all cars", e);
        }
        return cars;
    }

    public static Car mapToCar(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getLong("id"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setYear(rs.getInt("year"));
        car.setPrice(rs.getDouble("price"));
        car.setStatus(CarStatus.valueOf(rs.getString("status")));
        return car;
    }
}