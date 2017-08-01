package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ves.services.EventService;

@Controller
@RequestMapping(value = "/showEvents.do")
public class ShowEventsController {

    @Autowired
    EventService eventService;

    private static final String SHOW_EVENTS = "showEvents";
    private final static String MAP_VIEW = "home";

    @RequestMapping(method = RequestMethod.GET)
    public String setupPage(Model model){
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute("eventsList", eventService.getAllEvents());
        return SHOW_EVENTS;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doAction(
            @RequestParam(required = false) String deleteEvent,
            @RequestParam(required = false) String editCar,
            RedirectAttributes redirectAttributes,
            Model model){

        if(null != deleteEvent){
            eventService.removeEvent(deleteEvent);
        }

//        if(null != editCar){
//            redirectAttributes.addFlashAttribute("car", carService.getCar(editCar));
//            return "redirect:" + EDIT_CAR_VIEW + ".do" + "?carId=" + editCar;
//        }

        model.addAttribute("eventsList", eventService.getAllEvents());
        return SHOW_EVENTS;
    }
}