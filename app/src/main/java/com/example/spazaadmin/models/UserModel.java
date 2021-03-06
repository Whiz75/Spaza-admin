package com.example.spazaadmin.models;

public class UserModel
{
    String businessName;
    String businessEmail;
    String businessDescription;
    String businessPhoneNo;
    String latlong;
    String uri;

    String businessMFOpen;
    String businessMFClose;
    String businessSatOpen;
    String businessSatClose;
    String businessSunOpen;
    String businessSunClose;

    public UserModel() {
        //empty constructor
    }

    public UserModel(String businessName, String businessEmail, String businessDescription,
                     String businessPhoneNo, String latlong, String businessMFOpen,
                     String businessMFClose, String businessSatOpen, String businessSatClose,
                     String businessSunOpen, String businessSunClose, String uri) {
        this.businessName = businessName;
        this.businessEmail = businessEmail;
        this.businessDescription = businessDescription;
        this.businessPhoneNo = businessPhoneNo;
        this.latlong = latlong;
        this.uri = uri;
        this.businessMFOpen = businessMFOpen;
        this.businessMFClose = businessMFClose;
        this.businessSatOpen = businessSatOpen;
        this.businessSatClose = businessSatClose;
        this.businessSunOpen = businessSunOpen;
        this.businessSunClose = businessSunClose;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getBusinessPhoneNo() {
        return businessPhoneNo;
    }

    public void setBusinessPhoneNo(String businessPhoneNo) {
        this.businessPhoneNo = businessPhoneNo;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getBusinessMFOpen() {
        return businessMFOpen;
    }

    public void setBusinessMFOpen(String businessMFOpen) {
        this.businessMFOpen = businessMFOpen;
    }

    public String getBusinessMFClose() {
        return businessMFClose;
    }

    public void setBusinessMFClose(String businessMFClose) {
        this.businessMFClose = businessMFClose;
    }

    public String getBusinessSatOpen() {
        return businessSatOpen;
    }

    public void setBusinessSatOpen(String businessSatOpen) {
        this.businessSatOpen = businessSatOpen;
    }

    public String getBusinessSatClose() {
        return businessSatClose;
    }

    public void setBusinessSatClose(String businessSatClose) {
        this.businessSatClose = businessSatClose;
    }

    public String getBusinessSunOpen() {
        return businessSunOpen;
    }

    public void setBusinessSunOpen(String businessSunOpen) {
        this.businessSunOpen = businessSunOpen;
    }

    public String getBusinessSunClose() {
        return businessSunClose;
    }

    public void setBusinessSunClose(String businessSunClose) {
        this.businessSunClose = businessSunClose;
    }
}
