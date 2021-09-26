package com.khoders.pos.dto;

public class ProductPackage {
    private String id;
    private String productId;
    private String unitId;
    private String packageFactor;
    private String packagePrice;

    public ProductPackage(String id, String productId, String unitId, String packageFactor, String packagePrice) {
        this.id = id;
        this.productId = productId;
        this.unitId = unitId;
        this.packageFactor = packageFactor;
        this.packagePrice = packagePrice;
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

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPackageFactor() {
        return packageFactor;
    }

    public void setPackageFactor(String packageFactor) {
        this.packageFactor = packageFactor;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }
}
