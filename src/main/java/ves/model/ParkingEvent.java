package ves.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("ParkingEvent")
public class ParkingEvent extends Event{

    @Column(name = "pev_type")
    @Enumerated(EnumType.STRING)
    private ParkingEventType parkingEventType;

    public ParkingEventType getParkingEventType() {
        return parkingEventType;
    }

    public void setParkingEventType(ParkingEventType parkingEventType) {
        this.parkingEventType = parkingEventType;
    }
}