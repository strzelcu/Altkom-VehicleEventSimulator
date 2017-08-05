package ves.model;

import javax.persistence.*;

/**
 * Klasa ParkingEvent reprezentuje obiekty zdarzeń związanych z postojem
 * pojazdu. Oprócz cech z klasy Event zawiera również indywidualne cechy
 * zdarzenia postoju.
 */
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

    @Override
    public String toString() {
        return "Zdarzenie parkingowe";
    }
}