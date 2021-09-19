package com.example.spazaadmin.models;

public class AdminModel {

    private String email;
    private String fullName;
    private String latlong;
    private String phoneNumber;
    private String url;

    public AdminModel() {
    }

    public AdminModel(String email, String fullName, String latlong, String phoneNumber, String url) {
        this.email = email;
        this.fullName = fullName;
        this.latlong = latlong;
        this.phoneNumber = phoneNumber;
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
