package ru.ylib.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ylib.dao.CarDAO;
import ru.ylib.models.Car;

import java.util.List;

@Slf4j
@Service
public class CarServiceImpl implements CarService {
    private final CarDAO carDAO;

    @Autowired
    public CarServiceImpl(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public Car create(Car car) {
        return carDAO.create(car);
    }

    public Car read(long id) {
        return carDAO.read(id);
    }

    public Car update(Car car) {
        return carDAO.update(car);
    }

    public void delete(long id) {
        carDAO.delete(id);
    }

    public List<Car> readAll() {
        return carDAO.readAll();
    }
}
