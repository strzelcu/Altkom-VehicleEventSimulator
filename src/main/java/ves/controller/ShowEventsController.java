package ves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ves.services.EventService;

/**
 * Klasa ShowEventsController jest odpowiedzialna za obsługę wyświetlenia
 * listy rekordów zawierających wszystkie zdarzenia jak również za obsługę
 * akcji dotyczących rekordów tj. usuń, pobierz Json, pokaż na mapie
 */
@Controller
@RequestMapping(value = "/showEvents.do")
public class ShowEventsController {

    private static final String SHOW_EVENTS = "showEvents";
    private static final String GET_JSON = "redirect:getJson.do?eventId=";
    private static final String SHOW_ON_MAP = "redirect:showMap.do?eventId=";
    @Autowired
    EventService eventService;

    @RequestMapping(method = RequestMethod.GET)
    public String setupPage(Model model) {
        System.setProperty("file.encoding", "UTF-8");
        model.addAttribute("eventsList", eventService.getAllEvents());
        return SHOW_EVENTS;
    }

    /**
     * Metoda doAction jest odpowiedzialna za wywołanie odpowiedniej akcji
     * dla zdarzenia tj. usunięcie, pobranie pliku Json lub wyświetlenie na mapie
     *
     * @param deleteEvent zawiera id zdarzenia do usunięcia
     * @param showOnMap   zawiera id zdarzenia do pokazania na mapie
     * @param getJson     zawiera id zdarzenia do akcji pobrania pliku Json
     */
    @RequestMapping(method = RequestMethod.POST)
    public String doAction(
            @RequestParam(required = false) String deleteEvent,
            @RequestParam(required = false) String showOnMap,
            @RequestParam(required = false) String getJson,
            Model model) {

        if (null != deleteEvent) {
            eventService.removeEvent(deleteEvent);
        }

        if (null != showOnMap) {
            return "redirect:showMap.do" + "?eventId=" + showOnMap;
        }

        if (null != getJson) {
            return GET_JSON + getJson;
        }

        model.addAttribute("eventsList", eventService.getAllEvents());
        return SHOW_EVENTS;
    }
}