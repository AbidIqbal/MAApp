package com.example.abid.maapp;

public class MarkerData {

    private double latitude;
    private double longitude;
    private String fullName;
    private String contact;
    private String startTime;
    private String endTime;

    public MarkerData(){

    }
    public MarkerData(double latitude, double longitude, String fullName, String contact, String startTime, String endTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.fullName = fullName;
        this.contact = contact;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact() {
        return contact;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
