package com.example.android.rowanparkingpass.personinfo;

import java.io.Serializable;

public class Driver implements PersonInfo  {

    private String uid;
    private String firstName;
    private String lastName;
    private String street;
    private String town;
    private String state;
    private String zipCode;

    public Driver(String uid, String firstName, String lastName, String street, String town, String state, String zipCode) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.town = town;
        this.state = state;
        this.zipCode = zipCode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {return firstName +" "+ lastName;}

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress(){
        return street + " " + town + ", " + state +" " + zipCode;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + firstName + " "+lastName + '\'' +
                '}';
    }
}
