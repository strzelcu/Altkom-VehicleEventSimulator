package ves.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ves.services.EventService;

@Controller
@RequestMapping(value = "/showMap.do")
public class ShowMapController {

    @Autowired
    EventService eventService;

    @GetMapping
    public String prepareMap(
            @RequestParam(required = true) String eventId,
            Model model){

        try {
            model.addAttribute("geoJson", eventService.getGeoJSON(eventService.getEvent(eventId)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("event", eventService.getEvent(eventId));
        return "showMap";
    }
}