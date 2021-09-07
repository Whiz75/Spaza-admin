package com.example.spazaadmin.models;

import java.util.List;

public class MenuModel {

    public String name;
    public String price;
    public String url;
    public String status;
    public String key;
    private List<String> extras;

    public MenuModel() {
    }

    public MenuModel(String name, String price, String url,
                     String status, String key, List<String> extras) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.status = status;
        this.key = key;
        this.extras = extras;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getExtras() {
        return extras;
    }

    public void setExtras(List<String> extras) {
        this.extras = extras;
    }
}
