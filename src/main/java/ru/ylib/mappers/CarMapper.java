package ru.ylib.mappers;

import org.mapstruct.Mapper;
import ru.ylib.dto.CarDTO;
import ru.ylib.models.Car;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarDTO carToCarDTO(Car car);
    Car carDTOToCar(CarDTO carDTO);

    List<CarDTO> carsToCarDTOs(List<Car> cars);
    List<Car> carDTOsToCars(List<CarDTO> carDTOs);
}
