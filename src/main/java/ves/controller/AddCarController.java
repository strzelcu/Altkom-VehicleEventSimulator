package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ves.model.Car;
import ves.model.CarType;
import ves.model.util.validation.CarValidator;
import ves.services.CarService;

import java.util.Arrays;
import java.util.List;

/**
 * Klasa AddCarController jest odpowiedzialna za obsługę formularza
 * dodania nowego pojazdu do bazy danych
 */
@Controller
@RequestMapping(value = "/addCar.do")
public class AddCarController {

    private static final String FORM_VIEW = "addCar";
    private static final String SUCCESS_VIEW = "home";

    @Autowired
    private CarService carService;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model) {
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute(new Car());
        model.addAttribute("carTypes", prepareCarTypes());
        return FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("car") Car car, Errors errors, Model model) {

        new CarValidator().validate(car, errors);

        if (errors.hasErrors()) {
            model.addAttribute("carTypes", prepareCarTypes());
            return FORM_VIEW;
        }

        carService.saveCar(car);

        return SUCCESS_VIEW;
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
