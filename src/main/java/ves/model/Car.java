package ves.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Klasa Car reprezentuje obiekty zawierające dane na temat pojazdów.
 * Usunięcie obiektu klasy Car z bazy danych powoduje usunięcie wszystkich
 * przypisanych do niego zdarzeń.
 */
@Entity
public class Car implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "car_id")
    private Integer id;
    @Column(name = "car_make")
    private String make;
    @Column(name = "car_model")
    private String model;
    @Column(name = "car_type")
    @Enumerated(EnumType.STRING)
    private CarType type;
    @Column(name = "car_regNumber")
    private String registrationNumber;
    @OneToMany(mappedBy = "car", orphanRemoval = true)
    private List<Event> eventList;

    public Car() {
    }

    public Car(String make, String model, CarType type, String registrationNumber, List<Event> eventList) {
        this.make = make;
        this.model = model;
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.eventList = eventList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return getMake() + " " + getModel() + " " + getRegistrationNumber() + " " + getType();
    }
}
