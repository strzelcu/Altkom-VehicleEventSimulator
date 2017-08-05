package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ves.services.CarService;

/**
 * Klasa ShowCarsController jest odpowiedzialna za wyświetlenie listy pojazdów
 * i obsługę akcji dostępnych dla każdego rekordu pojazdu tj. usunięcie lub edycja
 */
@Controller
@RequestMapping(value = "/showCars.do")
public class ShowCarsController {

    private static final String SHOW_CARS = "showCars";
    private final static String EDIT_CAR_VIEW = "redirect:editCar.do?carId=";
    @Autowired
    CarService carService;

    @RequestMapping(method = RequestMethod.GET)
    public String setupPage(Model model) {
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute("carList", carService.getAllCars());
        return SHOW_CARS;
    }

    /**
     * Metoda doAction jest odpowiedzialna za wywołanie odpowiedniej akcji
     * dla pojazdu tj. usunięcie lub edycja.
     *
     * @param deleteCar zawiera id pojazdu do usunięcia
     * @param editCar   zawiera id pojazdu do edycji
     */
    @RequestMapping(method = RequestMethod.POST)
    public String doAction(
            @RequestParam(required = false) String deleteCar,
            @RequestParam(required = false) String editCar,
            Model model) {

        if (null != deleteCar) {
            carService.removeCar(deleteCar);
        }

        if (null != editCar) {
            return EDIT_CAR_VIEW + editCar;
        }

        model.addAttribute("carList", carService.getAllCars());
        return SHOW_CARS;
    }
}