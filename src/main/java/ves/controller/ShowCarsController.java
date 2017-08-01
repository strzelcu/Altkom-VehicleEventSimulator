package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ves.services.CarService;

@Controller
@RequestMapping(value = "/showCars.do")
public class ShowCarsController {

    @Autowired
    CarService carService;

    private static final String SHOW_CARS = "showCars";
    private final static String EDIT_CAR_VIEW = "editCar";

    @RequestMapping(method = RequestMethod.GET)
    public String setupPage(Model model){
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute("carList", carService.getAllCars());
        return SHOW_CARS;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doAction(
            @RequestParam(required = false) String deleteCar,
            @RequestParam(required = false) String editCar,
            RedirectAttributes redirectAttributes,
            Model model){

        if(null != deleteCar){
            carService.removeCar(deleteCar);
        }

        if(null != editCar){
            redirectAttributes.addFlashAttribute("car", carService.getCar(editCar));
            return "redirect:" + EDIT_CAR_VIEW + ".do" + "?carId=" + editCar;
        }

        model.addAttribute("carList", carService.getAllCars());
        return SHOW_CARS;
    }
}