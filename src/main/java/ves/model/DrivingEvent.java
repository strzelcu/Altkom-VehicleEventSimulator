package ves.model;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("DrivingEvent")
public class DrivingEvent extends Event {

    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    @OneToMany(fetch = FetchType.LAZY)
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
}