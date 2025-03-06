package com.acf.trainingserv.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {

    private String addressLine1;
    private String city;
    private String zip;
    private String country;

    public Address() {
    }

    public Address(String addressLine1, String city, String zip, String country) {
        this.addressLine1 = addressLine1;
        this.city = city;
        this.zip = zip;
        this.country = country;
    }

}

