package com.khoders.pos.dto;

public class Product {
    private String id;
    private String productId;
    private String productName;
    private String productType;

    public  Product(String id, String productId, String productName, String productType) {
        this.id = id;
        this.productName = productName;
        this.productType = productType;
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
