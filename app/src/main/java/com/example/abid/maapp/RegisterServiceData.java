package com.example.abid.maapp;

public class RegisterServiceData {

    private double latitude;
    private double longitude;
    private String serviceName;
    private String fullName;
    private String Contact;
    private String startTime;
    private String endTime;

    public RegisterServiceData(){

    }
    public RegisterServiceData(double latitude, double longitude, String serviceName, String fullName, String contact, String startTime, String endTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.serviceName = serviceName;
        this.fullName = fullName;
        Contact = contact;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
