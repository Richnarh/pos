package com.khoders.pos.dto;

public class ProductType {
    private String id;
    private String productTypeName;

    public ProductType(String id, String productTypeName) {
        this.id = id;
        this.productTypeName = productTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

}
