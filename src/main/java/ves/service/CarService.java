package ves.service;

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

    public void saveCar(Car car){
        if(dao != null && car != null) {
            dao.save(car);
        } else {
            System.out.println(car + " " + dao);
        }
    }


}