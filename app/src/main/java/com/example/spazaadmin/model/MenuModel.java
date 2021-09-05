package com.example.spazaadmin.model;

import java.util.Dictionary;

public class MenuModel {

    public String name;
    public String price;
    public int imgUrl;
    public String status;
    public String key;
    public Dictionary<String, String> extras;

    public MenuModel() {
        //empty constructor
    }

    public MenuModel(String name, String price, int imgUrl, String status, String key,
                     Dictionary<String, String> AddedChips)
    {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.status = status;
        this.key = key;
        this.extras = AddedChips;
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

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
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

    public Dictionary<String, String> getExtras() {
        return extras;
    }

    public void setExtras(Dictionary<String, String> extras) {
        this.extras = extras;
    }
}
