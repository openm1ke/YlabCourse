package ru.ylib.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ylib.dto.CarDTO;
import ru.ylib.mappers.CarMapper;
import ru.ylib.models.Car;
import ru.ylib.services.CarService;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    @Autowired
    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable("id") long id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Car car = carService.read(id);
        if (car != null) {
            CarDTO carDTO = carMapper.carToCarDTO(car);
            return ResponseEntity.ok(carDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
        Car car = carMapper.carDTOToCar(carDTO);
        Car createdCar = carService.create(car);
        CarDTO createdCarDTO = carMapper.carToCarDTO(createdCar);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCarDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable("id") long id, @RequestBody CarDTO carDTO) {
        Car car = carMapper.carDTOToCar(carDTO);
        car.setId(id);
        Car updatedCar = carService.update(car);
        if (updatedCar != null) {
            CarDTO updatedCarDTO = carMapper.carToCarDTO(updatedCar);
            return ResponseEntity.ok(updatedCarDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") long id) {
        try {
            carService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        List<Car> cars = carService.readAll();
        List<CarDTO> carDTOs = carMapper.carsToCarDTOs(cars);
        return ResponseEntity.ok(carDTOs);
    }
}
