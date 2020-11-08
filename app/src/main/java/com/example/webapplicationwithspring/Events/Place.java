package com.example.webapplicationwithspring.Events;

public class Place {
    private String location;
    private String name;
    private String description;
    private String workHours;
    private String address;
    private String telegram;
    private String phoneNumber;
    private int id;

    public Place(String name, String location, String description, String workHours,String address, String telegram, String phoneNumber, int id) {
        this.location = location;
        this.name = name;
        this.description = description;
        this.workHours = workHours;
        this.address = address;
        this.telegram = telegram;
        this.phoneNumber = phoneNumber;
        this.id = id;

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
