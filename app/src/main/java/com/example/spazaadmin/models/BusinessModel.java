package com.example.spazaadmin.models;

public class BusinessModel {

    String businessName;
    String businessDescription;
    String businessPhoneNo;

    String businessMFOpen;
    String businessMFClose;
    String businessSatOpen;
    String businessSatClose;
    String businessSunOpen;
    String businessSunClose;

    public BusinessModel() {
        //empty constructor
    }

    public BusinessModel(String businessName, String businessDescription,
                         String businessPhoneNo, String businessMFOpen, String businessMFClose,
                         String businessSatOpen, String businessSatClose,
                         String businessSunOpen, String businessSunClose) {
        this.businessName = businessName;
        this.businessDescription = businessDescription;
        this.businessPhoneNo = businessPhoneNo;
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
