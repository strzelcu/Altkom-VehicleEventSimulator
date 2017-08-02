package ves.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ves.model.*;
import ves.model.repository.EventRepository;

import java.util.List;

@Service
public class EventService {

    @Autowired
    EventRepository dao;

    public List<Event> getAllEvents(){
        return dao.findAll();
    }

    public void removeEvent(String id){
        dao.delete(Integer.valueOf(id));
    }


    public boolean saveEvent(Event event){
        if(event != null) {
            dao.save(event);
            return true;
        } else {
            return false;
        }
    }

    public Event getEvent(String eventId){
        return dao.findOne(Integer.valueOf(eventId));
    }

    public String getGeoJSON(Event event) throws JsonProcessingException {
        System.out.println(event.getClass().getSimpleName());

        //Przechowywanie wszystkich danych GeoJSON
        FeatureCollection featureCollection = new FeatureCollection();

        // Tworzenie punktu startowego zdarzenia
        Feature startPointFeature = new Feature();
        Point startPoint = new Point();
        LngLatAlt startCoordinates = new LngLatAlt();
        startCoordinates.setLatitude(event.getStartPoint().getLatitude());
        startCoordinates.setLongitude(event.getStartPoint().getLongitude());
        startPoint.setCoordinates(startCoordinates);
        startPointFeature.setGeometry(startPoint);
        featureCollection.add(startPointFeature);

        // Tworzenie ścieżki trasy dla zdarzenia jazdy
        if(event instanceof DrivingEvent){
            if(((DrivingEvent) event).getGeoPoints().size() > 0){
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
        Feature endPointFeature = new Feature();
        Point endPoint = new Point();
        LngLatAlt endCoordinates = new LngLatAlt();
        endCoordinates.setLatitude(event.getEndPoint().getLatitude());
        endCoordinates.setLongitude(event.getEndPoint().getLongitude());
        endPoint.setCoordinates(endCoordinates);
        endPointFeature.setGeometry(endPoint);
        featureCollection.add(endPointFeature);

        //Zwrócenie stringu JSON
        String json = new ObjectMapper().writeValueAsString(featureCollection);
        return json;
    }
}