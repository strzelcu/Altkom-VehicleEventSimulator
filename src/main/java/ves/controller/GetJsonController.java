package ves.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ves.model.Event;
import ves.services.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Klasa GetJsonController jest odpowiedzialna za przygotowanie pliku GeoJson
 * do pobrania
 */
@Controller
@RequestMapping(value = "/getJson.do")
public class GetJsonController {

    @Autowired
    EventService eventService;


    /**
     * Metoda odpowiada za zwrócenie pliku *.json z wygenerowanych danych
     * ze zdarzenia
     *
     * @param eventId wskazuje, które zdarzenie powinno być pobrane z bazy danych
     */
    @RequestMapping(method = RequestMethod.GET)
    public void downloadJson(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = true) String eventId) {

        try {

            Event event = eventService.getEvent(eventId);
            String filename = (event.toString() + " " + event.getStartDate().toLocaleString() + ".json").replace(" ", "_");
            File file = new File(filename);

            FileUtils.writeStringToFile(file, eventService.getGeoJSON(event));

            response.setContentType("application/json");

            response.setContentLength((int) file.length());

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
