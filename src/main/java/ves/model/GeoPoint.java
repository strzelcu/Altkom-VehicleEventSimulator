package ves.model;

import javax.persistence.*;

@Entity
public class GeoPoint {

    @Id
    @GeneratedValue
    @Column(name = "geo_id")
    private Integer id;
    private Double latitude;
    private Double longitude;
    @Embedded
    private Address address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s",
                address.getStreet(),
                address.getHouseNumber(),
                address.getPostalCode(),
                address.getCity());
    }
}