package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ves.model.DriveType;
import ves.model.DrivingEvent;
import ves.model.GeoPoint;
import ves.services.CarService;
import ves.services.EventService;
import ves.services.GraphHopperApiService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/addDriveEvent.do")
public class AddDriveEventController {

    private static final String FORM_VIEW = "addDriveEvent";
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
        model.addAttribute("drivingEvent", new DrivingEvent());
        model.addAttribute("carList", prepareCars());
        model.addAttribute("driveTypeList", prepareDriveTypes());
        return FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            @RequestParam String startAddress,
            @RequestParam String endAddress,
            @RequestParam String carId,
            @RequestParam String loadAddresses,
            @ModelAttribute("drivingEvent") DrivingEvent event, Errors errors, Model model ) {

        if(errors.hasErrors()){
            model.addAttribute("carList", prepareCars());
            model.addAttribute("driveTypeList", prepareDriveTypes());
            return FORM_VIEW;
        }

        event.setCar(carService.getCar(carId));
        event.setStartPoint(geoApiService.getGeoPoint(startAddress));
        event.setEndPoint(geoApiService.getGeoPoint(endAddress));
        Object[] track = geoApiService.getViaPoints(event.getStartPoint(),event.getEndPoint(), Boolean.valueOf(loadAddresses));
        event.setGeoPoints((List<GeoPoint>) track[0]);
        event.setEndDate(new Date(event.getStartDate().getTime()+((Long)track[1])));
        eventService.saveEvent(event);

        return SUCCESS_VIEW;
    }

    private List prepareCars() {
        return carService.getAllCars();
    }

    private List prepareDriveTypes(){
        return Arrays.asList(DriveType.values());
    }

}