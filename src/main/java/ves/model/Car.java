package ves.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Car {

    @Id
    @GeneratedValue
    @Column(name = "car_id")
    private Long id;
    @Column(name = "car_make")
    private String make;
    @Column(name = "car_model")
    private String model;
    @Column(name = "car_type")
    @Enumerated(EnumType.STRING)
    private CarType type;
    @Column(name = "car_regNumber")
    private String registrationNumber;
    @OneToMany(mappedBy = "car")
    private List<Event> eventList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

}
