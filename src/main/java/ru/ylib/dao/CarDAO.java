package ru.ylib.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ylib.models.Car;
import ru.ylib.models.CarStatus;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CarDAO {

    private final DataSource dataSource;

    private static final String INSERT_CAR = "INSERT INTO app.car (brand, model, year, price, status) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_CAR = "UPDATE app.car SET brand = ?, model = ?, year = ?, price = ?, status = ? WHERE id = ?";
    private static final String DELETE_CAR = "DELETE FROM app.car WHERE id = ?";
    private static final String SELECT_ALL_CARS = "SELECT * FROM app.car";
    private static final String SELECT_CAR_BY_ID = "SELECT * FROM app.car WHERE id = ?";

    @Autowired
    public CarDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Car create(Car car) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(INSERT_CAR)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPrice());
            stmt.setString(5, car.getStatus().name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                car.setId(id); // Set the generated ID
                log.info("Car created: {}", car);
                return car;
            }
        } catch (SQLException e) {
            log.error("Failed to create car", e);
        }
        return null;
    }

    public Car read(long id) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(SELECT_CAR_BY_ID)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Car car = mapToCar(rs);
                log.info("Car read: {}", car);
                return car;
            }
        } catch (SQLException e) {
            log.error("Failed to read car", e);
        }
        return null;
    }

    public Car update(Car car) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(UPDATE_CAR)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setDouble(4, car.getPrice());
            stmt.setString(5, car.getStatus().name());
            stmt.setLong(6, car.getId());
            stmt.executeUpdate();
            log.info("Car updated: {}", car);
            return car;
        } catch (SQLException e) {
            log.error("Failed to update car", e);
        }
        return null;
    }

    public void delete(long id) {
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(DELETE_CAR)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            log.info("Car deleted: {}", id);
        } catch (SQLException e) {
            log.error("Failed to delete car", e);
        }
    }

    public List<Car> readAll() {
        List<Car> cars = new ArrayList<>();
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(SELECT_ALL_CARS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Car car = mapToCar(rs);
                cars.add(car);
            }
            log.info("View all cars");
        } catch (SQLException e) {
            log.error("Failed to read all cars", e);
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
