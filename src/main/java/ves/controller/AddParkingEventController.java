package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ves.model.*;
import ves.services.CarService;
import ves.services.EventService;
import ves.services.GraphHopperApiService;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/addParkingEvent.do")
public class AddParkingEventController {

    private static final String FORM_VIEW = "addParkingEvent";
    private final static String SUCCESS_VIEW = "home";

    @Autowired
    private CarService carService;

    @Autowired
    private EventService eventService;

    @Autowired
    private GraphHopperApiService geoApiService;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model){
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute("carList", prepareCars());
        model.addAttribute("parkingEvent", new ParkingEvent());
        model.addAttribute("parkingTypeList", prepareParkingTypes());
        return FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            @RequestParam String carId,
            @RequestParam String startAddress,
            @ModelAttribute("parkingEvent") ParkingEvent event, Errors errors, Model model ) {

        if(errors.hasErrors()){
            model.addAttribute("carList", prepareCars());
            model.addAttribute("parkingTypeList", prepareParkingTypes());
            return FORM_VIEW;
        }

        event.setCar(carService.getCar(carId));
        GeoPoint geoPoint = geoApiService.getGeoPoint(startAddress);
        event.setStartPoint(geoPoint);
        event.setEndPoint(geoPoint);
        eventService.saveEvent(event);
        return SUCCESS_VIEW;
    }

    private List prepareCars() {
        return carService.getAllCars();
    }

    private List prepareParkingTypes(){
        return Arrays.asList(ParkingEventType.values());
    }
}