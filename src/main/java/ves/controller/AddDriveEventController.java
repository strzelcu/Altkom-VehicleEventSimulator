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
import ves.model.DriveType;
import ves.model.DrivingEvent;
import ves.model.GeoPoint;
import ves.services.CarService;
import ves.services.EventService;
import ves.services.GraphHopperApiService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Klasa AddDriveEventController jest odpowiedzialna za obsługę formularza
 * dodania nowego zdarzenia jazdy do bazy
 */
@Controller
@RequestMapping(value = "/addDriveEvent.do")
public class AddDriveEventController {

    private static final String FORM_VIEW = "addDriveEvent";
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
        model.addAttribute("drivingEvent", new DrivingEvent());
        model.addAttribute("carList", prepareCars());
        model.addAttribute("driveTypeList", prepareDriveTypes());
        return FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            @RequestParam String startAddress,
            @RequestParam String endAddress,
            @RequestParam(required = false) String carId,
            @RequestParam String loadAddresses,
            @ModelAttribute("drivingEvent") DrivingEvent event, Errors errors, Model model) {

        if (null == carId) {
            errors.reject("carId", "required");
        }

        if (errors.hasErrors()) {
            model.addAttribute("carList", prepareCars());
            model.addAttribute("driveTypeList", prepareDriveTypes());
            model.addAttribute("error", "Prosz&#281; wype&#322;ni&#263; wszystkie pola przed zapisem");
            return FORM_VIEW;
        }

        try {
            event.setCar(carService.getCar(carId));
            event.setStartPoint(geoApiService.getGeoPoint(startAddress));
            event.setEndPoint(geoApiService.getGeoPoint(endAddress));
            Object[] track = geoApiService.getViaPoints(event.getStartPoint(), event.getEndPoint(), Boolean.valueOf(loadAddresses));
            event.setGeoPoints((List<GeoPoint>) track[0]);
            event.setEndDate(new Date(event.getStartDate().getTime() + ((Long) track[1])));
            eventService.saveEvent(event);
        } catch (AddressNotFoundException e) {
            model.addAttribute("carList", prepareCars());
            model.addAttribute("driveTypeList", prepareDriveTypes());
            model.addAttribute("error", "Nie znaleziono adresu. Wpisz poprawne adresy w formularzu.");
            return FORM_VIEW;
        }

        return SUCCESS_VIEW;
    }

    /**
     * Metoda przygotowuje listę typów pojazdów potrzebną do wyświetlenia
     * na stronie formularza
     *
     * @return Lista typów pojazdów
     */
    private List prepareCars() {
        return carService.getAllCars();
    }

    /**
     * Metoda przygotowująca listę typów jazdy potrzebną do
     * do wyświetlenia na stronie formularza
     *
     * @return Lista typów jazdy
     */
    private List prepareDriveTypes() {
        return Arrays.asList(DriveType.values());
    }

}