package ves.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ves.model.Car;
import ves.model.repository.CarRepository;

import java.util.List;

/**
 * Klasa CarService została zaimplementowana aby udostępniać do kontrolera
 * wybrane możliwości operacji na bazie danych dotyczących encji Car.
 */
@Service
public class CarService {

    @Autowired
    private CarRepository dao;

    public List<Car> getAllCars() {

        return dao.findAll();
    }

    public Car getCar(String carId) {
        return dao.getOne(Integer.valueOf(carId));
    }

    public void saveCar(Car car) {
        dao.save(car);
    }

    public void removeCar(String carId) {
        dao.delete(Integer.valueOf(carId));
    }


}
