package com.example.spazaadmin.model;

class InfoModel {
    private String businessName;
    private String businessPhoneNumber;
    private String businessDescription;
    private String weekdaysOperatingTime;
    private String weekendsOperatingTime;
    private String sundaysOperatingTime;
    private String address;

    public InfoModel() {
        //empty constructor
    }

    public InfoModel(String businessName, String businessPhoneNumber, String businessDescription, String address,
                     String weekdaysOperatingTime,String weekendsOperatingTime , String sundaysOperatingTime) {

        this.businessName = businessName;
        this.businessPhoneNumber = businessPhoneNumber;
        this.businessDescription = businessDescription;
        this.weekdaysOperatingTime = weekdaysOperatingTime;
        this.weekendsOperatingTime = weekendsOperatingTime;
        this.sundaysOperatingTime = sundaysOperatingTime;
        this.address = address;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessPhoneNumber() {
        return businessPhoneNumber;
    }

    public void setBusinessPhoneNumber(String businessPhoneNumber) {
        this.businessPhoneNumber = businessPhoneNumber;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getWeekdaysOperatingTime() {
        return weekdaysOperatingTime;
    }

    public void setWeekdaysOperatingTime(String weekdaysOperatingTime) {
        this.weekdaysOperatingTime = weekdaysOperatingTime;
    }

    public String getWeekendsOperatingTime() {
        return weekendsOperatingTime;
    }

    public void setWeekendsOperatingTime(String weekendsOperatingTime) {
        this.weekendsOperatingTime = weekendsOperatingTime;
    }

    public String getSundaysOperatingTime() {
        return sundaysOperatingTime;
    }

    public void setSundaysOperatingTime(String sundaysOperatingTime) {
        this.sundaysOperatingTime = sundaysOperatingTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
