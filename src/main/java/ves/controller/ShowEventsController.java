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

    @RequestMapping(method = RequestMethod.GET)
    public String setupPage(Model model){
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute("eventsList", eventService.getAllEvents());
        return SHOW_EVENTS;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doAction(
            @RequestParam(required = false) String deleteEvent,
            @RequestParam(required = false) String showOnMap,
            @RequestParam(required = false) String getJson,
            Model model){

        if(null != deleteEvent){
            eventService.removeEvent(deleteEvent);
        }

        if(null != showOnMap){;
            return "redirect:showMap.do" + "?eventId=" + showOnMap;
        }

        if(null != getJson){
            return SHOW_EVENTS;
        }

        model.addAttribute("eventsList", eventService.getAllEvents());
        return SHOW_EVENTS;
    }
}