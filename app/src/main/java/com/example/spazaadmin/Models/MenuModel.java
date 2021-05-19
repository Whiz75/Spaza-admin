package com.example.spazaadmin.Models;

import java.util.Dictionary;

public class MenuModel {

    public String Name;
    public String Price;
    public int ImgUrl;
    public String Status;
    public String Key;
    public Dictionary<String, String> AddedChips;

    public MenuModel() {
    }

    public MenuModel(String name, String price, int imgUrl, String status, String key, Dictionary<String, String> AddedChips)
    {
        Name = name;
        Price = price;
        ImgUrl = imgUrl;
        Status = status;
        Key = key;
        AddedChips = AddedChips;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public int getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(Integer imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public Dictionary<String, String> getAddedChips() {
        return AddedChips;
    }

    public void setAddedChips(Dictionary<String, String> addedChips) {
        AddedChips = addedChips;
    }
}
