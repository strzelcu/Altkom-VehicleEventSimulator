package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ves.exceptions.AddressNotFoundException;
import ves.model.AdditionalWorkEvent;
import ves.model.AdditionalWorkType;
import ves.services.CarService;
import ves.services.EventService;
import ves.services.GraphHopperApiService;

import java.util.Arrays;
import java.util.List;

/**
 * Klasa AddAdditionalWorkEventController jest odpowiedzialna za
 * wyświetlenie i obsługę formularza dodającego zdarzenie
 * pracy dodatkowej.
 */
@Controller
@RequestMapping(value = "/addAdditionalWorkEvent.do")
public class AddAdditionalWorkEventController {

    private static final String FORM_VIEW = "addAdditionalWorkEvent";
    private static final String SUCCESS_VIEW = "home";

    @Autowired
    private CarService carService;

    @Autowired
    private EventService eventService;

    @Autowired
    private GraphHopperApiService geoApiService;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model) {
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute("carList", prepareCars());
        model.addAttribute("additionalWorkEvent", new AdditionalWorkEvent());
        model.addAttribute("additionalWorkTypeList", prepareAdditionalWorkTypes());
        return FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            @RequestParam(required = false) String carId,
            @RequestParam String startAddress,
            @RequestParam String endAddress,
            @ModelAttribute("additionalWorkEvent") AdditionalWorkEvent event,
            Errors errors,
            Model model) {

        if (null == carId) {
            errors.reject("carId", "required");
        }

        if (errors.hasErrors()) {
            model.addAttribute("carList", prepareCars());
            model.addAttribute("additionalWorkTypeList", prepareAdditionalWorkTypes());
            model.addAttribute("error", "Prosz&#281; wype&#322;ni&#263; wszystkie pola przed zapisem");
            return FORM_VIEW;
        }

        try {
            event.setCar(carService.getCar(carId));
            event.setStartPoint(geoApiService.getGeoPoint(startAddress));
            if (endAddress.length() != 0) {
                event.setEndPoint(geoApiService.getGeoPoint(endAddress));
            } else {
                event.setEndPoint(event.getStartPoint());
            }
            eventService.saveEvent(event);
        } catch (AddressNotFoundException e) {
            model.addAttribute("carList", prepareCars());
            model.addAttribute("additionalWorkTypeList", prepareAdditionalWorkTypes());
            model.addAttribute("error", "Nie znaleziono adresu. Wpisz poprawne adresy w formularzu.");
            return FORM_VIEW;
        }
        return SUCCESS_VIEW;
    }

    /**
     * Metoda przygotowująca listę pojazdów znajdujących się w bazie danych
     * potrzebną do wyświetlenia na stronie formularza
     *
     * @return Lista pojazdów
     */
    private List prepareCars() {
        return carService.getAllCars();
    }

    /**
     * Metoda przygotowująca listę typów prac dodatkowych potrzebną do
     * do wyświetlenia na stronie formularza
     *
     * @return Lista typów prac dodatkowych
     */
    private List prepareAdditionalWorkTypes() {
        return Arrays.asList(AdditionalWorkType.values());
    }

}
