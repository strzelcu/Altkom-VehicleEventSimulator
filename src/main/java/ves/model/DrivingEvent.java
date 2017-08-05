package ves.model;

import javax.persistence.*;
import java.util.List;

/**
 * Klasa DrivingEvent reprezentuje obiekty zdarzeń związanych z poruszaniem się
 * pojazdu. Oprócz cech z klasy Event zawiera również indywidualne cechy
 * zdarzenia poruszania się pojazdu.
 */
@Entity
@DiscriminatorValue("DrivingEvent")
public class DrivingEvent extends Event {

    @Enumerated(EnumType.STRING)
    @Column(name = "dev_type")
    private DriveType driveType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<GeoPoint> geoPoints;

    public DriveType getDriveType() {
        return driveType;
    }

    public void setDriveType(DriveType driveType) {
        this.driveType = driveType;
    }

    public List<GeoPoint> getGeoPoints() {
        return geoPoints;
    }

    public void setGeoPoints(List<GeoPoint> geoPoints) {
        this.geoPoints = geoPoints;
    }

    @Override
    public String toString() {
        return "Zdarzenie jazdy";
    }
}