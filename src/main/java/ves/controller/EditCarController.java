package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ves.model.Car;
import ves.model.CarType;
import ves.model.util.validation.CarValidator;
import ves.services.CarService;

import java.util.Arrays;
import java.util.List;

/**
 * Klasa EditCarController jest odpowiedzialna za obsługę formularza edycji
 * wybranego pojazdu z bazy danych
 */
@Controller
@RequestMapping(value = "/editCar.do")
public class EditCarController {

    private static final String EDIT_CAR = "editCar";
    private static final String SHOW_CARS_VIEW = "redirect:showCars.do";
    @Autowired
    CarService carService;

    @RequestMapping(method = RequestMethod.GET)
    public String setupPage(
            @RequestParam String carId,
            Model model) {
        System.setProperty("file.encoding", "UTF-8");
        Car car = carService.getCar(carId);
        model.addAttribute("carId", carId);
        model.addAttribute("car", car);
        model.addAttribute("carTypes", prepareCarTypes());
        return EDIT_CAR;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            @ModelAttribute("car") Car oldCar,
            Errors errors,
            Model model,
            @RequestParam String carId,
            @RequestParam(required = false) String make,
            @RequestParam(name = "model", required = false) String carModel,
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(required = false) String type) {

        new CarValidator().validate(oldCar, errors);

        if (errors.hasErrors()) {
            model.addAttribute("carTypes", prepareCarTypes());
            model.addAttribute("carId", carId);
            return EDIT_CAR;
        }

        Car car = carService.getCar(carId);
        car.setMake(make);
        car.setModel(carModel);
        car.setRegistrationNumber(registrationNumber);
        car.setType(CarType.valueOf(type));
        carService.saveCar(car);
        return SHOW_CARS_VIEW;
    }

    /**
     * Metoda przygotowuje listę typów pojazdów potrzebną do wyświetlenia
     * na stronie formularza
     *
     * @return Lista typów pojazdów
     */
    private List prepareCarTypes() {
        return Arrays.asList(CarType.values());
    }

}
