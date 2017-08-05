package ves.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ves.model.*;
import ves.model.repository.EventRepository;

import java.time.Duration;
import java.util.List;

/**
 * Klasa EventService została zaimplementowana aby udostępniać do kontrolera
 * wybrane możliwości operacji na bazie danych dotyczących encji Event.
 * Udostępnia również metodę generującą dane GeoJson zdarzenia.
 */
@Service
public class EventService {

    @Autowired
    EventRepository dao;

    public List<Event> getAllEvents() {
        return dao.findAll();
    }

    public void removeEvent(String id) {
        dao.delete(Integer.valueOf(id));
    }


    public void saveEvent(Event event) {
        dao.save(event);
    }

    public Event getEvent(String eventId) {
        return dao.findOne(Integer.valueOf(eventId));
    }

    /**
     * Metoda getGeoJson zwraca string zawierający dane Json wygenerowane na podstawie
     * przekazanego w parametrze obiektu klasy Event.
     *
     * @param event należy dostarczyć klasę zdarzenia dziedziczącą po Event
     * @return String zawierający dane Json na temat zdarzenia
     * @throws JsonProcessingException gdy wystąpi problem z tworzeniem Jsona
     */
    public String getGeoJSON(Event event) throws JsonProcessingException {

        //Zmienna przechowująca wszystkie dane GeoJSON
        FeatureCollection featureCollection = new FeatureCollection();

        // Tworzenie punktu startowego zdarzenia
        Feature startPointFeature = new Feature();
        startPointFeature.setProperty("name", event + " ID:" + event.getId());
        startPointFeature.setProperty("date", event.getStartDate().toLocaleString());
        startPointFeature.setProperty("car", event.getCar().toString());
        startPointFeature.setProperty("address", event.getStartPoint().getAddress().toString());
        if (event instanceof DrivingEvent) {
            startPointFeature.setProperty("popupContent", "Punkt początkowy zdarzenia");
            startPointFeature.setProperty("type", ((DrivingEvent) event).getDriveType());
        }
        if (event instanceof ParkingEvent) {
            startPointFeature.setProperty("type", ((ParkingEvent) event).getParkingEventType());
            Duration duration = Duration.between(event.getStartDate().toInstant(), event.getEndDate().toInstant());
            startPointFeature.setProperty("popupContent",
                    String.format("Czas trwania zdarzenia: %s minut", duration.toMinutes()));
        }
        if (event instanceof AdditionalWorkEvent) {
            startPointFeature.setProperty("popupContent", "Zdarzenie pracy dodatkowej");
            startPointFeature.setProperty("type", ((AdditionalWorkEvent) event).getWorkType());
            startPointFeature.setProperty("additionalWorkValue", ((AdditionalWorkEvent) event).getAdditionalParameter());
        }
        Point startPoint = new Point();
        LngLatAlt startCoordinates = new LngLatAlt();
        startCoordinates.setLatitude(event.getStartPoint().getLatitude());
        startCoordinates.setLongitude(event.getStartPoint().getLongitude());
        startPoint.setCoordinates(startCoordinates);
        startPointFeature.setGeometry(startPoint);
        featureCollection.add(startPointFeature);

        // Tworzenie ścieżki trasy dla zdarzenia jazdy
        if (event instanceof DrivingEvent) {
            if (((DrivingEvent) event).getGeoPoints().size() > 0) {
                Feature trackFeature = new Feature();
                LineString trackLine = new LineString();
                for (GeoPoint geoPoint :
                        ((DrivingEvent) event).getGeoPoints()) {
                    LngLatAlt viaPoint = new LngLatAlt();
                    viaPoint.setLongitude(geoPoint.getLongitude());
                    viaPoint.setLatitude(geoPoint.getLatitude());
                    trackLine.add(viaPoint);
                }
                trackFeature.setGeometry(trackLine);
                featureCollection.add(trackFeature);
            }
        }

        // Tworzenie punktu końcowego zdarzenia
        if (!(event instanceof ParkingEvent)) {
            Feature endPointFeature = new Feature();
            Point endPoint = new Point();
            LngLatAlt endCoordinates = new LngLatAlt();
            endPointFeature.setProperty("name", event + " ID:" + event.getId());
            endPointFeature.setProperty("date", event.getEndDate().toLocaleString());
            endPointFeature.setProperty("car", event.getCar().toString());
            endPointFeature.setProperty("address", event.getEndPoint().getAddress().toString());
            if (event instanceof DrivingEvent) {
                endPointFeature.setProperty("popupContent", "Punkt końcowy zdarzenia");
                endPointFeature.setProperty("type", ((DrivingEvent) event).getDriveType());
            }
            if (event instanceof AdditionalWorkEvent) {
                endPointFeature.setProperty("popupContent", "Zdarzenie pracy dodatkowej");
                endPointFeature.setProperty("type", ((AdditionalWorkEvent) event).getWorkType());
                endPointFeature.setProperty("additionalWorkValue", ((AdditionalWorkEvent) event).getAdditionalParameter());
            }
            endCoordinates.setLatitude(event.getEndPoint().getLatitude());
            endCoordinates.setLongitude(event.getEndPoint().getLongitude());
            endPoint.setCoordinates(endCoordinates);
            endPointFeature.setGeometry(endPoint);
            featureCollection.add(endPointFeature);
        }

        //Zwrócenie stringu JSON
        return new ObjectMapper().writeValueAsString(featureCollection);
    }
}