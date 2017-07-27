package ves.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String street;
    private String streetNumber;
    private String postalCode;
    private String city;

}