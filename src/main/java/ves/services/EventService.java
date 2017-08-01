package ves.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ves.model.Car;
import ves.model.Event;
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

}
