package ves.model;

import javax.persistence.Embeddable;

/**
 * Klasa Address jest klasą reprezentującą obiekty adresu
 * przypisane do obiektów klasy GeoPoint.
 */
@Embeddable
public class Address {

    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(street).append(" ")
                .append(houseNumber).append(" ")
                .append(postalCode).append(" ")
                .append(city).append(" ")
                .append(country);
        return result.toString().replaceAll("null ", "");
    }
}