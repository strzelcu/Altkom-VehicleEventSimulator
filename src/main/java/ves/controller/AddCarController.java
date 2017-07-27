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
import ves.service.CarService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/addCar.do")
public class AddCarController {

    private static final String FORM_VIEW = "addCar";
    private final static String SUCCESS_VIEW = "home";

    @Autowired
    private CarService carService;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model){
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute(new Car());
        model.addAttribute("carTypes", prepareCarTypes());
        return FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("car") Car car, Errors errors, Model model ) {

        carService.saveCar(car);

        return SUCCESS_VIEW;
    }

    public List prepareCarTypes() {

        List<CarType> carTypes = new ArrayList<CarType>();

        for (CarType carType :
                CarType.values()) {
            carTypes.add(carType);
        }
        return carTypes;
    }


}
