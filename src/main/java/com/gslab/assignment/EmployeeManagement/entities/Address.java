package com.gslab.assignment.EmployeeManagement.entities;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Embeddable
public class Address implements Serializable {
    private String city;
    private String pinCode;
    private String state;
    private String street;

    public Address(String city, String pinCode, String state, String street) {
        this.city = city;
        this.pinCode = pinCode;
        this.state = state;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", state='" + state + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    public Address() {
    }
}
