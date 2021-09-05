package com.example.spazaadmin.models;

class OrderModel {

    public String Id;
    public String CustomerId;
    public String ProductId;
    public String status;

    public OrderModel() {
    }

    public OrderModel(String id, String customerId, String productId, String status) {
        Id = id;
        CustomerId = customerId;
        ProductId = productId;
        this.status = status;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
