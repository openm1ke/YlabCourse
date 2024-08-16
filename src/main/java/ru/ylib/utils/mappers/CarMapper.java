package ru.ylib.utils.mappers;

import ru.ylib.models.Car;
import ru.ylib.models.CarStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper {
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
