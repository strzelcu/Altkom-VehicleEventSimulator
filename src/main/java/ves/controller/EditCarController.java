package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ves.model.Car;
import ves.model.CarType;
import ves.services.CarService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/editCar.do")
public class EditCarController {

    @Autowired
    CarService carService;

    private static final String EDIT_CAR = "editCar";
    private final static String SHOW_CARS_VIEW = "showCars";

    @RequestMapping(method = RequestMethod.GET)
    public String setupPage(
            @RequestParam(required = true) String carId,
            Model model){
        System.setProperty("file.encoding", "UTF-8");
        Car car = carService.getCar(carId);
        model.addAttribute("car", car);
        model.addAttribute("carTypes", prepareCarTypes());
        return EDIT_CAR;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            @RequestParam String carId,
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String carModel,
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(required = false) String type,
            Model model ) {

        Car car = carService.getCar(carId);
        car.setMake(make);
        car.setModel(carModel);
        car.setRegistrationNumber(registrationNumber);
        car.setType(CarType.valueOf(type));
        carService.saveCar(car);
        return "redirect:" + SHOW_CARS_VIEW + ".do";
    }

    public List prepareCarTypes() {
        return Arrays.asList(CarType.values());
    }

}
