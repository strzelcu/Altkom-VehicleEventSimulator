package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ves.model.Car;
import ves.model.Event;
import ves.service.CarService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/addEvent.do")
public class AddEventFlowController {

    private static final String FORM_VIEW = "addEvent";
    private final static String SUCCESS_VIEW = "home";

    @Autowired
    private CarService carService;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model){
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute(new Event());
        model.addAttribute("cars", prepareCars());
        return FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(@ModelAttribute("car") Car car, Errors errors, Model model ) {

        if(errors.hasErrors()){
            model.addAttribute("cars", prepareCars());
            return FORM_VIEW;
        }


        return SUCCESS_VIEW;
    }

    public List prepareCars() {

        List<String> cars = new ArrayList<String>();

        for (Car car :
                carService.getAllCars()) {
            cars.add(String.valueOf(car));
        }

        return cars;
    }
}
