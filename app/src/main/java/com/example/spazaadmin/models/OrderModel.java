package com.example.spazaadmin.models;

 public class OrderModel {

    public String id;
    public String customer_Id;
    public String product_id;
    public String status;
    private String quantity;


    public OrderModel() {
    }

    public OrderModel(String id, String customerId, String productId, String status, String quantity) {
        this.id = id;
        customer_Id = customerId;
        product_id = productId;
        this.status = status;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        this.customer_Id = customer_Id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
