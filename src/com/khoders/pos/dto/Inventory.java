package com.khoders.pos.dto;

import java.sql.Date;
import java.time.LocalDate;

public class Inventory {
    private String id;
    private String inventoryId;
    private String productId;
    private int quantity;
    private java.sql.Date purchasedDate;
    private double costPrice;
    private double sellingPrice;
    private String description;

    public Inventory(String id, String inventoryId, String productId, int quantity, java.sql.Date purchasedDate, double costPrice, double sellingPrice, String description) {
        this.id = id;
        this.inventoryId = inventoryId;
        this.productId = productId;
        this.quantity = quantity;
        this.purchasedDate = purchasedDate;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
