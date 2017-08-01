package ves.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ves.model.Car;
import ves.model.repository.CarRepository;
import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository dao;

    public List<Car> getAllCars(){

        return dao.findAll();
    }

    public Car getCar(String carId){
        int id = Integer.valueOf(carId);
        return dao.getOne(id);
    }

    public boolean saveCar(Car car){
        if(car != null) {
            dao.save(car);
            return true;
        } else {
            return false;
        }
    }

    public void removeCar(String carId){
        int id = Integer.valueOf(carId);
        dao.delete(id);
    }


}
