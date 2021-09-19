package com.example.spazaadmin.models;

import java.util.List;

public class OrderModel {

    private String id;
    public String customer_Id;
    private String product_id;
    private String order_id;
    private String status;
    private String quantity;
    private List<String> extras;


    public OrderModel() {
    }

    public OrderModel(String id, String customerId, String productId,
                      String order_id, String status, String quantity, List<String> extras) {
        this.id = id;
        customer_Id = customerId;
        product_id = productId;
        this.status = status;
        this.quantity = quantity;
        this.order_id = order_id;
        this.extras = extras;
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

     public String getOrder_id() {
         return order_id;
     }

     public void setOrder_id(String order_id) {
         this.order_id = order_id;
     }

     public String getQuantity() {
         return quantity;
     }

     public void setQuantity(String quantity) {
         this.quantity = quantity;
     }

    public List<String> getExtras() {
        return extras;
    }

    public void setExtras(List<String> extras) {
        this.extras = extras;
    }
}
