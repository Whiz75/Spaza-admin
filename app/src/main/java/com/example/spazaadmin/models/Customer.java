package com.example.spazaadmin.models;

public class Customer {
    private String names;
    private String email;
    private String phone;

    public Customer(String names, String email, String phone) {
        this.names = names;
        this.email = email;
        this.phone = phone;
    }

    public Customer() {
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
