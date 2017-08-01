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
@RequestMapping(value = "/addAdditionalWorkEvent.do")
public class AddAdditionalWorkEventController {

    private static final String FORM_VIEW = "addAdditionalWorkEvent";
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
        model.addAttribute("additionalWorkEvent", new AdditionalWorkEvent());
        model.addAttribute("additionalWorkTypeList", prepareAdditionalWorkTypes());
        return FORM_VIEW;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(
            @RequestParam String carId,
            @RequestParam String startAddress,
            @RequestParam String endAddress,
            @ModelAttribute("additionalWorkEvent") AdditionalWorkEvent event, Errors errors, Model model ) {

//        if(errors.hasErrors()){
//            model.addAttribute("carList", prepareCars());
//            model.addAttribute("additionalWorkTypeList", prepareAdditionalWorkTypes());
//            return FORM_VIEW;
//        } //TODO Zrobić validację
//
        event.setCar(carService.getCar(carId));
        event.setStartPoint(geoApiService.getGeoPoint(startAddress));
        if(endAddress.length() == 0){
            event.setEndPoint(geoApiService.getGeoPoint(endAddress));
        } else {
            event.setEndPoint(event.getStartPoint());
        }
        eventService.saveEvent(event);
        return SUCCESS_VIEW;
    }

    private List prepareCars() {
        return carService.getAllCars();
    }

    private List prepareAdditionalWorkTypes(){
        return Arrays.asList(AdditionalWorkType.values());
    }


}
